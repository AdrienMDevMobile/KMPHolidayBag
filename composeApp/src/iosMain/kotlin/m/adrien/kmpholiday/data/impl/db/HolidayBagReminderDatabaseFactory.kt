package m.adrien.kmpholiday.data.impl.db

import androidx.room3.Room
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.coroutines.Dispatchers
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

actual object HolidayBagReminderDatabaseFactory {
    private const val DB_FILE_NAME = "holiday_bag_reminder.db"

    @OptIn(ExperimentalForeignApi::class)
    actual fun createDatabase(): AppDatabase {
        val documentsPath = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null,
        )?.path ?: error("Could not resolve iOS documents directory")
        return Room.databaseBuilder<AppDatabase>(
            name = "$documentsPath/$DB_FILE_NAME",
        )
            .setDriver(BundledSQLiteDriver())
            .setQueryCoroutineContext(Dispatchers.Default)
            .build()
    }
}
