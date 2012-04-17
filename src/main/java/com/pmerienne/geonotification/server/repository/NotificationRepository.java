package com.pmerienne.geonotification.server.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.geo.Box;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.pmerienne.geonotification.shared.model.Notification;
import com.pmerienne.geonotification.shared.model.NotificationType;

@Repository("notificationRepository")
public interface NotificationRepository extends MongoRepository<Notification, String> {

	List<Notification> findByTypeInAndPositionWithin(List<NotificationType> acceptedTypes, Box box, Sort sort);

	Page<Notification> findByTypeIn(List<NotificationType> acceptedTypes, Pageable pageable);

	List<Notification> findByTypeAndCreationDateLessThan(NotificationType type, Date minDate);
}
