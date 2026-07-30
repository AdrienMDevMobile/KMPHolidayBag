package m.adrien.kmpholiday.data.impl

import kotlinx.coroutines.flow.Flow
import m.adrien.kmpholiday.data.SettingsData
import m.adrien.kmpholiday.domain.Settings
import m.adrien.kmpholiday.domain.repository.SettingsRepository

expect class SettingsRepositoryImpl() : SettingsRepository {
    override fun get(): Flow<Settings>
    override suspend fun setKeepScreenOn(value: Boolean): Boolean
}