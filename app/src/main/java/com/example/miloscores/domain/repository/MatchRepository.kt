package com.example.miloscores.domain.repository

import com.example.miloscores.domain.model.Match
import com.example.miloscores.domain.model.MatchStatus
import kotlinx.coroutines.flow.Flow

interface MatchRepository {
    fun getMatches(): Flow<List<Match>>
    fun getMatchById(id: String): Flow<Match>
    suspend fun refreshMatches()
    suspend fun updateMatchScore(matchId: String, homeScore: Int, awayScore: Int)
    suspend fun updateMatchStatus(matchId: String, status: MatchStatus)
} 