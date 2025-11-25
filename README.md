# TransferMe — Jetpack Compose App

A Jetpack Compose Android app built to demonstrate pixel-accurate UI, animations, and modern app architecture. The UI is based on a Figma design and the project focuses on clean Compose patterns, navigation, theming and interaction handling.

## Highlights

- Design source: Figma (frames and specs used to reproduce visual layout and spacing)
- Tech: Kotlin, Jetpack Compose, AndroidX Navigation for Compose
- Single-activity, Compose-first architecture
- Bottom navigation with selectable state and optional hide-on-scroll behavior
- Locale-aware number formatting (system default locale) with thousands grouping
- Transparent navigation gestures area (system navigation bar gestures color settable to transparent)
- Centralized route observation to react to navigation changes

## Project structure (high level)

- app/src/main/java/com/cequea/transferme/
  - ui/navigation/ — navigation host, AppScreens, NavigationViewModel
  - ui/components/ — shared composables (BottomBar, BottomBarItem, etc.)
  - ui/screens/ — feature screens (Home, Wallet, Chart, Profile, etc.)
  - MainActivity.kt — activity entrypoint and window/insets handling
- app/src/main/res/ — resources (drawables, layouts, values)

Key files to inspect right away:
- ui/navigation/AppNavigation.kt — NavHost setup and centralized coordination (route detection, bottom bar visibility)
- ui/navigation/NavigationViewModel.kt — shared ViewModel used to show/hide UI chrome (bottom bar) and other global state
- ui/navigation/AppScreens.kt — sealed class describing screens (immutable; do not modify if used across serialization in your app)
- ui/components/BottomBar.kt — bottom navigation composable, handles isSelected states and animations

## Running the app

1. Open this project in Android Studio (Arctic Fox or newer recommended)
2. Build and run on a device or emulator
   - From terminal: ./gradlew assembleDebug
   - Or use the Run action in Android Studio

---

This README was generated with AI.

Design reference (Figma): https://www.figma.com/design/LsEN9DoypcT6Kj08sZGPd4/Transferme-Banking-Financial-Full-APP-Ui-Template-Free-57-plus-screen--Community-?node-id=0-1&p=f&t=lJ92lqVhEzn2o5DK-0
