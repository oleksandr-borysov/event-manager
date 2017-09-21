package com.ab.eventmanager.repository;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Repository;

import com.ab.eventmanager.model.Event;
import com.ab.eventmanager.utils.EventFilter;

@Repository
public class DynamicFilterEventRepository {

	@PersistenceContext
	private EntityManager em;

	private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

	public DynamicFilterEventRepository() {

	}

	public Iterable<Event> find(Map<String, String> filters) throws Exception {
		CriteriaBuilder cb = em.getCriteriaBuilder();
		CriteriaQuery<Event> cq = cb.createQuery(Event.class);
		Root<Event> event = cq.from(Event.class);

		final List<Predicate> predicates = new ArrayList<Predicate>();

		// Device ID filter
		String deviceIdFilterValue = getFilterValue(EventFilter.DEVICE_ID, filters);
		if (deviceIdFilterValue != null && !deviceIdFilterValue.isEmpty()) {
			predicates.add(cb.equal(event.get("deviceId"), deviceIdFilterValue));
		}

		// Event type filter
		String eventTypefilterValue = getFilterValue(EventFilter.EVENT_TYPE, filters);
		if (eventTypefilterValue != null && !eventTypefilterValue.isEmpty()) {
			predicates.add(cb.equal(event.get("eventType"), eventTypefilterValue));
		}

		// Time range filter
		String startTimeFilterValue = getFilterValue(EventFilter.START_TIME, filters);
		String endTimeFilterValue = getFilterValue(EventFilter.END_TIME, filters);
		if ((startTimeFilterValue != null && !startTimeFilterValue.isEmpty())
				&& (endTimeFilterValue != null && !endTimeFilterValue.isEmpty())) {
			java.sql.Timestamp startTime = new java.sql.Timestamp(dateFormat.parse(startTimeFilterValue).getTime());
			java.sql.Timestamp endTime = new java.sql.Timestamp(dateFormat.parse(endTimeFilterValue).getTime());
			predicates.add(cb.between(event.<java.sql.Timestamp> get("timestamp"), startTime, endTime));
		} else if (startTimeFilterValue != null && !startTimeFilterValue.isEmpty()) {
			java.sql.Timestamp startTime = new java.sql.Timestamp(dateFormat.parse(startTimeFilterValue).getTime());
			predicates.add(cb.greaterThanOrEqualTo(event.<java.sql.Timestamp> get("timestamp"), startTime));
		} else if (endTimeFilterValue != null && !endTimeFilterValue.isEmpty()) {
			java.sql.Timestamp endTime = new java.sql.Timestamp(dateFormat.parse(endTimeFilterValue).getTime());
			predicates.add(cb.lessThanOrEqualTo(event.<java.sql.Timestamp> get("timestamp"), endTime));
		}

		cq.where(cb.and(predicates.toArray(new Predicate[predicates.size()])));

		TypedQuery<Event> q = em.createQuery(cq);
		return q.getResultList();
	}

	public String getFilterValue(EventFilter kind, Map<String, String> filters) {
		String filterValue = null;
		if (filters != null) {
			try {
				filterValue = filters.get(kind.getName());
			} catch (NullPointerException e) {
				// suppress and ignore
			}
		}
		return filterValue;
	}
}
