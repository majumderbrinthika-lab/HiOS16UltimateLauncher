# HiOS 16 Ultimate Launcher — Starter Project

- Minimum Android: 5.0 (API 21)
- Language: Kotlin
- UI: Native Android Views (XML-free starter screen built programmatically)
- Build: GitHub Actions, JDK 17, Gradle

## Build
Push this project to a GitHub repository. The workflow at `.github/workflows/android.yml`
builds a debug APK. Download it from the Actions run's Artifacts section.

This is the first working foundation, not the finished launcher. Later steps will add
an icon grid, dock, wallpaper picker, app drawer, widgets, gestures, and settings.
Lock-screen replacement is restricted by Android; only permitted shortcuts/styles
will be implemented.
