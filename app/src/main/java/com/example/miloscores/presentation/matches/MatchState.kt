package com.example.miloscores.presentation.matches

import com.example.miloscores.domain.model.Match
import com.example.miloscores.presentation.base.MviState

data class MatchState(
    val matches: List<Match> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
) : MviState 