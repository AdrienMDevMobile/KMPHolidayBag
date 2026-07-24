package m.adrien.kmpholiday.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import m.adrien.kmpholiday.data.converter.toPreview
import m.adrien.kmpholiday.domain.HolidayBagReminder
import m.adrien.kmpholiday.domain.HolidayBagReminderPreviews
import m.adrien.kmpholiday.domain.repository.HolidayBagReminderPreviewsRepository

class HolidayBagReminderPreviewsRepositoryStaticImpl : HolidayBagReminderPreviewsRepository {
    override fun get(): Flow<HolidayBagReminderPreviews> = flow {
        emit(
            StaticDatas.listOfHolidayBagReminder
                .map { it.toPreview() }
        )
    }

    override suspend fun create(holidayBagReminder: HolidayBagReminder): Boolean {
        TODO("Not yet implemented")
    }
}