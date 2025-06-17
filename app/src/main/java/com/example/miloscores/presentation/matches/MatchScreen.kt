package com.example.miloscores.presentation.matches

import androidx.compose.foundation.gestures.detectVerticalDragGestures
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.miloscores.domain.model.Match
import com.example.miloscores.domain.model.MatchStatus
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MatchScreen(
    viewModel: MatchViewModel = koinViewModel()
) {
    val matches by viewModel.matches.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val pullRefreshState = rememberPullToRefreshState()
    var dragOffset by remember { mutableStateOf(0f) }

    LaunchedEffect(pullRefreshState.isRefreshing) {
        if (pullRefreshState.isRefreshing) {
            viewModel.loadMatches()
            pullRefreshState.endRefresh()
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .pointerInput(Unit) {
                detectVerticalDragGestures(
                    onVerticalDrag = { change, dragAmount ->
                        if (dragAmount > 0) { // Only allow downward drag
                            dragOffset += dragAmount
                            change.consume()
                        }
                    },
                    onDragEnd = {
                        if (dragOffset > 100f) { // Threshold for refresh
                            pullRefreshState.startRefresh()
                        }
                        dragOffset = 0f
                    }
                )
            }
    ) {
        when {
            error != null -> {
                ErrorState(
                    error = error!!,
                    onRetry = { viewModel.loadMatches() }
                )
            }
            matches.isEmpty() -> {
                EmptyState()
            }
            else -> {
                MatchList(matches = matches)
            }
        }

        // Custom pull indicator
        if (dragOffset > 0) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(dragOffset.coerceAtMost(100f).dp)
                    .align(Alignment.TopCenter)
            ) {
                CircularProgressIndicator(
                    modifier = Modifier
                        .size(40.dp)
                        .align(Alignment.Center)
                )
            }
        }

        PullToRefreshContainer(
            state = pullRefreshState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

@Composable
private fun ErrorState(
    error: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Error",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.error
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = error,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = onRetry
        ) {
            Text("Retry")
        }
    }
}

@Composable
private fun EmptyState() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "No Live Matches",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = "There are currently no live matches. Check back later!",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun MatchList(matches: List<Match>) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(matches) { match ->
            MatchItem(match = match)
        }
    }
}

@Composable
private fun MatchItem(match: Match) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = match.homeTeam,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = match.homeScore.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = match.awayTeam,
                    style = MaterialTheme.typography.titleMedium
                )
                Text(
                    text = match.awayScore.toString(),
                    style = MaterialTheme.typography.titleMedium
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = match.status.name,
                style = MaterialTheme.typography.bodyMedium,
                color = when (match.status) {
                    MatchStatus.LIVE -> MaterialTheme.colorScheme.primary
                    MatchStatus.FINISHED -> MaterialTheme.colorScheme.secondary
                    MatchStatus.CANCELLED -> MaterialTheme.colorScheme.error
                    MatchStatus.SCHEDULED -> MaterialTheme.colorScheme.tertiary
                }
            )
        }
    }
} 