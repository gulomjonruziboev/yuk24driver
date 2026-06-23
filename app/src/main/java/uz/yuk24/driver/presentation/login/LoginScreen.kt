package uz.yuk24.driver.presentation.login

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocalShipping
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import uz.yuk24.driver.R
import uz.yuk24.driver.presentation.theme.Primary
import uz.yuk24.driver.presentation.theme.TextSecondary

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val loginSuccess by viewModel.loginSuccess.collectAsState()

    LaunchedEffect(loginSuccess) {
        if (loginSuccess) onLoginSuccess()
    }

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(48.dp))
            Icon(Icons.Default.LocalShipping, contentDescription = null, tint = Primary)
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "YUK 24",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = stringResource(R.string.driver_mode),
                style = MaterialTheme.typography.labelMedium,
                color = TextSecondary
            )
            Spacer(modifier = Modifier.height(32.dp))
            Text(
                text = stringResource(R.string.driver_login),
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.SemiBold
            )
            Spacer(modifier = Modifier.height(24.dp))

            OutlinedTextField(
                value = uiState.username,
                onValueChange = viewModel::onUsernameChange,
                label = { Text(stringResource(R.string.driver_username)) },
                placeholder = { Text(stringResource(R.string.driver_username_placeholder)) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            )
            Spacer(modifier = Modifier.height(12.dp))
            OutlinedTextField(
                value = uiState.password,
                onValueChange = viewModel::onPasswordChange,
                label = { Text(stringResource(R.string.driver_password)) },
                placeholder = { Text(stringResource(R.string.driver_password_placeholder)) },
                singleLine = true,
                visualTransformation = if (uiState.passwordVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation()
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(onDone = { viewModel.login() }),
                trailingIcon = {
                    IconButton(onClick = viewModel::togglePasswordVisibility) {
                        Icon(
                            if (uiState.passwordVisible) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                            contentDescription = null
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                enabled = !uiState.isLoading
            )

            uiState.errorMessage?.let { error ->
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = when (error) {
                        "invalid" -> stringResource(R.string.driver_login_error)
                        "inactive" -> stringResource(R.string.driver_inactive_account)
                        "rate_limited" -> stringResource(R.string.driver_rate_limited)
                        "network" -> stringResource(R.string.network_error)
                        else -> error
                    },
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = viewModel::login,
                enabled = !uiState.isLoading &&
                    uiState.username.isNotBlank() &&
                    uiState.password.isNotBlank(),
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.height(20.dp),
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(stringResource(R.string.driver_login_btn))
                }
            }
        }
    }
}
