package m.adrien.kmpholiday

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import m.adrien.kmpholiday.data.impl.cache.HolidayBagReminderCacheFactory
import m.adrien.kmpholiday.di.initKoin

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)

        // Initialize Koin
        initKoin()

        // Initialize the cache factory with application context
        HolidayBagReminderCacheFactory.init(applicationContext)
        
        // Initialize SettingsRepository with application context
        m.adrien.kmpholiday.data.impl.SettingsRepositoryImpl.init(applicationContext)

        setContent {
            App()
        }
    }
}