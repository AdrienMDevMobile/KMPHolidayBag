package m.adrien.kmpholiday.data.impl.cache

expect object HolidayBagReminderCacheFactory {
    fun createCache(): HolidayBagReminderInfosInstanceCache
}