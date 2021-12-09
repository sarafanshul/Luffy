package luffy.model

import java.io.Serializable

/**
 * Data object for User Message
 * - [MessageData.receiverId] is [QueueInformation.name] for ease
 */
data class MessageData(
    val senderId : String? = null ,
    val receiverId : String? = null,
    val data : String? = null,
    var time : Long? = null
) : Serializable