package m.adrien.kmpholiday.data.impl.cache

actual object HolidayReminderCacheFactory {
    actual fun createCache(): HolidayReminderInstanceCache {
        return InMemoryHolidayReminderInstanceCache()
    }
}