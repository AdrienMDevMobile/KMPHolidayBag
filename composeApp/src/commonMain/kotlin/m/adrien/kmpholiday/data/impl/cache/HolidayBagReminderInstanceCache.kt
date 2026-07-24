package m.adrien.kmpholiday.data.impl.cache

interface HolidayBagReminderInstanceCache {
    fun getReminderInstance(holidayId: String): Int?
    fun saveReminderInstance(holidayId: String, duration: Int)
}