package m.adrien.kmpholiday.data.impl.cache

actual object HolidayBagReminderCacheFactory {
    actual fun createCache(): HolidayBagReminderInfosInstanceCache {
        return InMemoryHolidayBagReminderInfosInstanceCache()
    }
}