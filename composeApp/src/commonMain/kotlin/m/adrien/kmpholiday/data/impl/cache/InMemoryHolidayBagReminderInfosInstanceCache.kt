package m.adrien.kmpholiday.data.impl.cache

import m.adrien.kmpholiday.data.HolidayReminderInstanceData

//TODO PlaceHolder, version others than Android don't use real cache
class InMemoryHolidayBagReminderInfosInstanceCache : HolidayBagReminderInfosInstanceCache {
    private val reminderDataMap = mutableMapOf<String, HolidayReminderInstanceData>()
    
    override fun getReminderInstance(holidayId: String): HolidayReminderInstanceData? {
        return reminderDataMap[holidayId]
    }
    
    override fun saveReminderInstance(holidayId: String, reminderData: HolidayReminderInstanceData) {
        reminderDataMap[holidayId] = reminderData
    }
}