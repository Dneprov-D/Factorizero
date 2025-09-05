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

rootProject.name = "Factorizero"
include(":app")
include(":core")
include(":core:designsystem")
include(":core:data")
include(":core:domain")
include(":core:model")
include(":core:navigation")
include(":core:network")
include(":core:ui")
include(":core:common")
include(":feature:master")
include(":feature:employee")
include(":feature:employee:main")
include(":feature:employee:profile")
include(":feature:master:main")
include(":feature")
include(":feature:authorization")
include(":feature:master:tasks")
include(":feature:master:profile")
