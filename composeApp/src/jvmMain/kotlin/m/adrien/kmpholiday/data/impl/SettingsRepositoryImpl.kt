package m.adrien.kmpholiday.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import m.adrien.kmpholiday.domain.Settings

actual class SettingsRepositoryImpl actual constructor() : m.adrien.kmpholiday.domain.repository.SettingsRepository {
    private val _settingsFlow = MutableStateFlow(Settings(keepScreenOn = false))
    
    actual override fun get(): Flow<Settings> {
        return _settingsFlow.asStateFlow()
    }

    actual override suspend fun setKeepScreenOn(value: Boolean): Boolean {
        _settingsFlow.update { currentSettings ->
            currentSettings.copy(keepScreenOn = value)
        }
        return true
    }
}