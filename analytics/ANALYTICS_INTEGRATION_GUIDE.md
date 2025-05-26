# Analytics Integration Guide

This guide explains how to properly integrate analytics tracking into your Compose screens and components using the **AnalyticsManager interface**.

## Architecture Overview

- **`AnalyticsManager`** - Interface defining all analytics operations
- **`AnalyticsManagerImpl`** - Production implementation with full provider orchestration
- **`MockAnalyticsManager`** - Mock implementation for previews and testing

## Quick Setup for Previews

For **`@Preview` composables**, always use `MockAnalyticsManager()`:

```kotlin
@Preview
@Composable
private fun MyScreenPreview() {
    MyScreen(
        // ... other parameters
        analyticsManager = MockAnalyticsManager()
    )
}
```

## Best Practice: Required Parameter Approach

**Recommended**: Make `analyticsManager` a required parameter for better testability and explicit dependency management:

```kotlin
@Composable
fun MyScreen(
    analyticsManager: AnalyticsManager // Required parameter - RECOMMENDED
) {
    // Track screen view automatically
    analyticsManager.TrackScreenView(
        screenName = AnalyticsScreens.MY_SCREEN,
        additionalProperties = mapOf(
            "feature_name" to "my_feature",
            "user_type" to "premium"
        )
    )
    
    // Your UI components with analytics
    Button(
        onClick = {
            analyticsManager.logButtonClick(
                screenName = AnalyticsScreens.MY_SCREEN,
                buttonName = "action_button",
                buttonType = "primary"
            )
            // Your click handling
        }
    ) {
        Text("Action")
    }
}

@Preview
@Composable
private fun MyScreenPreview() {
    MyScreen(analyticsManager = MockAnalyticsManager())
}
```

## ViewModel Integration (Optional)

If you need AnalyticsManager in your ViewModel:

```kotlin
@HiltViewModel
class MyScreenViewModel @Inject constructor(
    private val analyticsManager: AnalyticsManager,
    // ... other dependencies
) : ViewModel() {

    fun onUserAction() {
        analyticsManager.logButtonClick(
            screenName = AnalyticsScreens.MY_SCREEN,
            buttonName = "vm_action"
        )
    }
}
```

## Available Analytics Functions

### Screen Tracking
```kotlin
// Automatic screen view tracking with lifecycle awareness
analyticsManager.TrackScreenView(
    screenName = AnalyticsScreens.HOME_SCREEN,
    additionalProperties = mapOf("section" to "news_feed")
)
```

### User Interactions
```kotlin
// Button clicks
analyticsManager.logButtonClick(
    screenName = AnalyticsScreens.HOME_SCREEN,
    buttonName = "search_button",
    buttonType = "navigation"
)

// News article clicks
analyticsManager.logNewsArticleClick(
    screenName = AnalyticsScreens.HOME_SCREEN,
    title = "Article Title",
    url = "https://example.com/article",
    source = "BBC News",
    category = "technology",
    position = 0
)

// Search queries
analyticsManager.logSearchQuery(
    screenName = AnalyticsScreens.SEARCH_SCREEN,
    query = "latest news",
    resultsCount = 25
)

// AI Assistant interactions
analyticsManager.logAIAssistantMessage(
    screenName = AnalyticsScreens.AI_ASSISTANT_BOTTOM_SHEET,
    messageLength = 50,
    conversationTurn = 3
)

// Error tracking
analyticsManager.logError(
    screenName = AnalyticsScreens.HOME_SCREEN,
    errorType = "api_error",
    errorMessage = "Failed to load news"
)
```

### Custom Events
```kotlin
analyticsManager.logEvent(
    AnalyticsEventBuilder()
        .setScreenName(AnalyticsScreens.HOME_SCREEN)
        .setEventType(EventType.CUSTOM)
        .setEventName("custom_event_name")
        .addParameter("custom_param", "custom_value")
)
```

## Constants Usage

### Screen Names
```kotlin
import com.sgupta.analytics.constants.AnalyticsScreens

AnalyticsScreens.HOME_SCREEN
AnalyticsScreens.SEARCH_SCREEN
AnalyticsScreens.NEWS_LIST_SCREEN
AnalyticsScreens.AI_ASSISTANT_BOTTOM_SHEET
```

### Event Names
```kotlin
import com.sgupta.analytics.constants.AnalyticsEvents

AnalyticsEvents.BUTTON_CLICKED
AnalyticsEvents.NEWS_ARTICLE_CLICKED
AnalyticsEvents.SEARCH_PERFORMED
AnalyticsEvents.AI_MESSAGE_SENT
```

### Properties
```kotlin
import com.sgupta.analytics.constants.AnalyticsProperties

AnalyticsProperties.NEWS_TITLE
AnalyticsProperties.SEARCH_QUERY
AnalyticsProperties.BUTTON_NAME
AnalyticsProperties.LIST_POSITION
```

## Dependency Injection Setup

The Hilt module automatically provides the interface binding:

```kotlin
@Module
@InstallIn(SingletonComponent::class)
abstract class AnalyticsModule {
    
    @Binds
    @Singleton
    abstract fun bindAnalyticsManager(
        analyticsManagerImpl: AnalyticsManagerImpl
    ): AnalyticsManager
}
```

Inject in ViewModels or other classes:

```kotlin
@HiltViewModel
class MyViewModel @Inject constructor(
    private val analyticsManager: AnalyticsManager
) : ViewModel()
```

## Best Practices

1. **Always use MockAnalyticsManager in @Preview composables**
2. **Make analyticsManager a required parameter for better testability**
3. **Use consistent screen names from AnalyticsScreens object**
4. **Log user interactions immediately when they happen**
5. **Include relevant context in additionalProperties**
6. **Track errors for debugging and monitoring**
7. **Use meaningful event and parameter names**
8. **Avoid logging sensitive user data**

## Testing

### Unit Tests
```kotlin
@Test
fun `test screen behavior`() {
    val mockAnalytics = MockAnalyticsManager()
    val viewModel = MyViewModel(mockAnalytics)
    // Your test code
}
```

### Compose Tests
```kotlin
@Test
fun `test composable analytics`() {
    val mockAnalytics = MockAnalyticsManager()
    
    composeTestRule.setContent {
        MyScreen(analyticsManager = mockAnalytics)
    }
    
    // Test interactions and verify analytics calls
}
```

## Migration from Class to Interface

If updating existing code:

1. Replace `AnalyticsManager` class references with interface
2. Update Hilt injection to use `AnalyticsManagerImpl`
3. Use `MockAnalyticsManager()` in previews
4. Consider making analyticsManager a required parameter 