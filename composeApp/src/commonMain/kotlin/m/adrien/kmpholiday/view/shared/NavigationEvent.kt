package m.adrien.kmpholiday.view.shared

// Navigation events following Google's guidance
abstract class NavigationEvent {
    // Simple counter for unique IDs - this is sufficient for our use case
    companion object {
        private var counter = 0
    }

    val id: String = "evt_${counter++}"
}