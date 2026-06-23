package uz.yuk24.driver.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uz.yuk24.driver.data.local.SessionManager
import uz.yuk24.driver.presentation.dashboard.DashboardScreen
import uz.yuk24.driver.presentation.login.LoginScreen
import uz.yuk24.driver.presentation.reviews.ReviewsScreen
import uz.yuk24.driver.presentation.splash.SplashScreen

@Composable
fun DriverNavHost(sessionManager: SessionManager) {
    val navController = rememberNavController()

    LaunchedEffect(sessionManager) {
        sessionManager.logoutEvents.collect {
            navController.navigate(Destinations.LOGIN) {
                popUpTo(0) { inclusive = true }
            }
        }
    }

    NavHost(navController = navController, startDestination = Destinations.SPLASH) {
        composable(Destinations.SPLASH) {
            SplashScreen(
                onNavigate = { route ->
                    navController.navigate(route) {
                        popUpTo(Destinations.SPLASH) { inclusive = true }
                    }
                }
            )
        }
        composable(Destinations.LOGIN) {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(Destinations.DASHBOARD) {
                        popUpTo(Destinations.LOGIN) { inclusive = true }
                    }
                }
            )
        }
        composable(Destinations.DASHBOARD) {
            DashboardScreen(
                onNavigateToReviews = { navController.navigate(Destinations.REVIEWS) },
                onLogout = {
                    navController.navigate(Destinations.LOGIN) {
                        popUpTo(0) { inclusive = true }
                    }
                }
            )
        }
        composable(Destinations.REVIEWS) {
            ReviewsScreen(onBack = { navController.popBackStack() })
        }
    }
}
