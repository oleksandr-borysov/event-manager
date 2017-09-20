package com.ab.eventmanager.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ab.eventmanager.model.Event;

@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

	public Iterable<Event> findByDeviceId(String deviceId);

	public Iterable<Event> findByEventType(String eventType);

	public Iterable<Event> findByTimestampBetween(Timestamp startTime, Timestamp endTime);

	public Iterable<Event> findByDeviceIdAndEventTypeAndTimestampBetween(String deviceId, String eventType,
			Timestamp startTime, Timestamp endTime);
}
