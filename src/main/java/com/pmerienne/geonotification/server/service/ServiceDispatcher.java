package com.pmerienne.geonotification.server.service;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.google.gwt.user.client.rpc.IncompatibleRemoteServiceException;
import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.RPC;
import com.google.gwt.user.server.rpc.RPCRequest;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

public class ServiceDispatcher extends RemoteServiceServlet {

	private static final long serialVersionUID = 5713342995532698061L;

	private final static Logger LOGGER = Logger.getLogger(ServiceDispatcher.class);

	@Override
	public String processCall(String payload) throws SerializationException {
		try {
			String serviceName = extractServiceName(getThreadLocalRequest().getServletPath());
			Object service = ApplicationContextHolder.getContext().getBean(serviceName);

			RPCRequest rpcRequest = RPC.decodeRequest(payload, service.getClass(), this);
			onAfterRequestDeserialized(rpcRequest);

			return RPC.invokeAndEncodeResponse(service, rpcRequest.getMethod(), rpcRequest.getParameters(), rpcRequest.getSerializationPolicy(),
					rpcRequest.getFlags());
		} catch (IncompatibleRemoteServiceException ex) {
			LOGGER.error("An IncompatibleRemoteServiceException was thrown while processing this call.", ex);
			return RPC.encodeResponseForFailure(null, ex);
		}
	}

	protected String extractServiceName(String servletPath) throws IncompatibleRemoteServiceException {
		String path = servletPath.substring(servletPath.lastIndexOf("/") + 1);
		String serviceName = path.substring(0, path.length() - 4);
		if (serviceName == null) {
			throw new IncompatibleRemoteServiceException("The requested service (" + servletPath + ") cannot be found.");
		}
		return serviceName;
	}

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		WebApplicationContext ctx = WebApplicationContextUtils.getRequiredWebApplicationContext(config.getServletContext());
		AutowireCapableBeanFactory beanFactory = ctx.getAutowireCapableBeanFactory();
		beanFactory.autowireBean(this);
	}
}
