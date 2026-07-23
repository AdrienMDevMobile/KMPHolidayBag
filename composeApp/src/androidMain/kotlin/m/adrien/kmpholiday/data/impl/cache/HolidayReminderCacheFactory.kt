package m.adrien.kmpholiday.data.impl.cache

import android.content.Context

actual object HolidayReminderCacheFactory {
    private lateinit var applicationContext: Context
    
    fun init(context: Context) {
        applicationContext = context.applicationContext
    }
    
    actual fun createCache(): HolidayReminderInstanceCache {
        if (!::applicationContext.isInitialized) {
            throw IllegalStateException("HolidayReminderCacheFactory must be initialized with a Context first")
        }
        return DataStoreHolidayReminderInstanceCache(applicationContext)
    }
}