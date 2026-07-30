package m.adrien.kmpholiday.domain.repository

import kotlinx.coroutines.flow.Flow
import m.adrien.kmpholiday.domain.Settings

interface SettingsRepository {
    fun get(): Flow<Settings>
    suspend fun setKeepScreenOn(value: Boolean): Boolean
}