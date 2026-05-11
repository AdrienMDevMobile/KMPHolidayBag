package m.adrien.kmpholiday.data.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import m.adrien.kmpholiday.data.StaticDatas
import m.adrien.kmpholiday.data.impl.cache.HolidayReminderInstanceCache
import m.adrien.kmpholiday.domain.HolidayItem
import m.adrien.kmpholiday.domain.HolidayItemId
import m.adrien.kmpholiday.domain.HolidayReminder
import m.adrien.kmpholiday.domain.HolidayReminderId
import m.adrien.kmpholiday.domain.repository.HolidayReminderRepository

class HolidayReminderRepositoryImpl(
    private val holidayReminderInstanceCache: HolidayReminderInstanceCache
) : HolidayReminderRepository {

    override fun get(id: HolidayReminderId): Flow<HolidayReminder> = flow {
        // 1. Récupérer les données statiques de base
        val staticData = StaticDatas.listOfHolidayReminder.find { it.id == id }

        if (staticData != null) {
            // 2. Récupérer la durée depuis le cache (si disponible) ou utiliser la durée par défaut
            val cachedDuration = holidayReminderInstanceCache.getReminderInstance(id) ?: staticData.duration

            // 3. Créer le HolidayReminder complet en combinant données statiques et durée dynamique
            val holidayReminder = HolidayReminder(
                id = staticData.id,
                name = staticData.name,
                duration = cachedDuration,
                items = staticData.items.map { itemData ->
                    HolidayItem(
                        name = itemData.name,
                        id = itemData.id,
                        checked = false, // Valeur par défaut //TODO : cette valeur doit etre en cache
                        quantity = itemData.quantity,
                        isDurationDependant = itemData.isDayDependant
                    )
                }
            )

            emit(holidayReminder)
        } else {
            throw IllegalArgumentException("Holiday reminder with id $id not found")
        }
    }

    override suspend fun check(itemId: HolidayItemId): Boolean {
        // Dans notre architecture mixte, les items sont statiques et ne peuvent pas être modifiés
        // Cette méthode pourrait être utilisée pour marquer un item comme coché/décoché
        // Pour l'instant, nous retournons false car nous ne supportons pas cette fonctionnalité
        return false
    }

    override suspend fun edit(itemId: HolidayItemId, newItem: HolidayItem): Boolean {
        // Dans notre architecture mixte, les items sont statiques et ne peuvent pas être édités
        // Cette méthode pourrait être implémentée pour permettre l'édition des items
        // Pour l'instant, nous retournons false car nous ne supportons pas cette fonctionnalité
        return false
    }

    override suspend fun reset(id: HolidayReminderId): Boolean {
        // Réinitialiser signifie supprimer la durée personnalisée du cache
        // et revenir à la durée par défaut des données statiques
        holidayReminderInstanceCache.saveReminderInstance(id, StaticDatas.listOfHolidayReminder.find { it.id == id }?.duration ?: 0)
        return true
    }
}