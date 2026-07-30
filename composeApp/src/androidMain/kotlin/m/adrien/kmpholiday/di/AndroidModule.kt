package m.adrien.kmpholiday.di

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.SavedStateHandle
import m.adrien.kmpholiday.data.impl.SettingsRepositoryImpl
import m.adrien.kmpholiday.domain.repository.SettingsRepository
import m.adrien.kmpholiday.view.holidayBag.HolidayBagReminderViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

fun createAndroidModule() = module {
    viewModelOf(::HolidayBagReminderViewModel)
    
    single<SettingsRepository> {
        SettingsRepositoryImpl()
    }
}

// Helper function to create SavedStateHandle for testing/previews
fun createMockSavedStateHandle(holidayId: String = ""): SavedStateHandle {
    val bundle = Bundle().apply {
        putString("holidayId", holidayId)
    }
    // Create SavedStateHandle from bundle - this is a simplified version
    // In real usage, this would come from the Compose navigation or activity
    return SavedStateHandle.createHandle(bundle, null)
}