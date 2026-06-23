package uz.yuk24.driver

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import uz.yuk24.driver.data.local.SessionManager
import uz.yuk24.driver.presentation.navigation.DriverNavHost
import uz.yuk24.driver.presentation.theme.SurfaceWhite
import uz.yuk24.driver.presentation.theme.Yuk24DriverTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    @Inject lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            Yuk24DriverTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = SurfaceWhite) {
                    DriverNavHost(sessionManager = sessionManager)
                }
            }
        }
    }
}
