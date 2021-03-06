package com.ab.eventmanager.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ab.eventmanager.model.Event;
import com.ab.eventmanager.repository.DynamicFilterEventRepository;
import com.ab.eventmanager.repository.EventRepository;

@RestController()
@RequestMapping(value = "/event")
public class EventController {

	@Autowired
	private EventRepository eventRepository;
	
	@Autowired
	private DynamicFilterEventRepository dynamicFilterRepository;

	@RequestMapping(value = "/add")
	public void create(@RequestBody Event event) {
		eventRepository.save(event);
	}

	@RequestMapping(value = "/add_all")
	public void createBatch(@RequestBody Iterable<Event> events) {
		eventRepository.save(events);
	}

	@RequestMapping(value = "/delete_all")
	public void delete() {
		eventRepository.deleteAll();
	}

	@RequestMapping(method = RequestMethod.GET, value = "/all")
	public Iterable<Event> find() {
		return eventRepository.findAll();
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Event> findByFilter(@RequestParam(required = false) Map<String, String> filter) throws Exception {
			return dynamicFilterRepository.find(filter);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/device_id={deviceId}")
	public Iterable<Event> findByDeviceId(@PathVariable String deviceId) {
		return eventRepository.findByDeviceId(deviceId);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/event_type={eventType}")
	public Iterable<Event> findByEventType(@PathVariable String eventType) {
		return eventRepository.findByEventType(eventType);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/device_id={deviceId}/event_type={eventType}")
	public Iterable<Event> findByDeviceIdAndEventType(@PathVariable String deviceId, @PathVariable String eventType) {
		return eventRepository.findByDeviceIdAndEventType(deviceId, eventType);
	}
	
	
	@RequestMapping(method = RequestMethod.GET, value = "/start_time={startTime}/end_time={endTime}")
	public Iterable<Event> findByTimeRange(
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date startTime,
			@PathVariable @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss") Date endTime) {
		java.sql.Timestamp startTimestamp = new java.sql.Timestamp(startTime.getTime());
		java.sql.Timestamp endTimestamp = new java.sql.Timestamp(endTime.getTime());		
		return eventRepository.findByTimestampBetween(startTimestamp, endTimestamp);
	}

	@RequestMapping(method = RequestMethod.GET, value = "/device_id={deviceId}/event_type={eventType}/start_time={startTime}/end_time={endTime}")
	public Iterable<Event> find(String deviceId, String eventType, Date startTime, Date endTime) {
		java.sql.Timestamp startTimestamp = new java.sql.Timestamp(startTime.getTime());
		java.sql.Timestamp endTimestamp = new java.sql.Timestamp(endTime.getTime());
		return eventRepository.findByDeviceIdAndEventTypeAndTimestampBetween(deviceId, eventType, startTimestamp, endTimestamp);
	}
}
