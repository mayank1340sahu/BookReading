package com.example.bookreading.data

data class UserData(
    var userId: String,
    var display: String,
    var avatarUrl: String,
    var quote: String,
    var profession: String,
    var id: Int?
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_Id" to this.userId,
            "display" to this.display,
            "avatarUrl" to this.avatarUrl,
            "quote" to this.quote,
            "profession" to this.profession,
        )
    }
}