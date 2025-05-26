package com.sgupta.analytics

import com.sgupta.analytics.builder.AnalyticsEventBuilder
import com.sgupta.analytics.model.EventType
import org.junit.Assert.*
import org.junit.Test

/**
 * Unit tests for AnalyticsEventBuilder
 */
class AnalyticsEventBuilderTest {

    @Test
    fun `build event with required fields`() {
        val event = AnalyticsEventBuilder()
            .setScreenName("TestScreen")
            .setEventType(EventType.CLICK)
            .setEventName("test_event")
            .build()

        assertEquals("TestScreen", event.screenName)
        assertEquals(EventType.CLICK, event.eventType)
        assertEquals("test_event", event.eventName)
        assertTrue(event.parameters.isEmpty())
        assertNull(event.userId)
        assertTrue(event.timestamp > 0)
    }

    @Test
    fun `build event with parameters`() {
        val params = mapOf("key1" to "value1", "key2" to 123)
        
        val event = AnalyticsEventBuilder()
            .setScreenName("TestScreen")
            .setEventName("test_event")
            .addParameters(params)
            .addParameter("key3", true)
            .build()

        assertEquals(3, event.parameters.size)
        assertEquals("value1", event.parameters["key1"])
        assertEquals(123, event.parameters["key2"])
        assertEquals(true, event.parameters["key3"])
    }

    @Test
    fun `build event with user ID`() {
        val event = AnalyticsEventBuilder()
            .setScreenName("TestScreen")
            .setEventName("test_event")
            .setUserId("user123")
            .build()

        assertEquals("user123", event.userId)
    }

    @Test
    fun `build event with custom timestamp`() {
        val customTimestamp = 1234567890L
        
        val event = AnalyticsEventBuilder()
            .setScreenName("TestScreen")
            .setEventName("test_event")
            .setTimestamp(customTimestamp)
            .build()

        assertEquals(customTimestamp, event.timestamp)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `build event without screen name throws exception`() {
        AnalyticsEventBuilder()
            .setEventName("test_event")
            .build()
    }

    @Test(expected = IllegalArgumentException::class)
    fun `build event without event name throws exception`() {
        AnalyticsEventBuilder()
            .setScreenName("TestScreen")
            .build()
    }

    @Test
    fun `screen view builder creates correct event`() {
        val event = AnalyticsEventBuilder.screenView("HomeScreen").build()

        assertEquals("HomeScreen", event.screenName)
        assertEquals(EventType.SCREEN_VIEW, event.eventType)
        assertEquals("screen_view", event.eventName)
    }

    @Test
    fun `button click builder creates correct event`() {
        val event = AnalyticsEventBuilder.buttonClick("HomeScreen", "subscribe").build()

        assertEquals("HomeScreen", event.screenName)
        assertEquals(EventType.CLICK, event.eventType)
        assertEquals("button_click", event.eventName)
        assertEquals("subscribe", event.parameters["button_name"])
    }

    @Test
    fun `item click builder creates correct event`() {
        val event = AnalyticsEventBuilder.itemClick("ListScreen", "item123", "Test Item").build()

        assertEquals("ListScreen", event.screenName)
        assertEquals(EventType.CLICK, event.eventType)
        assertEquals("item_click", event.eventName)
        assertEquals("item123", event.parameters["item_id"])
        assertEquals("Test Item", event.parameters["item_name"])
    }

    @Test
    fun `search builder creates correct event`() {
        val event = AnalyticsEventBuilder.search("SearchScreen", "android").build()

        assertEquals("SearchScreen", event.screenName)
        assertEquals(EventType.SEARCH, event.eventType)
        assertEquals("search", event.eventName)
        assertEquals("android", event.parameters["search_term"])
    }

    @Test
    fun `share builder creates correct event`() {
        val event = AnalyticsEventBuilder.share("DetailScreen", "article", "twitter").build()

        assertEquals("DetailScreen", event.screenName)
        assertEquals(EventType.SHARE, event.eventType)
        assertEquals("share", event.eventName)
        assertEquals("article", event.parameters["content_type"])
        assertEquals("twitter", event.parameters["method"])
    }
} 