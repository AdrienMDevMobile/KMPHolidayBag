package m.adrien.kmpholiday.data.impl.db

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import java.io.File

actual object HolidayBagReminderDatabaseFactory {
    private const val DB_FILE_NAME = "holiday_bag_reminder.db"

    actual fun createDatabase(): AppDatabase {
        val appDataDir = File(System.getProperty("user.home"), ".kmpholiday").apply { mkdirs() }
        val dbFile = File(appDataDir, DB_FILE_NAME)
        return Room.databaseBuilder<AppDatabase>(
            name = dbFile.absolutePath,
        )
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.IO)
            .build()
    }
}
