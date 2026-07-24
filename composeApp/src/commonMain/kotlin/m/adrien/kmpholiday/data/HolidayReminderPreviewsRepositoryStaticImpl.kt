package m.adrien.kmpholiday.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import m.adrien.kmpholiday.data.converter.toPreview
import m.adrien.kmpholiday.domain.HolidayBagReminder
import m.adrien.kmpholiday.domain.HolidayReminderPreviews
import m.adrien.kmpholiday.domain.repository.HolidayReminderPreviewsRepository

class HolidayReminderPreviewsRepositoryStaticImpl : HolidayReminderPreviewsRepository {
    override fun get(): Flow<HolidayReminderPreviews> = flow {
        emit(
            StaticDatas.listOfHolidayReminder
                .map { it.toPreview() }
        )
    }

    override suspend fun create(holidayBagReminder: HolidayBagReminder): Boolean {
        TODO("Not yet implemented")
    }
}