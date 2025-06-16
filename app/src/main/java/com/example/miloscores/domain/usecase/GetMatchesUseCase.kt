package com.example.miloscores.domain.usecase

import com.example.miloscores.domain.model.Match
import com.example.miloscores.domain.repository.MatchRepository
import kotlinx.coroutines.flow.Flow

class GetMatchesUseCase(
    private val repository: MatchRepository
) {
    operator fun invoke(): Flow<List<Match>> = repository.getMatches()
} 