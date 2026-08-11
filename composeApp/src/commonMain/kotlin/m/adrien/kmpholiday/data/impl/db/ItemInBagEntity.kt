package m.adrien.kmpholiday.data.impl.db

import androidx.room3.Entity
import androidx.room3.ForeignKey
import androidx.room3.Index
import androidx.room3.PrimaryKey
import m.adrien.kmpholiday.domain.ItemInBagId

@Entity(
    tableName = "item_in_bag",
    foreignKeys = [
        ForeignKey(
            entity = HolidayBagReminderEntity::class,
            parentColumns = ["id"],
            childColumns = ["holidayId"],
            onDelete = ForeignKey.CASCADE,
        )
    ],
    indices = [Index("holidayId")],
)
data class ItemInBagEntity(
    @PrimaryKey(autoGenerate = true) val uid: Long = 0,
    val holidayId: String,
    val itemId: ItemInBagId,
    val name: String,
    val quantity: Int,
    val isDurationDependant: Boolean,
    val checked: Boolean = false,
)
