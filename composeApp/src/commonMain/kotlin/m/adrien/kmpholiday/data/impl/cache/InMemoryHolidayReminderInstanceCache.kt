package m.adrien.kmpholiday.data.impl.cache

//TODO PlaceHolder, version others than Android don't use real cache
class InMemoryHolidayReminderInstanceCache : HolidayReminderInstanceCache {
    private val durationMap = mutableMapOf<String, Int>()
    
    override fun getReminderInstance(holidayId: String): Int? {
        return durationMap[holidayId]
    }
    
    override fun saveReminderInstance(holidayId: String, duration: Int) {
        durationMap[holidayId] = duration
    }
}