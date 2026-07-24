package m.adrien.kmpholiday.data.impl.cache

import m.adrien.kmpholiday.data.HolidayReminderInstanceData

interface HolidayBagReminderInfosInstanceCache {
    fun getReminderInstance(holidayId: String): HolidayReminderInstanceData?
    fun saveReminderInstance(holidayId: String, reminderData: HolidayReminderInstanceData)
}