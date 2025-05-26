# Analytics Module

## Overview

This analytics module provides a scalable, maintainable, and extensible solution for tracking user interactions and app events across multiple analytics providers. The module is designed with a clean architecture that allows easy integration of new analytics SDKs without modifying the feature layer code.

## Architecture

### Key Components

1. **AnalyticsManager** - The main interface for the feature layer
2. **AnalyticsProvider** - Interface for all analytics SDK implementations
3. **AnalyticsEvent** - Data model representing an analytics event
4. **AnalyticsEventBuilder** - Builder pattern for creating events
5. **AnalyticsConfig** - Configuration for providers and filtering

### Design Patterns

- **Strategy Pattern**: Different analytics providers implement the same interface
- **Builder Pattern**: Easy event creation with fluent API
- **Dependency Injection**: Hilt for managing dependencies
- **Coroutines**: Asynchronous event logging without blocking UI

## Features

- ✅ **Multi-Provider Support**: Firebase Analytics, Google Analytics, CleverTap, etc.
- ✅ **Event Filtering**: Send specific events to specific providers
- ✅ **Builder Pattern**: Easy event creation with validation
- ✅ **Async Logging**: Non-blocking event processing
- ✅ **Error Handling**: Graceful failure handling for each provider
- ✅ **Debug Mode**: Logging for development and testing
- ✅ **User Management**: User ID and properties tracking
- ✅ **Configuration**: Easy provider enablement/disablement

## Usage

### 1. Inject AnalyticsManager

```kotlin
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val analyticsManager: AnalyticsManager
) : ViewModel() {
    // Your code here
}
```

### 2. Log Events

#### Simple Events
```kotlin
// Screen view
analyticsManager.logScreenView("HomeScreen")

// Button click
analyticsManager.logButtonClick("HomeScreen", "refresh_button")

// Item click
analyticsManager.logItemClick("NewsListScreen", "news_123", "Breaking News")
```

#### Custom Events with Builder
```kotlin
analyticsManager.logEvent(
    AnalyticsEventBuilder()
        .setScreenName("HomeScreen")
        .setEventType(EventType.CLICK)
        .setEventName("custom_button_click")
        .addParameter("button_id", "subscribe_button")
        .addParameter("user_type", "premium")
)
```

#### Advanced Events
```kotlin
val event = AnalyticsEventBuilder()
    .setScreenName("CheckoutScreen")
    .setEventType(EventType.PURCHASE)
    .setEventName("subscription_purchase")
    .addParameter(AnalyticsParams.ITEM_ID, "premium_subscription")
    .addParameter(AnalyticsParams.VALUE, 9.99)
    .addParameter(AnalyticsParams.CURRENCY, "USD")
    .build()

analyticsManager.logEvent(event)
```

### 3. User Management

```kotlin
// Set user ID
analyticsManager.setUserId("user_12345")

// Set user properties
analyticsManager.setUserProperties(mapOf(
    "user_type" to "premium",
    "subscription_plan" to "monthly"
))

// Clear user data (logout)
analyticsManager.clearUserData()
```

## Adding New Analytics Providers

### 1. Create Provider Implementation

```kotlin
@Singleton
class CleverTapProvider @Inject constructor(
    @ApplicationContext private val context: Context
) : AnalyticsProvider {
    
    override suspend fun initialize() {
        // Initialize CleverTap SDK
    }
    
    override suspend fun logEvent(event: AnalyticsEvent) {
        // Log event to CleverTap
    }
    
    // Implement other methods...
}
```

### 2. Update Configuration

```kotlin
// In AnalyticsModule.kt
@Provides
@Singleton
fun provideAnalyticsConfig(): AnalyticsConfig {
    return AnalyticsConfig(
        firebaseEnabled = true,
        googleAnalyticsEnabled = false,
        cleverTapEnabled = true, // Enable new provider
        // ...
    )
}
```

### 3. Update AnalyticsManager

```kotlin
@Singleton
class AnalyticsManager @Inject constructor(
    private val firebaseProvider: FirebaseAnalyticsProvider,
    private val googleAnalyticsProvider: GoogleAnalyticsProvider,
    private val cleverTapProvider: CleverTapProvider, // Add new provider
    private val config: AnalyticsConfig
) {
    // Add to initialize method
    if (config.cleverTapEnabled) {
        activeProviders.add(cleverTapProvider)
    }
}
```

## Configuration

### Provider Configuration

```kotlin
AnalyticsConfig(
    firebaseEnabled = true,
    googleAnalyticsEnabled = false,
    cleverTapEnabled = false,
    debugMode = BuildConfig.DEBUG,
    eventFilterConfig = EventFilterConfig(
        // Send purchase events only to Firebase
        firebaseOnlyEvents = setOf("purchase", "subscription"),
        // Send marketing events only to CleverTap
        cleverTapOnlyEvents = setOf("marketing_campaign"),
        // Exclude test events
        excludedEvents = setOf("debug_event")
    )
)
```

### Event Filtering

Events can be filtered to send to specific providers:

- **firebaseOnlyEvents**: Events sent only to Firebase
- **googleAnalyticsOnlyEvents**: Events sent only to Google Analytics
- **cleverTapOnlyEvents**: Events sent only to CleverTap
- **excludedEvents**: Events not sent to any provider

## Best Practices

### 1. Event Naming
- Use snake_case for event names
- Be descriptive but concise
- Use consistent naming across your app

### 2. Parameters
- Use predefined constants from `AnalyticsParams`
- Keep parameter values simple (strings, numbers, booleans)
- Avoid sensitive information in parameters

### 3. Screen Names
- Use consistent screen naming convention
- Consider using class names or route names
- Keep names descriptive

### 4. Error Handling
- The module handles errors gracefully
- Failed events for one provider don't affect others
- Check logs in debug mode for issues

## Testing

### Unit Testing
```kotlin
@Test
fun `test event logging`() {
    val event = AnalyticsEventBuilder()
        .setScreenName("TestScreen")
        .setEventType(EventType.CLICK)
        .setEventName("test_event")
        .build()
    
    assertEquals("TestScreen", event.screenName)
    assertEquals(EventType.CLICK, event.eventType)
    assertEquals("test_event", event.eventName)
}
```

### Integration Testing
- Test with actual analytics providers in staging
- Verify events appear in analytics dashboards
- Test error scenarios and recovery

## Migration Guide

### From Direct Firebase Analytics
```kotlin
// Before
firebaseAnalytics.logEvent("button_click", Bundle().apply {
    putString("button_name", "subscribe")
})

// After
analyticsManager.logButtonClick("HomeScreen", "subscribe")
```

### From Multiple Direct Calls
```kotlin
// Before
firebaseAnalytics.logEvent("user_action", bundle)
cleverTap.pushEvent("user_action", properties)

// After
analyticsManager.logEvent(
    AnalyticsEventBuilder()
        .setEventName("user_action")
        .addParameters(properties)
)
```

## Troubleshooting

### Common Issues

1. **Events not appearing**: Check if provider is enabled in config
2. **App crashes**: Ensure all required dependencies are added
3. **Missing events**: Check event filtering configuration
4. **Performance issues**: Events are processed asynchronously

### Debug Mode
Enable debug mode to see detailed logs:
```kotlin
AnalyticsConfig(debugMode = true)
```

## Future Enhancements

- [ ] Real-time event validation
- [ ] Offline event queuing
- [ ] A/B testing integration
- [ ] Custom event schemas
- [ ] Analytics dashboard
- [ ] Event replay functionality 