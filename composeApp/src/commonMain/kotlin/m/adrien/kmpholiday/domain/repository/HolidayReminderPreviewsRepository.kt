package m.adrien.kmpholiday.domain.repository

import kotlinx.coroutines.flow.Flow
import m.adrien.kmpholiday.domain.HolidayBagReminder
import m.adrien.kmpholiday.domain.HolidayReminderPreviews

interface HolidayReminderPreviewsRepository {
    fun get(): Flow<HolidayReminderPreviews>
    suspend fun create(holidayBagReminder: HolidayBagReminder): Boolean
}