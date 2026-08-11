package m.adrien.kmpholiday.di

import m.adrien.kmpholiday.data.impl.HolidayBagReminderPreviewsRepositoryImpl
import m.adrien.kmpholiday.data.impl.HolidayBagReminderRepositoryImpl
import m.adrien.kmpholiday.data.impl.db.AppDatabase
import m.adrien.kmpholiday.data.impl.db.HolidayBagReminderDao
import m.adrien.kmpholiday.data.impl.db.HolidayBagReminderDatabaseFactory
import m.adrien.kmpholiday.data.impl.db.HolidayBagReminderDatabaseSeeder
import m.adrien.kmpholiday.domain.repository.HolidayBagReminderPreviewsRepository
import m.adrien.kmpholiday.domain.repository.HolidayBagReminderRepository
import m.adrien.kmpholiday.view.holidays.HolidaysViewModel
import m.adrien.kmpholiday.view.settings.SettingsViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf

val appModule = module {
    // Database
    single<AppDatabase> { HolidayBagReminderDatabaseFactory.createDatabase() }
    single<HolidayBagReminderDao> { get<AppDatabase>().holidayBagReminderDao() }
    single { HolidayBagReminderDatabaseSeeder(get()) }

    // Repositories
    single<HolidayBagReminderPreviewsRepository> {
        HolidayBagReminderPreviewsRepositoryImpl(get(), get())
    }

    single<HolidayBagReminderRepository> {
        HolidayBagReminderRepositoryImpl(get(), get())
    }

    // SettingsRepository is handled by platform-specific modules
    // because Android implementation requires Context

    // ViewModels
    viewModelOf(::HolidaysViewModel)
    viewModelOf(::SettingsViewModel)

    // Note: HolidayBagReminderViewModel is handled by platform-specific modules
    // because it requires SavedStateHandle which is Android-specific
}