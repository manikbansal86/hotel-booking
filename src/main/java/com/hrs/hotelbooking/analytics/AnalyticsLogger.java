package com.hrs.hotelbooking.analytics;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Utility class to log application activity events for analytics.
 * Keeps event logs consistent and easy to filter.
 */
public class AnalyticsLogger {

    private static final Logger logger = LoggerFactory.getLogger("AnalyticsLogger");

    /**
     * Logs a structured analytics event with name and details.
     *
     * @param eventName Name of the analytics event (e.g., "booking_created")
     * @param details   Optional key-value info (e.g., "userId=1 hotelId=2")
     */
    public static void log(String eventName, String details) {
        logger.info("analytics_event={} {}", eventName, details);
    }

    /**
     * Overload to allow logging with just event name.
     */
    public static void log(String eventName) {
        logger.info("analytics_event={}", eventName);
    }
}
