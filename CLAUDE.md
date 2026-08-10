# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project

Kotlin Multiplatform (Compose Multiplatform) app targeting Android, iOS, and Desktop (JVM). It helps users
track packing lists ("holiday bag reminders") for trips, with checkable items and a per-trip duration.
Single module: `composeApp`.

## Common commands

Run all commands from the repo root.

- Desktop app: `./gradlew :composeApp:run`
- Android debug build: `./gradlew :composeApp:assembleDebug`
- iOS: open `/iosApp` in Xcode and run from there (Compose UI is shared, but the iOS app entry point/SwiftUI shell lives here)
- JVM unit tests: `./gradlew :composeApp:jvmTest`
- All tests (all targets): `./gradlew :composeApp:allTests`
- Single test class (JVM): `./gradlew :composeApp:jvmTest --tests "m.adrien.kmpholiday.ComposeAppCommonTest"`
- Android lint: `./gradlew :composeApp:lint`
- Full check (tests + lint): `./gradlew :composeApp:check`

Common (`commonTest`) tests run on the JVM target via `jvmTest`; there's also `iosSimulatorArm64Test` for iOS
and `connectedAndroidTest` for on-device/emulator Android instrumentation tests.

Android is the primary development/testing target — assume that's the platform in use unless told otherwise.
Write `commonTest` coverage for new repository/ViewModel logic; the existing test suite is currently a
placeholder, but new logic should raise that bar rather than match it.

Write code comments and commit messages in English (a few legacy French comments exist in the data layer;
don't follow that precedent for new code).

## Architecture

### expect/actual platform split

The app compiles for `androidMain`, `iosMain`, `jvmMain`, sharing everything possible in `commonMain`.
Platform-specific behavior is implemented via Kotlin's `expect`/`actual` mechanism in matching file paths
across source sets (e.g. `util/PlatformUtils.kt`, `util/ScreenKeepOn.kt`,
`data/impl/cache/HolidayBagReminderCacheFactory.kt`, `data/impl/SettingsRepositoryImpl.kt`,
`di/KoinInitializer.kt`). When adding a new platform-dependent capability, follow this same pattern: declare
`expect` in `commonMain`, implement `actual` in each of the three platform source sets.

Each platform's entry point wires up Koin and any context-dependent singletons before rendering `App()`:
- Android (`MainActivity.kt`): `initKoin()`, then `HolidayBagReminderCacheFactory.init(context)` and
  `SettingsRepositoryImpl.init(context)` (Android's DataStore-backed cache/settings need a `Context`).
- JVM (`main.kt`): just `initKoin()`, then opens a Compose `Window`.
- iOS (`MainViewController.kt`): equivalent Koin init before returning the UIViewController.

### Dependency injection (Koin)

- `di/AppModule.kt` (`appModule`) declares everything platform-agnostic: repositories that don't need a
  platform context, and ViewModels that don't need `SavedStateHandle`.
- Each platform has its own module (`createAndroidModule()`, `createIosModule()`, `createJvmModule()`) for
  what can't live in common code — e.g. `SettingsRepository` (needs platform storage) and
  `HolidayBagReminderViewModel` (needs `SavedStateHandle`, currently only wired on Android).
- `initKoin()` in each platform's `KoinInitializer.kt` starts Koin with `appModule + create<Platform>Module()`.

### Domain layer

- `domain/` holds pure data classes and repository interfaces (`HolidayBagReminderRepository`,
  `SettingsRepository`, `HolidayBagReminderPreviewsRepository`) — no platform or framework dependencies.

### Data layer

- `data/impl/` holds the common implementations. `HolidayBagReminderRepositoryImpl` combines static trip
  definitions (`StaticDatas.listOfHolidayBagReminder`, hardcoded packing-list templates) with a per-instance
  cache holding user state (checked items, chosen duration) — trip *content* is currently static, only the
  user's progress through a trip is mutable/cached. `HolidayBagReminderRepository.edit()` is a stub that
  always returns `false`, and `HolidayBagReminderViewModel` has commented-out item-editing methods
  (`updateHolidayName`, `addItem`, `removeItem`, etc.) — user-editable items/trips are on the roadmap but not
  yet implemented, so expect to build this out rather than treat the stub as final.
- The cache (`HolidayBagReminderInfosInstanceCache`) is obtained via the `expect object
  HolidayBagReminderCacheFactory`: Android uses a DataStore-backed implementation
  (`DataStoreHolidayBagReminderInfosInstanceCache`), other platforms fall back to
  `InMemoryHolidayBagReminderInfosInstanceCache` (in-memory only, not persisted — see TODO in that file).
- `data/converter/` converts between `data/*Data` (serializable/storage shapes) and `domain/*` models.

### View layer

Organized by screen under `view/<screen>/`, each with a `ViewModel`, a `Screen` composable, and a
`value/` subpackage for UI state classes and domain-to-UiState converters (e.g.
`view/holidayBag/value/HolidayConverterExt.kt`). Reusable pieces for a screen go in a `component/`
subpackage (e.g. `view/holidayBag/component/`). Cross-screen shared composables live in `view/shared/`
(`LoadingPage`, `ErrorPage`, `NavigationEvent`).

ViewModels expose a single `StateFlow<XxxUiState>` (sealed class: `Loading` / `Value` / `Error`) built by
combining repository flows via `stateIn(... SharingStarted.WhileSubscribed(5_000) ...)`, plus a separate
`StateFlow<List<NavigationEvent>>` for one-off navigation events (see `HolidayBagReminderViewModel`).

### Navigation

Type-safe Compose Navigation defined in `App.kt` using `@Serializable` route objects (`Holidays`,
`Holiday(holidayId)`, `Settings`) and a single `NavHost`.

### Strings

UI strings are in Compose Multiplatform resources (`composeApp/src/commonMain/composeResources/values/strings.xml`),
not hardcoded in composables — add new user-facing text there. `androidMain/res/values/strings.xml` only
holds the Android app name.
