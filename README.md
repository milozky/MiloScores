# MiloScores 🏆

A modern Android application that keeps you updated with live match scores and notifications. Built with clean architecture principles and modern Android development practices.

![video_2025-06-16_19-11-06](https://github.com/user-attachments/assets/ce0bad4c-3c40-42d4-8eb3-280dc7e2b5f8)


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

The app follows Clean Architecture principles with a clear separation of concerns:

### Data Flow

```
API (MatchApiService)
    ↓
Repository (MatchRepositoryImpl)
    ↓
ViewModel (MatchViewModel)
    ↓
UI (MatchScreen)
```

### Layer Details

1. **API Layer** (`MatchApiService.kt`):
   - Interface defining API endpoints
   - Uses Retrofit for HTTP requests
   - Handles raw API responses
   ```kotlin
   interface MatchApiService {
       @GET("fixtures")
       suspend fun getLiveMatches(
           @Query("live") live: String = "all"
       ): ApiResponse<List<MatchResponse>>
   }
   ```

2. **Repository Layer** (`MatchRepositoryImpl.kt`):
   - Middleman between API and app
   - Converts API data to domain models
   - Handles error cases
   ```kotlin
   class MatchRepositoryImpl(
       private val apiService: MatchApiService
   ) : MatchRepository {
       override fun getMatches(): Flow<List<Match>> = flow {
           try {
               val response = apiService.getLiveMatches()
               val matches = response.response.map { matchResponse ->
                   Match(
                       id = matchResponse.fixture.id.toString(),
                       homeTeam = matchResponse.teams.home.name,
                       awayTeam = matchResponse.teams.away.name,
                       homeScore = matchResponse.goals.home,
                       awayScore = matchResponse.goals.away,
                       status = when (matchResponse.fixture.status.short) {
                           "1H", "2H", "HT" -> MatchStatus.LIVE
                           "FT" -> MatchStatus.FINISHED
                           "CANC" -> MatchStatus.CANCELLED
                           else -> MatchStatus.SCHEDULED
                       },
                       startTime = matchResponse.fixture.timestamp * 1000L,
                       endTime = null
                   )
               }
               emit(matches)
           } catch (e: Exception) {
               emit(emptyList())
           }
       }
   }
   ```

3. **ViewModel Layer** (`MatchViewModel.kt`):
   - Manages UI state
   - Handles data operations
   - Provides data to UI
   ```kotlin
   class MatchViewModel(
       private val getMatchesUseCase: GetMatchesUseCase
   ) : ViewModel() {
       private val _matches = MutableStateFlow<List<Match>>(emptyList())
       val matches: StateFlow<List<Match>> = _matches.asStateFlow()

       init {
           viewModelScope.launch {
               getMatchesUseCase().collect { matches ->
                   _matches.value = matches
               }
           }
       }
   }
   ```

4. **UI Layer** (`MatchScreen.kt`):
   - Displays data to user
   - Handles user interactions
   - Uses Jetpack Compose
   ```kotlin
   @Composable
   fun MatchScreen(
       viewModel: MatchViewModel = koinViewModel()
   ) {
       val matches by viewModel.matches.collectAsState()
       
       LazyColumn {
           items(matches) { match ->
               MatchItem(match = match)
           }
       }
   }
   ```

## Key Features

- Live match scores
- Match status tracking
- Clean, modern UI with Jetpack Compose
- Reactive updates using Kotlin Flow
- Error handling at each layer
- Dependency injection with Koin

## Setup

1. Clone the repository
2. Create a `local.properties` file in the root directory
3. Add your API key:
   ```properties
   FOOTBALL_API_KEY=your_api_key_here
   ```
4. Build and run the project

## Dependencies

- Kotlin Coroutines
- Jetpack Compose
- Retrofit
- Koin
- Kotlin Flow
- Material Design 3

## Architecture Benefits

- **Separation of Concerns**: Each layer has a specific responsibility
- **Testability**: Layers can be tested independently
- **Maintainability**: Changes in one layer don't affect others
- **Scalability**: Easy to add new features or modify existing ones
- **Error Handling**: Built-in error handling at each level

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit your changes
4. Push to the branch
5. Create a Pull Request

## License

This project is licensed under the MIT License - see the LICENSE file for details

## Acknowledgments

- [Android Jetpack](https://developer.android.com/jetpack)
- [Material Design](https://m3.material.io/)
- [Kotlin Coroutines](https://github.com/Kotlin/kotlinx.coroutines)
- [Koin](https://insert-koin.io/) 
