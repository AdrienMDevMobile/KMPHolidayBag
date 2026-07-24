package m.adrien.kmpholiday.data.impl.cache

import android.content.Context

actual object HolidayBagReminderCacheFactory {
    private lateinit var applicationContext: Context
    
    fun init(context: Context) {
        applicationContext = context.applicationContext
    }
    
    actual fun createCache(): HolidayBagReminderInfosInstanceCache {
        if (!::applicationContext.isInitialized) {
            throw IllegalStateException("HolidayBagReminderCacheFactory must be initialized with a Context first")
        }
        return DataStoreHolidayBagReminderInfosInstanceCache(applicationContext)
    }
}