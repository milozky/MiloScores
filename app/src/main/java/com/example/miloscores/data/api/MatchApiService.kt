package com.example.miloscores.data.api

import retrofit2.http.GET
import retrofit2.http.Query

interface MatchApiService {
    @GET("fixtures")
    suspend fun getLiveMatches(
        @Query("live") live: String = "all"
    ): ApiResponse<List<MatchResponse>>

    @GET("fixtures")
    suspend fun getMatchesByDate(
        @Query("date") date: String
    ): ApiResponse<List<MatchResponse>>
}

data class ApiResponse<T>(
    val get: String,
    val parameters: Map<String, String>,
    val errors: List<String>,
    val results: Int,
    val paging: Paging,
    val response: T
)

data class Paging(
    val current: Int,
    val total: Int
)

data class MatchResponse(
    val fixture: FixtureInfo,
    val league: League,
    val teams: Teams,
    val goals: Goals,
    val score: Score
)

data class FixtureInfo(
    val id: Int,
    val timestamp: Long,
    val status: Status
)

data class Status(
    val long: String,
    val short: String,
    val elapsed: Int?
)

data class League(
    val id: Int,
    val name: String,
    val country: String,
    val logo: String,
    val flag: String?,
    val season: Int,
    val round: String
)

data class Teams(
    val home: Team,
    val away: Team
)

data class Team(
    val id: Int,
    val name: String,
    val logo: String,
    val winner: Boolean?
)

data class Goals(
    val home: Int,
    val away: Int
)

data class Score(
    val halftime: Goals,
    val fulltime: Goals,
    val extratime: Goals?,
    val penalty: Goals?
) 