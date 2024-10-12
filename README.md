<h1 align="center"> Kakao Mobility Andorid </h1>

## Design

-Figma

## Development

### Required

- IDE: Android Studio Koala (AGP : 8.6.1)
- Java Version: Java 17
- Kotlin Language: 2.0.20

### Libraries

- AndroidX
    - Activity & Activity Compose
    - AppCompat
    - Core
    - Lifecycle & ViewModel Compose
- Kotlin Libraries
    - Coroutine
    - Serialization
- Compose
    - Material3
    - Navigation
- Dagger & Hilt
- Squre (Ktor, OkHttp)

#### Gradle Dependecy

Gradle [Version Catalog](/gradle/libs.versions.toml) 를 활용하여 종속성과 플러그인을 관리하고 있습니다.

## Architecture

![Architecture](/document/Architecture.png)

### Module

Multi-module로 구성하였으며, 각 Feature마다 모듈 형태로 구성했습니다.

![Module](/document/Module.png)
