package m.adrien.kmpholiday.data.impl.db

import android.content.Context
import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers

actual object HolidayBagReminderDatabaseFactory {
    private const val DB_FILE_NAME = "holiday_bag_reminder.db"
    private lateinit var applicationContext: Context

    fun init(context: Context) {
        applicationContext = context.applicationContext
    }

    actual fun createDatabase(): AppDatabase {
        check(::applicationContext.isInitialized) {
            "HolidayBagReminderDatabaseFactory must be initialized with a Context first"
        }
        val dbFile = applicationContext.getDatabasePath(DB_FILE_NAME)
        return Room.databaseBuilder<AppDatabase>(
            context = applicationContext,
            name = dbFile.absolutePath,
        )
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}
