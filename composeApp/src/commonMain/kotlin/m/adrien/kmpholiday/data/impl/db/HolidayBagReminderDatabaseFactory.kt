package m.adrien.kmpholiday.data.impl.db

expect object HolidayBagReminderDatabaseFactory {
    fun createDatabase(): AppDatabase
}
