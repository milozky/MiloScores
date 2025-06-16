package com.example.miloscores.di

import com.example.miloscores.data.api.MatchApiService
import com.example.miloscores.data.repository.MatchRepositoryImpl
import com.example.miloscores.domain.repository.MatchRepository
import com.example.miloscores.domain.usecase.GetMatchesUseCase
import com.example.miloscores.presentation.matches.MatchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    // Add your dependencies here
}

val viewModelModule = module {
    viewModel { MatchViewModel(get()) }
}

val repositoryModule = module {
    single<MatchRepository> { MatchRepositoryImpl(get()) }
}

val useCaseModule = module {
    single { GetMatchesUseCase(get()) }
}

val dataModule = module {
    // Add your data sources here
} 