<h1 align="center"> Kakao Mobility Andorid </h1>

## Design

<p align ="center">
 <img alt="" src ="/document/screen/splash.png" width="110" heigth="50" />
 <img alt="" src ="/document/screen/홈.png" width="110" heigth="50" />
 <img alt="" src ="/document/screen/홈-1.png" width="110" heigth="50" />
 <img alt="" src ="/document/screen/홈-2.png" width="110" heigth="50" />
 <img alt="" src ="/document/screen/지도.png" width="110" heigth="50" />
</p>

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

#### Gradle Dependency

Gradle [Version Catalog](/gradle/libs.versions.toml) 를 활용하여 종속성과 플러그인을 관리하고 있습니다.

## Architecture

![Architecture](/document/Architecture.png)

### Module

Multi-module로 구성하였으며, 각 Feature마다 모듈 형태로 구성했습니다.
클린아키텍처에 기반해 구현했습니다.

![Module](/project.dot.png)

### Presentation Layer

MVW (Model-View-Whatever)

- LocationsScreen(출발지 / 도착지 출력 화면)은 MVI패턴으로 구현 하였습니다. (단방향 아키텍처)
- 맵 화면은 MVVM 패턴으로 구현 하였습니다.

![Presentation](/document/PresentationLayer.png)

## Detail

### Build

프로젝트에 맞춰서 구현했습니다.
Custom Convention Plugin을 작성하여 플러그인을 관리하였습니다.
버전 관리는 [gradle.properties](gradle.properties)에서 하고 있습니다.

### Architecture

Clean Architecture 기반으로 구성하여 Data -> Domain <- Presentation 도메인을 바라보게 의존성을 설정하였습니다

[Remote](/core/remote/src/main/java/kr/co/remote/)모듈에서 Network 데이터를 Ktor를 사용하여 받습니다.
Kotlin serialization 통해 데이터를 파싱합니다.

- 위도 경도 데이터의 경우 별도의 [시리얼라이저](/core/remote/src/main/java/kr/co/remote/serializer/PointSerializer.kt)를
  만들어 파싱합니다.
- 데이터를
  파싱하기전에 [Mapper](/core/remote/src/main/java/kr/co/remote/implementation/LocationRemoteDataSourceImpl.kt)
  를 extention으로 만들어 ApiResponse로 감싸줍니다.

Remote <- data 데이터 모듈에서 리모트 모듈을 받아 도메인 모델로 매핑합니다.

domain 데이터 모듈의 경우 model 모듈을 따로 만들어 관리합니다.
domain 모듈은 repository 인터페이스와, UseCase로 구성됩니다.

### Memory Cache

- Lru 캐시를 구현하여, Routes(경로 조회 API)의 목록을 캐싱하고 있습니다.
- UseCase를 구현부를 분리하여 캡슐화 한뒤 Lru캐시를 적용해 CachedUseCase를 만들었습니다.
- Mutex를 사용하여 동시에 여러번 접근이 불가하게 만들었습니다.

### Navigation

Compose의 Navigation에서 사용할 Route를 Navigation 모듈로 분리해 관리합니다.

### Main

LocationsScreen과, MapScreen을 navigation을 관리합니다.
SnackBar를 관리하고 있습니다.

### Locations Screen

- MVI 패턴으로 구성하였습니다.
- Compose로 구현 하였습니다
- Compose 구조는 기본적으로 Route와 Screen으로 분리하여, 상태 호이스팅 하고 있습니다.
- viewModel은 데이터 스트림을 Flow로 관리합니다.
- 사용자 event를 SharedFlow의 Intent로 emit 하여 작업합니다.
- Flow Extension을 사용하여 파이프라인을 공유해 state Model의 상태를 관리합니다.
    - [이곳에서](/feature/location/src/main/java/kr/co/location/model)Intent와 UiState를 확인할 수 있습니다.
- Error처리는 ApiResponse 를 mapping하여 처리합니다.
    - [ApiResponse](/core/common/src/main/java/kr/co/common/model/ApiResponse.kt)
      를 [EntityWrapper](/core/common/src/main/java/kr/co/common/model/EntityWrapper.kt)
      로 [ApiMapper](/core/common/src/main/java/kr/co/common/mapper/ApiMapper.kt)를 통해서 Mapping합니다.
- 알수없는 에러인경우 snackBar를 호출하여 띄워줍니다. (LocationsScreen이
  아닌 [MainScreen](/feature/main/src/main/java/kr/co/main/MainScreen.kt)에서 snackBar를 관리합니다.)
    - MapScreen의경우 에러 발생시 Map이 출력되면 안된다 판단하여, popBackStack으로 돌아가 MainScreen에서 Snakbar를 호출하게 구현하였습니다.
    - 그렇기 때문에 SnackBar관리를 전부 LocationsScreen에서 따로 하는게 아닌 MainScreen에서 관리하였습니다

### Map Screen

- Compose로 구현 하였습니다.
- MVVM 패턴으로 구현 하였습니다.
- State 클래스를 뷰모델 내부에선언, UI State Holder로 사용하고 있습니다.
- KaKaoMap의 경우 지도 API 라이프 사이클을 Compose에 맞게 lifecycleOwner를 사용하여 관리하였습니다.
- KakaoMap은 AndroidView를 사용하여 Compose에서
  구현하였고 [KakaoMapConfigure](/feature/map/src/main/java/kr/co/map/service/KakaoMapConfigure.kt)에서
  설정하고 있습니다.

## Secret

local.properties에서 키를 관리하고 있습니다.

- KAKAO_MAP_KEY=0123263e-977f-4124-b773-520ac2634c65
- KAKAO_NATIVE_KEY=1b0965c85a2fb289050a0fb13411621b

//누구에게도 공유하지 않았습니다.
