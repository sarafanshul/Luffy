package luffy.model

/**
 * Data Class for requesting a connection between users.
 */
data class ConnectionData(
    val senderUser : String? = null,
    val receiverUser : String? = null,
)
