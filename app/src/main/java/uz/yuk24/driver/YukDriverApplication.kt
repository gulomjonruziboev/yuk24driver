package uz.yuk24.driver

import android.app.Application
import android.content.res.Configuration as AndroidConfiguration
import dagger.hilt.android.HiltAndroidApp
import org.osmdroid.config.Configuration
import java.util.Locale

@HiltAndroidApp
class YukDriverApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Configuration.getInstance().userAgentValue = packageName
        setDefaultLocale(Locale("uz"))
    }

    private fun setDefaultLocale(locale: Locale) {
        Locale.setDefault(locale)
        val config = AndroidConfiguration(resources.configuration)
        config.setLocale(locale)
        @Suppress("DEPRECATION")
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}
