package m.adrien.kmpholiday.di

import m.adrien.kmpholiday.di.createIosModule
import org.koin.core.context.startKoin

actual fun initKoin() {
    startKoin {
        modules(appModule + createIosModule())
    }
}