package m.adrien.kmpholiday.di

import m.adrien.kmpholiday.data.impl.HolidayBagReminderPreviewsRepositoryStaticImpl
import m.adrien.kmpholiday.data.impl.HolidayBagReminderRepositoryImpl
import m.adrien.kmpholiday.domain.repository.HolidayBagReminderPreviewsRepository
import m.adrien.kmpholiday.domain.repository.HolidayBagReminderRepository
import m.adrien.kmpholiday.view.holidays.HolidaysViewModel
import org.koin.dsl.module
import org.koin.core.module.dsl.viewModelOf

val appModule = module {
    // Repositories
    single<HolidayBagReminderPreviewsRepository> {
        HolidayBagReminderPreviewsRepositoryStaticImpl()
    }

    single<HolidayBagReminderRepository> {
        HolidayBagReminderRepositoryImpl()
    }

    // ViewModels
    viewModelOf(::HolidaysViewModel)

    // Note: HolidayBagReminderViewModel is handled by platform-specific modules
    // because it requires SavedStateHandle which is Android-specific
}