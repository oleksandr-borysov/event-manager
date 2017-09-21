package com.ab.eventmanager.utils;

public enum EventFilter {

	DEVICE_ID("device_id"), EVENT_TYPE("event_type"), START_TIME("start_time"), END_TIME("end_time");
	
	private String name;
	
	EventFilter(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

 }
