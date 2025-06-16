package com.example.miloscores.presentation.matches

import androidx.lifecycle.viewModelScope
import com.example.miloscores.domain.usecase.GetMatchesUseCase
import com.example.miloscores.presentation.base.MviViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

class MatchViewModel(
    private val getMatchesUseCase: GetMatchesUseCase
) : MviViewModel<MatchIntent, MatchState>() {

    override fun createInitialState() = MatchState()

    override fun handleIntent(intent: MatchIntent) {
        when (intent) {
            is MatchIntent.LoadMatches -> loadMatches()
            is MatchIntent.RefreshMatches -> refreshMatches()
            is MatchIntent.MatchClicked -> handleMatchClick(intent.matchId)
        }
    }

    private fun loadMatches() {
        setState { copy(isLoading = true, error = null) }
        
        getMatchesUseCase()
            .onEach { matches ->
                setState { copy(matches = matches, isLoading = false) }
            }
            .catch { error ->
                setState { copy(error = error.message, isLoading = false) }
            }
            .launchIn(viewModelScope)
    }

    private fun refreshMatches() {
        launchWithState {
            try {
                setState { copy(isLoading = true, error = null) }
                // Implement refresh logic
                setState { copy(isLoading = false) }
            } catch (e: Exception) {
                setState { copy(error = e.message, isLoading = false) }
            }
        }
    }

    private fun handleMatchClick(matchId: String) {
        // Handle match click - navigate to details or show more info
    }
} 