package m.adrien.kmpholiday

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform