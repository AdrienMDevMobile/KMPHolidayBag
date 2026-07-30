package m.adrien.kmpholiday.di

import m.adrien.kmpholiday.data.impl.SettingsRepositoryImpl
import m.adrien.kmpholiday.domain.repository.SettingsRepository
import org.koin.dsl.module

fun createIosModule() = module {
    single<SettingsRepository> {
        SettingsRepositoryImpl()
    }
}