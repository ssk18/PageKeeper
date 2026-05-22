# PageKeeper

A reading-library Android app built as a study of modern Android architecture: Material 3 theming, Jetpack Compose, Hilt, multi-module clean architecture, and NowInAndroid-style Gradle convention plugins.

## Tech Stack

| Concern | Choice |
|---|---|
| Language | Kotlin 2.3.21 |
| UI | Jetpack Compose · Material 3 · Compose BOM 2026.05.01 |
| Architecture | Clean Architecture (data / domain / presentation), no use cases |
| DI | Hilt 2.59.2 (KSP) |
| Async | Kotlin Coroutines 1.11.0 · Flow |
| Navigation | Navigation 3 (1.1.2) |
| Build | AGP 9.1.1 · Gradle 9.3.1 · KSP 2.3.8 · `build-logic` convention plugins |
| Fonts | Lora + Inter via Google Fonts downloadable provider |
| Min / Target / Compile SDK | 26 / 36 / 36 |

## Convention Plugins

The `build-logic` composite build keeps every module's `build.gradle.kts` small. Each module just picks the conventions it needs:

| Plugin | Applied in | What it does |
|---|---|---|
| `pagekeeper.android.application` | `:app` | `com.android.application`, SDK levels, Java 17 toolchain |
| `pagekeeper.android.library` | `:core:data` | `com.android.library`, SDK levels, Java 17 toolchain |
| `pagekeeper.android.library.compose` | `:core:designsystem` | Android library + Compose compiler + BOM/UI deps |
| `pagekeeper.jvm.library` | `:core:domain` | Plain Kotlin/JVM with Java 17 |
| `pagekeeper.hilt` | `:app`, `:core:data` | Applies Hilt + KSP and the Hilt deps |

Add a new plugin in `build-logic/convention/src/main/kotlin/`, then register it in `build-logic/convention/build.gradle.kts`'s `gradlePlugin { plugins { ... } }` block.

## Material 3 Theming

The design tokens live in `:core:designsystem`:

- `Color.kt` — raw brand palette mapped onto a light `ColorScheme`; a dark scheme is derived using standard M3 inversion. Two brand-only roles (`stateFinished`, `stateAlert`) live in a `PageKeeperExtraColors` extension exposed via `MaterialTheme.extraColors`.
- `Type.kt` — `Lora` (titles) and `Inter` (body) downloaded via the Google Fonts provider; seven design-spec styles mapped onto M3 roles (headlineLarge / headlineMedium / titleLarge / bodyLarge / bodyMedium / labelLarge / bodySmall).
- `Theme.kt` — `PageKeeperTheme { ... }` wrapper that switches color schemes and provides the extra-colors composition local.

The Google Fonts cert array ships in `core/designsystem/src/main/res/values/font_certs.xml`.

## Build & Run

```bash
# Assemble debug APK
./gradlew :app:assembleDebug

# Run unit tests
./gradlew :app:testDebugUnitTest

# Lint
./gradlew :app:lintDebug

# Install on a connected device
./gradlew :app:installDebug
```

## Status

Scaffold only — theming, DI wiring, and the module skeleton are in place. Feature screens (library, book detail) come next.
