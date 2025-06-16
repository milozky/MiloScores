package com.example.miloscores.data.repository

import com.example.miloscores.data.api.MatchApiService
import com.example.miloscores.domain.model.Match
import com.example.miloscores.domain.model.MatchStatus
import com.example.miloscores.domain.repository.MatchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.*

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
                    startTime = matchResponse.fixture.timestamp * 1000L, // Convert to milliseconds
                    endTime = null // We don't have this information in the API response
                )
            }
            emit(matches)
        } catch (e: Exception) {
            // For now, emit empty list on error
            emit(emptyList())
        }
    }

    override fun getMatchById(id: String): Flow<Match> = flow {
        // TODO: Implement single match fetch
        emit(
            Match(
                id = id,
                homeTeam = "Home Team",
                awayTeam = "Away Team",
                homeScore = 0,
                awayScore = 0,
                status = MatchStatus.SCHEDULED,
                startTime = System.currentTimeMillis(),
                endTime = null
            )
        )
    }

    override suspend fun refreshMatches() {
        // Refresh is handled by the Flow
    }

    override suspend fun updateMatchScore(matchId: String, homeScore: Int, awayScore: Int) {
        // Updates are handled by the API
    }

    override suspend fun updateMatchStatus(matchId: String, status: MatchStatus) {
        // Status updates are handled by the API
    }
} 