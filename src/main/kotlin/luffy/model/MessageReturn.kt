package luffy.model

/**
 * Return for [luffy.controller.MessageController.sendMessage] for return of [MessageData]
 */
data class MessageReturn(
    val messageData: MessageData? = null ,
    val queueInformation: QueueInformation? = null
)
