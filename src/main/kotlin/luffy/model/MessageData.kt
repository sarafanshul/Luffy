package luffy.model

/**
 * Data object for User Message
 * - [MessageData.receiverId] is [QueueInformation.name] for ease
 */
data class MessageData(
    val senderId : String ,
    val receiverId : String,
    val data : String
)