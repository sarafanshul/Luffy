@file:Suppress("unused")

package luffy.controller

import luffy.config.RabbitMQConfig.Companion.EXCHANGE_NAME
import luffy.config.RabbitMQConfig.Companion.createQueue
import luffy.config.RabbitMQConfig.Companion.getRoutingKey
import luffy.model.MessageData
import luffy.model.QueueInformation
import luffy.util.logger
import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("producer")
class MessageController(
    private val template: RabbitTemplate,
    private val amqpAdmin: AmqpAdmin ,
    private val exchange: TopicExchange
) {

    companion object{
        @JvmStatic private val log = logger()
    }

    @PostMapping("send")
    fun sendMessage(@RequestBody messageData : MessageData) : ResponseEntity<QueueInformation>{

        val queueName = messageData.receiverId!!
        messageData.time = System.currentTimeMillis() // when message received to server

        val routingKey = getRoutingKey(queueName)

        if( amqpAdmin.getQueueInfo(queueName) == null ){
            val queue = createQueue(queueName)
            val binding : Binding = BindingBuilder.bind(queue).to(exchange).with(routingKey)
            amqpAdmin.declareQueue(queue)
            amqpAdmin.declareBinding(binding)
        }

        template.convertAndSend(EXCHANGE_NAME , routingKey ,messageData)

        return ResponseEntity.ok( QueueInformation(queueName ,routingKey) )
    }
}