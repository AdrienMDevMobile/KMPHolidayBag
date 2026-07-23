package m.adrien.kmpholiday.data.impl.cache

expect object HolidayReminderCacheFactory {
    fun createCache(): HolidayReminderInstanceCache
}