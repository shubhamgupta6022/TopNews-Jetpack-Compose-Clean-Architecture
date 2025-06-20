pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "TopNews"
include(":app")
include(":domain")
include(":feature:core")
include(":feature:composite")
include(":data:composite")
include(":data:network")
include(":util:core")
include(":benchmark")
include(":navigation")
include(":analytics")
include(":media")
