package com.pmerienne.geonotification.server.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.geo.Box;
import org.springframework.data.mongodb.core.index.GeospatialIndex;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.pmerienne.geonotification.server.messaging.MessagingService;
import com.pmerienne.geonotification.server.repository.NotificationRepository;
import com.pmerienne.geonotification.shared.model.Bounds;
import com.pmerienne.geonotification.shared.model.Notification;
import com.pmerienne.geonotification.shared.model.NotificationType;
import com.pmerienne.geonotification.shared.service.NotificationService;

@Service("notificationService")
public class NotificationServiceImpl implements NotificationService, InitializingBean {

	private final static Sort DEFAULT_SORT = new Sort(Direction.DESC, "creationDate");

	@Autowired
	private MessagingService messagingService;

	@Autowired
	private NotificationRepository notifiactionRepository;

	@Autowired
	private MongoTemplate mongoTemplate;

	@Override
	public Notification findById(String id) {
		return this.notifiactionRepository.findOne(id);
	}

	@Override
	public Notification create(NotificationType type, String name, String description, double[] position) {
		// Create and save notification
		Notification notification = new Notification(type, name, description, position);
		notification = this.notifiactionRepository.save(notification);

		// Message service
		this.messagingService.onNotificationSaved(notification);
		return notification;
	}

	@Override
	public void votePlus(String notificationId) {
		Notification notification = this.notifiactionRepository.findOne(notificationId);
		if (notification != null) {
			notification.votePlus();
			this.notifiactionRepository.save(notification);

			// Message service
			this.messagingService.onNotificationSaved(notification);
		}
	}

	@Override
	public void voteMinus(String notificationId) {
		Notification notification = this.notifiactionRepository.findOne(notificationId);
		if (notification != null) {
			notification.voteMinus();
			this.notifiactionRepository.save(notification);

			// Message service
			this.messagingService.onNotificationSaved(notification);
		}
	}

	@Override
	public void voteEnd(String notificationId) {
		Notification notification = this.notifiactionRepository.findOne(notificationId);
		if (notification != null) {
			notification.voteEnd();
			this.notifiactionRepository.save(notification);

			// Message service
			this.messagingService.onNotificationSaved(notification);
		}
	}

	@Override
	public List<Notification> findNotifications(Bounds bounds, List<NotificationType> acceptedTypes) {
		// Convert Bound to mongodb box
		double[] lowerLeft = bounds.getSouthWest();
		double[] upperRight = bounds.getNorthEast();
		Box box = new Box(lowerLeft, upperRight);

		List<Notification> notifications = this.notifiactionRepository.findByTypeInAndPositionWithin(acceptedTypes, box, DEFAULT_SORT);
		return notifications;
	}

	@Override
	public List<Notification> findNotifications(List<NotificationType> acceptedTypes, Integer limit) {
		// Create page
		Pageable pageable = new PageRequest(0, limit, DEFAULT_SORT);

		// Query MongoDB and Convert Page to List
		List<Notification> notifications = pageToList(this.notifiactionRepository.findByTypeIn(acceptedTypes, pageable));

		return notifications;
	}

	/**
	 * Delete old notification every hours
	 */
	@Scheduled(fixedRate = 24 * 60 * 60 * 1000)
	public void deleteOldNotifications() {
		Date now = new Date();
		List<Notification> oldNotifications = new ArrayList<Notification>();

		// Each type as a special duration
		for (NotificationType type : NotificationType.values()) {
			// Find minimum date
			double durationInDay = type.getDuration();
			long durationInMilliseconds = (long) durationInDay * 24 * 60 * 60 * 1000;
			Date minDate = new Date(now.getTime() - durationInMilliseconds);

			// Remove notifications before this date
			oldNotifications.addAll(this.notifiactionRepository.findByTypeAndCreationDateLessThan(type, minDate));
		}

		this.notifiactionRepository.delete(oldNotifications);
	}

	private List<Notification> pageToList(Page<Notification> page) {
		List<Notification> notifications = new ArrayList<Notification>();
		notifications.addAll(page.getContent());
		return notifications;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		// Trick to avoid use of @GeoSpacialIndexed
		this.mongoTemplate.indexOps(Notification.class).ensureIndex(new GeospatialIndex("position"));
	}

}
