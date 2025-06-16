package com.example.miloscores.domain.model

data class Match(
    val id: String,
    val homeTeam: String,
    val awayTeam: String,
    val homeScore: Int,
    val awayScore: Int,
    val status: MatchStatus,
    val startTime: Long,
    val endTime: Long?
)

enum class MatchStatus {
    SCHEDULED,
    LIVE,
    FINISHED,
    CANCELLED
} 