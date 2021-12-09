package luffy.consumer

import luffy.model.MessageData
import luffy.util.logger
import org.slf4j.Logger
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Service

@Service
class MessageHandler {

    companion object{

        @JvmStatic
        private val log : Logger = logger()

    }

    /**
     * For DEBUGGING PURPOSES ONLY ,
     * Listens to all queue when Exchange type = Topic Exchange
     */
    @RabbitListener(
        queues = ["generic_queue.all"]
    )
    fun handleMessage( messageBody : MessageData ){
        log.info( "ALL_QUEUE_CONSUMER -> $messageBody" )
    }

}