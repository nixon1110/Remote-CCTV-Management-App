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
    maven(
        url = "https://maven.google.com"
    )
    maven (
        url = "https://jitpack.io"
    )
    /*maven(url = "https://dl.bintray.com/amulyakhare/maven")*/

    gradlePluginPortal()
}
}
dependencyResolutionManagement {
repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
    google()
    mavenCentral()
    maven ( url ="https://jitpack.io")
    }
}

rootProject.name = "Push Notification"
include(":app")