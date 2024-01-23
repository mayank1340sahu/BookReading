package com.example.bookreading.data

data class UserData(
    var userId: String,
    var display: String,
    var avatarUrl: String,
    var quote: String,
    var profession: String,
    var id: Any?
) {
    fun toMap(): MutableMap<String, Any> {
        return mutableMapOf(
            "user_Id" to this.userId,
            "display_name" to this.display,
            "avatarUrl" to this.avatarUrl,
            "quote" to this.quote,
            "profession" to this.profession,
        )
    }
}