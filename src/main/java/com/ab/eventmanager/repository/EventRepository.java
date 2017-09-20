package com.ab.eventmanager.repository;

import java.sql.Timestamp;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.ab.eventmanager.model.Event;

@Repository
@Transactional
public interface EventRepository extends JpaRepository<Event, Long> {

	public Iterable<Event> findByDeviceId(String deviceId);

	public Iterable<Event> findByEventType(String eventType);

	public Iterable<Event> findByDeviceIdAndEventType(String deviceId, String eventType);
	
	public Iterable<Event> findByTimestampBetween(Timestamp startTime, Timestamp endTime);

	public Iterable<Event> findByDeviceIdAndEventTypeAndTimestampBetween(String deviceId, String eventType,
			Timestamp startTime, Timestamp endTime);
}
