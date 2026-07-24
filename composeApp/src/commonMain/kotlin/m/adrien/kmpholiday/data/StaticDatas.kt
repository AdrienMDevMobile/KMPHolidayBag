package m.adrien.kmpholiday.data

internal object StaticDatas {
    val listOfHolidayBagReminder = listOf(
        HolidayBagReminderData(
            name = "Plage",
            id = "1",
            duration = 7,
            items = listOf(
                ItemInBagData(
                    name = "Crême solaire",
                    id = "11",
                    isDayDependant = false,
                    quantity = 1,
                ),
                ItemInBagData(
                    name = "T shirt",
                    id = "12",
                    isDayDependant = true,
                    quantity = 1,
                )
            )
        ),
        HolidayBagReminderData(
            name = "Week end Danse",
            id = "2",
            duration = 2,
            items = listOf(
                ItemInBagData(
                    name = "T shirt",
                    id = "21",
                    isDayDependant = true,
                    quantity = 2,
                )
            )
        ),
        HolidayBagReminderData(
            name = "Ski",
            id = "3",
            duration = 7,
            items = listOf(
                ItemInBagData(
                    name = "T shirt",
                    id = "31",
                    isDayDependant = true,
                    quantity = 2,
                ),
                ItemInBagData(
                    name = "Lunettes de soleil",
                    id = "32",
                    isDayDependant = true,
                    quantity = 2,
                )
            )
        ),
    )
}