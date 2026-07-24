package m.adrien.kmpholiday.domain.repository

import kotlinx.coroutines.flow.Flow
import m.adrien.kmpholiday.domain.HolidayBagReminder
import m.adrien.kmpholiday.domain.HolidayBagReminderPreviews

interface HolidayBagReminderPreviewsRepository {
    fun get(): Flow<HolidayBagReminderPreviews>
    suspend fun create(holidayBagReminder: HolidayBagReminder): Boolean
}