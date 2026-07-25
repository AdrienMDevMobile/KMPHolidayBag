package m.adrien.kmpholiday.data.impl

import m.adrien.kmpholiday.data.HolidayReminderFrameworkData
import m.adrien.kmpholiday.data.ItemInBagData
import m.adrien.kmpholiday.view.holidayBag.component.ItemInBag

internal object StaticDatas {
    val listOfHolidayBagReminder = listOf(
        HolidayReminderFrameworkData(
            name = "Plage",
            id = "1",
            items = listOf(
                ItemInBagData(
                    name = "Crême solaire",
                    id = "11",
                    isDayDependant = false,
                    quantity = 1,
                ),
                ItemInBagData(
                    name = "Serviette",
                    id = "16",
                    isDayDependant = false,
                    quantity = 1,
                ),
                ItemInBagData(
                    name = "Maillot de bain",
                    id = "15",
                    isDayDependant = false,
                    quantity = 1,
                ),
                ItemInBagData(
                    name = "Tongs",
                    id = "111",
                    isDayDependant = false,
                    quantity = 1,
                ),
                ItemInBagData(
                    name = "Casquette",
                    id = "110",
                    isDayDependant = false,
                    quantity = 1,
                ),
                ItemInBagData(
                    name = "T shirt",
                    id = "12",
                    isDayDependant = true,
                    quantity = 1,
                ),
                ItemInBagData(
                    name = "Slips",
                    id = "13",
                    isDayDependant = true,
                    quantity = 1,
                ),
                ItemInBagData(
                    name = "Chaussettes",
                    id = "14",
                    isDayDependant = true,
                    quantity = 1,
                ),
                ItemInBagData(
                    name = "Short",
                    id = "17",
                    isDayDependant = false,
                    quantity = 2,
                ),
                ItemInBagData(
                    name = "Pantalon",
                    id = "18",
                    isDayDependant = false,
                    quantity = 2,
                ),
                ItemInBagData(
                    name = "Affaire de sport",
                    id = "19",
                    isDayDependant = false,
                    quantity = 1,
                ),
                ItemInBagData(
                    name = "Chaussure supplementaire",
                    id = "113",
                    isDayDependant = false,
                    quantity = 1,
                ),
                ItemInBagData(
                    name = "Gourde",
                    id = "112",
                    isDayDependant = false,
                    quantity = 1,
                ),
                ItemInBagData(
                    name = "Belle tenue",
                    id = "115",
                    isDayDependant = false,
                    quantity = 1,
                ),
            )
        ),
        HolidayReminderFrameworkData(
            name = "Week end Danse",
            id = "2",
            items = listOf(
                ItemInBagData(
                    name = "T shirt",
                    id = "21",
                    isDayDependant = true,
                    quantity = 2,
                )
            )
        ),
        HolidayReminderFrameworkData(
            name = "Ski",
            id = "3",
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