package uz.yuk24.driver.presentation.splash

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uz.yuk24.driver.R
import uz.yuk24.driver.presentation.navigation.Destinations
import uz.yuk24.driver.presentation.theme.Primary

@Composable
fun SplashScreen(
    onNavigate: (String) -> Unit,
    viewModel: SplashViewModel = hiltViewModel()
) {
    val destination by viewModel.destination.collectAsState()
    val isOffline by viewModel.isOffline.collectAsState()

    LaunchedEffect(destination) {
        when (destination) {
            SplashDestination.Login -> onNavigate(Destinations.LOGIN)
            SplashDestination.Dashboard -> onNavigate(Destinations.DASHBOARD)
            SplashDestination.Loading -> Unit
        }
    }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            CircularProgressIndicator(color = Primary)
            Spacer(modifier = Modifier.height(16.dp))
            Text(stringResource(R.string.loading), style = MaterialTheme.typography.bodyMedium)
            if (isOffline) {
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = stringResource(R.string.offline_banner),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.padding(horizontal = 24.dp)
                )
            }
        }
    }
}
