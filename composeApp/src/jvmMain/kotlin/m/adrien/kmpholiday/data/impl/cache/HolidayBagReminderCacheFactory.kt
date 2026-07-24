package m.adrien.kmpholiday.data.impl.cache

actual object HolidayBagReminderCacheFactory {
    actual fun createCache(): HolidayBagReminderInstanceCache {
        return InMemoryHolidayBagReminderInstanceCache()
    }
}