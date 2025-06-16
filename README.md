# MiloScores 🏆

A modern Android application that keeps you updated with live match scores and notifications. Built with clean architecture principles and modern Android development practices.

## Features

- 📊 Live match scores
- 🔔 Real-time match notifications
- 🎯 Match end alerts
- 🌙 Dark/Light theme support
- 📱 Material Design 3

## Tech Stack & Open-source libraries

- 100% [Kotlin](https://kotlinlang.org/) based + [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) for asynchronous operations
- [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html) approach
- [MVI](https://medium.com/swlh/mvi-architecture-with-android-fcde123e3c4a) (Model-View-Intent) architecture pattern
- [Koin](https://insert-koin.io/) for dependency injection
- [Jetpack Compose](https://developer.android.com/jetpack/compose) for UI
- [Material Design 3](https://m3.material.io/) for modern UI components
- [Retrofit2 & OkHttp3](https://github.com/square/retrofit) for RESTful API
- [Room](https://developer.android.com/training/data-storage/room) for local database
- [DataStore](https://developer.android.com/topic/libraries/architecture/datastore) for data persistence
- [WorkManager](https://developer.android.com/topic/libraries/architecture/workmanager) for background tasks
- [Hilt](https://developer.android.com/training/dependency-injection/hilt-android) for dependency injection
- [Coil](https://coil-kt.github.io/coil/) for image loading
- [Timber](https://github.com/JakeWharton/timber) for logging

## Architecture

This app follows Clean Architecture principles and MVI pattern:

```
app
├── data
│   ├── api
│   ├── db
│   ├── repository
│   └── model
├── domain
│   ├── model
│   ├── repository
│   └── usecase
├── presentation
│   ├── ui
│   ├── viewmodel
│   └── state
└── di
```

### Clean Architecture Layers

1. **Presentation Layer**
   - UI components (Compose)
   - ViewModels
   - UI State
   - MVI implementation

2. **Domain Layer**
   - Business logic
   - Use cases
   - Domain models
   - Repository interfaces

3. **Data Layer**
   - Repository implementations
   - Data sources (Remote & Local)
   - Data models
   - API services

## SOLID Principles Implementation

- **Single Responsibility**: Each class has a single responsibility
- **Open/Closed**: Use of interfaces and abstractions
- **Liskov Substitution**: Proper inheritance and interface implementation
- **Interface Segregation**: Specific interfaces for different use cases
- **Dependency Inversion**: Dependency injection with Koin

## Getting Started

### Prerequisites

- Android Studio Hedgehog | 2023.1.1 or later
- JDK 17 or later
- Android SDK 34 or later

### Installation

1. Clone the repository
```bash
git clone https://github.com/milozky/MiloScores.git
```

2. Open the project in Android Studio

3. Build and run the app

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details

## Acknowledgments

- [Android Jetpack](https://developer.android.com/jetpack)
- [Material Design](https://m3.material.io/)
- [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Koin](https://insert-koin.io/) 