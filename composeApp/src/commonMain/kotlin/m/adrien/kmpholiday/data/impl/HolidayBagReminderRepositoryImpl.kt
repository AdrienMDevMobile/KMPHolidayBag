package m.adrien.kmpholiday.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import m.adrien.kmpholiday.data.impl.cache.HolidayBagReminderCacheFactory
import m.adrien.kmpholiday.data.impl.cache.HolidayBagReminderInfosInstanceCache
import m.adrien.kmpholiday.domain.ItemInBag
import m.adrien.kmpholiday.domain.ItemInBagId
import m.adrien.kmpholiday.domain.HolidayBagReminder
import m.adrien.kmpholiday.domain.HolidayBagReminderId
import m.adrien.kmpholiday.domain.repository.HolidayBagReminderRepository

class HolidayBagReminderRepositoryImpl(
    private val holidayReminderInstanceCache: HolidayBagReminderInfosInstanceCache = HolidayBagReminderCacheFactory.createCache()
) : HolidayBagReminderRepository {

    override fun get(id: HolidayBagReminderId): Flow<HolidayBagReminder> = flow {
        val staticData = StaticDatas.listOfHolidayBagReminder.find { it.id == id }

        if (staticData != null) {
            val cachedDuration = holidayReminderInstanceCache.getReminderInstance(id) ?: staticData.duration

            val holidayBagReminder = HolidayBagReminder(
                id = staticData.id,
                name = staticData.name,
                duration = cachedDuration,
                items = staticData.items.map { itemData ->
                    ItemInBag(
                        name = itemData.name,
                        id = itemData.id,
                        checked = false, // Valeur par défaut //TODO : cette valeur doit etre en cache
                        quantity = itemData.quantity,
                        isDurationDependant = itemData.isDayDependant
                    )
                }
            )

            emit(holidayBagReminder)
        } else {
            throw IllegalArgumentException("Holiday reminder with id $id not found")
        }
    }

    override suspend fun check(itemId: ItemInBagId): Boolean {
        // Dans notre architecture mixte, les items sont statiques et ne peuvent pas être modifiés
        // Cette méthode pourrait être utilisée pour marquer un item comme coché/décoché
        // Pour l'instant, nous retournons false car nous ne supportons pas cette fonctionnalité
        return false
    }

    override suspend fun edit(itemId: ItemInBagId, newItem: ItemInBag): Boolean {
        // Dans notre architecture mixte, les items sont statiques et ne peuvent pas être édités
        // Cette méthode pourrait être implémentée pour permettre l'édition des items
        // Pour l'instant, nous retournons false car nous ne supportons pas cette fonctionnalité
        return false
    }

    override suspend fun reset(id: HolidayBagReminderId): Boolean {
        // Réinitialiser signifie supprimer la durée personnalisée du cache
        // et revenir à la durée par défaut des données statiques
        holidayReminderInstanceCache.saveReminderInstance(id, StaticDatas.listOfHolidayBagReminder.find { it.id == id }?.duration ?: 0)
        return true
    }
}