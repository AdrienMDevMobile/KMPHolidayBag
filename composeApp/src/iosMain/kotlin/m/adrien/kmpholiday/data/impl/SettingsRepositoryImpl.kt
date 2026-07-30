package m.adrien.kmpholiday.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import m.adrien.kmpholiday.domain.Settings
import platform.Foundation.NSUserDefaults
import platform.Foundation.standardUserDefaults

actual class SettingsRepositoryImpl actual constructor() : m.adrien.kmpholiday.domain.repository.SettingsRepository {
    private val userDefaults = NSUserDefaults.standardUserDefaults
    private val _settingsFlow = MutableStateFlow(createSettingsFromUserDefaults())
    
    actual override fun get(): Flow<Settings> {
        return _settingsFlow.asStateFlow()
    }

    actual override suspend fun setKeepScreenOn(value: Boolean): Boolean {
        userDefaults.setBool(value, "keep_screen_on")
        _settingsFlow.update { currentSettings ->
            currentSettings.copy(keepScreenOn = value)
        }
        return true
    }
    
    private fun createSettingsFromUserDefaults(): Settings {
        val keepScreenOn = userDefaults.boolForKey("keep_screen_on")
        return Settings(keepScreenOn = keepScreenOn)
    }
}