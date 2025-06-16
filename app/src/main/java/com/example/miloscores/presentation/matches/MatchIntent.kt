package com.example.miloscores.presentation.matches

import com.example.miloscores.presentation.base.MviIntent

sealed class MatchIntent : MviIntent {
    object LoadMatches : MatchIntent()
    object RefreshMatches : MatchIntent()
    data class MatchClicked(val matchId: String) : MatchIntent()
} 