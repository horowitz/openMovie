# Android modernisation implementation plan

> **For agentic workers:** Use superpowers:executing-plans to implement the approved scope in this session.

**Goal:** Upgrade the Android toolchain, dependencies and CI together while preserving the movie discovery and details flows.

**Architecture:** Retain the existing Groovy multimodule build. Centralise plugin and dependency versions in `gradle/libs.versions.toml`, adopt AGP 9 built-in Kotlin, and retain JVM 21 consistently across modules and CI.

**Tech stack:** Published stable AGP 9, Gradle 9, Kotlin 2.4, KSP 2, Hilt, Compose BOM, Retrofit 3, OkHttp 5 and Coil 3.

**Spec:** Scope approved in conversation on 2026-11-02: upgrade the compatible toolchain, standardise JDK 21, introduce a version catalog and Compose BOM, migrate obsolete dependencies, update CI and verify builds/tests/lint/instrumentation compilation.

## Global constraints

- Use stable, explicitly pinned dependency versions verified against Maven repositories.
- Compile with SDK 37; retain app minimum SDK 27 and align internal Android libraries to it.
- Use JDK/JVM 21 locally and in CI.
- Preserve existing architecture and behaviour; change APIs only where upgrades require it.
- Do not create branches from local master. No branch or worktree is needed for this clean checkout.

## Review focus

- Plugin compatibility: Gradle configuration and task discovery must succeed.
- Serialization/network compatibility: existing MockWebServer integration tests must pass.
- Hilt code generation: production and instrumentation APKs must compile.
- Resource ownership and insets: use module-owned R classes and native Compose insets.
- Coverage/CI: reports must work headlessly and emulator tests must use a supported JDK.

## Tasks

- [x] Capture the current build baseline with JDK 21.
- [x] Add the version catalog; update root/settings/wrapper and all module build scripts; verify Gradle configuration.
- [x] Replace obsolete serialization, image-loading, navigation generation, insets and test APIs; run existing unit tests and assemble production/instrumentation APKs.
- [x] Modernise coverage, CI and README; run lint, review the complete diff and record verification results.

## Execution notes

Use existing regression tests and actual Gradle tasks to validate dependency/configuration migrations. No tests that merely repeat pinned version numbers. Work remains uncommitted for user review.


## Migration findings

- Baseline Gradle configuration succeeded, with Kotlin/KSP mismatch warnings and deprecated global BuildConfig configuration.
- Replaced Quadrant with explicit navigation destinations, Accompanist with Compose insets, Coil 1 with Coil 3, the Jake Wharton serialization converter with Retrofit's converter, and legacy Mockito/Android Support testing dependencies with maintained APIs.
- Removed Jetifier, legacy resource flags, unused dependencies and test support from release runtime dependencies.
- Corrected existing test expectations: Discover's error assertion used java.lang.Error, and movie-detail UI assertions expected a numeric rating although the screen displays stars.
- Android default configuration must precede feature configuration so Hilt's CustomTestRunner is retained.
- Device tests run sequentially because modules share an emulator and a MockWebServer port. Coverage reports use Hilt-transformed classes and run after connected tests when both are scheduled.

## Verification

- `./gradlew connectedDebugAndroidTest jacocoTestReport --no-parallel --console=plain`: passed on the existing API 37 emulator. Seven device tests passed; two pre-existing ignored tests remain skipped.
- Twenty-one debug unit tests passed, including network integration tests and the new AndroidX OkHttp idling adapter tests.
- Coverage XML contains executed lines for app, discovery and movie details, without Hilt class mismatch warnings.
- Independent review completed; identified dependency visibility issues were corrected.
- API 27 instrumentation is configured in CI; it was not run locally.
- `./gradlew build assembleDebugAndroidTest jacocoTestReport --console=plain --warning-mode all`: final full build passed, including debug/release APKs, lint, unit tests and instrumentation APK compilation. Existing non-fatal lint/deprecation warnings remain.
- `git diff --check`: passed.


## Android Studio sync follow-up

Android Studio Preview runs Gradle with JBR 25. The earlier CLI verification
explicitly selected Android Studio stable's JBR 21, so Gradle discovered the
current JVM as its Java 21 toolchain. With JBR 25, sync instead failed while
resolving `:libraries:network:compileJava.javaCompiler`: no discoverable Java 21
installation and no toolchain download repositories.

Reproduced using Android Studio Preview's JBR 25 and a temporary Gradle task that
resolves `compileJava.javaCompiler`. Added the official Foojay toolchain resolver
settings plugin. The same check now resolves Java 21 successfully, provisioning
it into the Gradle toolchain cache. Kept Android Studio's JDK setting unchanged.


Also generated `gradle/gradle-daemon-jvm.properties` with
`updateDaemonJvm --jvm-version=21`, so IDE and command-line builds use Java 21
for Gradle itself as well as compilation. Verified `./gradlew --version` with
the IDE's JBR 25 launcher reports Java 21 daemon criteria. Parallel module builds exposed lint Kotlin-analysis crashes, including a missing
KSP-generated Hilt factory; isolated lint and sequential builds passed. Added an
explicit Android-test lint dependency on KSP and reverted the parallel module
execution introduced by the upgrade. Final verification forces all tasks to run
with the shared Java 21 daemon configuration.

Final IDE-environment verification: launched with Android Studio Preview JBR 25,
`./gradlew build assembleDebugAndroidTest --rerun-tasks --console=plain` passed
(all 868 tasks executed), including lint and all 21 unit tests. Actual IDE UI
sync was not controlled by the agent; the matching compiler-resolution failure
was reproduced and fixed through Gradle.


## Android Studio AGP compatibility follow-up

The installed Android Studio Preview reports a maximum supported AGP of
9.5.0-alpha09, which excludes the newer stable 9.5.0. Pinned the version catalog
to stable AGP 9.4.1, within the installed IDE's range. Kept Gradle 9.6.0 and SDK
37, supported by AGP 9.4. Updated README to make IDE compatibility explicit.

AGP 9.4.1 verification: `./gradlew build assembleDebugAndroidTest --console=plain`
passed using Android Studio Preview's launcher and the Java 21 daemon, including
unit tests, lint, debug/release builds and instrumentation APK compilation.
