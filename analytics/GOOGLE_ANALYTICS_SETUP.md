# Google Analytics Provider Setup Guide

## Overview

The `GoogleAnalyticsProvider` implements classic Google Analytics (Universal Analytics) using the Google Play Services Analytics SDK. 

> **⚠️ Important Notice**: Universal Analytics was sunset on July 1, 2023. This provider is maintained for backward compatibility only. For new projects, use Firebase Analytics (GA4) instead.

## Setup Instructions

### 1. Enable Google Analytics in Configuration

Update your `AnalyticsConfig` in `AnalyticsModule.kt`:

```kotlin
@Provides
@Singleton
fun provideAnalyticsConfig(): AnalyticsConfig {
    return AnalyticsConfig(
        firebaseEnabled = true,
        googleAnalyticsEnabled = true, // Enable Google Analytics
        cleverTapEnabled = false,
        debugMode = true,
        enableCrashReporting = true,
        enablePerformanceMonitoring = true,
        eventFilterConfig = EventFilterConfig(
            // Send specific events only to Google Analytics
            googleAnalyticsOnlyEvents = setOf("legacy_tracking", "custom_metric"),
            // Events to exclude from Google Analytics
            excludedEvents = setOf("debug_event", "test_event")
        )
    )
}
```

### 2. Set Your Tracking ID

Open `GoogleAnalyticsProvider.kt` and replace the placeholder tracking ID:

```kotlin
companion object {
    // Replace with your actual Google Analytics tracking ID
    private const val TRACKING_ID = "UA-123456789-1" // Your GA tracking ID
}
```

### 3. Create Google Analytics Property

1. Go to [Google Analytics](https://analytics.google.com/)
2. Create a new property (if you don't have one)
3. Set up a Universal Analytics view
4. Get your tracking ID (format: `UA-XXXXXX-Y`)

## Usage Examples

### Basic Event Logging

```kotlin
// Through AnalyticsManager (automatically routes to enabled providers)
analyticsManager.logEvent(
    AnalyticsEventBuilder()
        .setEventName("button_click")
        .setEventType(EventType.CLICK)
        .setScreenName("home_screen")
        .addParameter("button_name", "search")
        .addParameter("section", "header")
)
```

### Screen View Tracking

```kotlin
analyticsManager.logEvent(
    AnalyticsEventBuilder()
        .setEventName("screen_view")
        .setEventType(EventType.SCREEN_VIEW)
        .setScreenName("product_details")
        .addParameter("product_id", "12345")
)
```

### Custom Events with Categories

```kotlin
analyticsManager.logEvent(
    AnalyticsEventBuilder()
        .setEventName("video_play")
        .setEventType(EventType.CUSTOM)
        .addParameter(AnalyticsParams.EVENT_CATEGORY, "Video")
        .addParameter(AnalyticsParams.EVENT_LABEL, "Product Demo")
        .addParameter(AnalyticsParams.VALUE, 1)
)
```

### E-commerce Tracking

```kotlin
analyticsManager.logEvent(
    AnalyticsEventBuilder()
        .setEventName("purchase")
        .setEventType(EventType.CUSTOM)
        .addParameter(AnalyticsParams.EVENT_CATEGORY, "Ecommerce")
        .addParameter("transaction_id", "TXN-12345")
        .addParameter(AnalyticsParams.VALUE, 29.99)
        .addParameter("currency", "USD")
)
```

## Features & Limitations

### Supported Features

✅ **Screen View Tracking**: Automatic screen view logging
✅ **Event Tracking**: Custom events with categories, actions, and labels
✅ **User Properties**: Limited user property support via custom dimensions
✅ **Custom Dimensions**: Up to 20 custom dimensions per hit
✅ **Custom Metrics**: Up to 20 custom metrics per hit
✅ **Exception Tracking**: Automatic exception reporting
✅ **Campaign Tracking**: Automatic campaign attribution
✅ **Offline Support**: Local queuing and dispatching

### Limitations

❌ **Enhanced E-commerce**: Limited e-commerce tracking compared to GA4
❌ **Real-time Reports**: Limited real-time capabilities
❌ **Event Parameters**: Only 20 custom dimensions/metrics per hit
❌ **Data Retention**: Subject to Universal Analytics data retention limits
❌ **Future Updates**: No new features (sunset product)

## Parameter Mapping

The provider automatically maps parameters to Google Analytics format:

| Analytics Param | Google Analytics Mapping |
|----------------|-------------------------|
| `screen_name` | Screen Name |
| `event_category` | Event Category |
| `event_label` | Event Label |
| `value` | Event Value |
| `search_term` | Event Label (for search events) |
| `button_name` | Event Label (for click events) |
| Custom parameters | Custom Dimensions/Metrics |

## Custom Dimensions Setup

To use custom dimensions effectively:

1. **Configure in Google Analytics**: 
   - Go to Admin → Property → Custom Definitions → Custom Dimensions
   - Create dimensions for your key parameters

2. **Map in Code**:
   ```kotlin
   // In GoogleAnalyticsProvider.addCustomParameters()
   when (key.lowercase()) {
       "user_type" -> builder.setCustomDimension(1, value.toString())
       "app_version" -> builder.setCustomDimension(2, value.toString())
       "user_segment" -> builder.setCustomDimension(3, value.toString())
       // Add more mappings as needed
   }
   ```

## Testing & Debugging

### Enable Debug Mode

```kotlin
// In AnalyticsConfig
debugMode = true
```

### Verify Events in Google Analytics

1. **Real-time Reports**: Go to Real-time → Events in Google Analytics
2. **Debug View**: Use Google Analytics Debugger browser extension
3. **Logs**: Check Android logcat for GoogleAnalyticsProvider logs

### Test Implementation

```kotlin
class AnalyticsTest {
    @Test
    fun testGoogleAnalyticsEvents() {
        val mockAnalytics = MockAnalyticsManager()
        
        mockAnalytics.logEvent(
            AnalyticsEventBuilder()
                .setEventName("test_event")
                .setEventType(EventType.CUSTOM)
                .addParameter("test_param", "test_value")
        )
        
        // Verify mock behavior
    }
}
```

## Migration to Firebase Analytics (GA4)

When ready to migrate to GA4:

1. **Enable Firebase**: Set `firebaseEnabled = true` in config
2. **Disable Google Analytics**: Set `googleAnalyticsEnabled = false`
3. **Update Event Structure**: GA4 has different event parameter limitations
4. **Test Both**: Run both providers temporarily for comparison

### Migration Checklist

- [ ] Set up Firebase project and GA4 property
- [ ] Configure Firebase Analytics Provider
- [ ] Test event tracking in GA4
- [ ] Update custom dimensions mapping
- [ ] Train team on GA4 interface differences
- [ ] Disable Universal Analytics provider

## Troubleshooting

### Common Issues

**Events not appearing in Google Analytics**:
- Check tracking ID is correct
- Verify Google Play Services is available on device
- Check network connectivity
- Ensure `setAnalyticsEnabled(true)` is called

**Custom dimensions not working**:
- Verify custom dimensions are configured in GA property
- Check dimension index mapping in `addCustomParameters()`
- Ensure dimension scope matches usage (hit, session, user, product)

**Performance issues**:
- Increase dispatch period: `setLocalDispatchPeriod(60)`
- Reduce custom parameters per event
- Use manual dispatch with `flush()` for critical events

### Support

For implementation issues:
1. Check Android logcat for GoogleAnalyticsProvider logs
2. Use Google Analytics Debugger extension
3. Refer to [Google Analytics SDK documentation](https://developers.google.com/analytics/devguides/collection/android/v4)

For migration help:
1. Review [GA4 migration guide](https://support.google.com/analytics/answer/9744165)
2. Test Firebase Analytics Provider alongside Google Analytics
3. Compare data between Universal Analytics and GA4 reports 