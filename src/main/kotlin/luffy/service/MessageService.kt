package luffy.service

import luffy.config.RabbitMQConfig
import luffy.model.MessageData
import luffy.model.QueueInformation
import luffy.util.isOk
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class MessageService(
    private val template: RabbitTemplate,
    private val amqpAdmin: AmqpAdmin,
    private val exchange: TopicExchange,
) {

    @Autowired
    lateinit var userService: UserService // for removing circular dependency

    fun sendMessage( messageData: MessageData ) : QueueInformation{

        if( ! isMessageValid(messageData) )
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST , "Bad message data!"
            )

        val queueName = messageData.receiverId!!
        messageData.time = System.currentTimeMillis() // when message received to server

        val routingKey = RabbitMQConfig.getRoutingKey(queueName)

        if( amqpAdmin.getQueueInfo(queueName) == null ){
            val queue = RabbitMQConfig.createMessagingQueue(queueName)
            val binding : Binding = BindingBuilder.bind(queue).to(exchange).with(routingKey)
            amqpAdmin.declareQueue(queue)
            amqpAdmin.declareBinding(binding)
        }

        template.convertAndSend(RabbitMQConfig.EXCHANGE_NAME, routingKey ,messageData)

        return QueueInformation(queueName ,routingKey)
    }

    fun getMessageCount( queueName : String ) : Int {
        return amqpAdmin.getQueueInfo(queueName)?.messageCount ?: throw ResponseStatusException(
            HttpStatus.NOT_FOUND , "Queue not found!"
        )
    }

    /**
     * Simple checks
     */
    fun isMessageValid( messageData: MessageData ) : Boolean =
        userService.userExists( messageData.senderId!! ) &&
        userService.userExists( messageData.receiverId!! ) &&
        messageData.data.isOk() &&
        (messageData.senderId != messageData.receiverId)

}