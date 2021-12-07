package luffy.producer

import luffy.config.ConfigureRabbitMQ.Companion.EXCHANGE_NAME
import luffy.config.ConfigureRabbitMQ.Companion.ROUTING_KEY
import org.springframework.amqp.rabbit.core.RabbitTemplate
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("producer")
class SendMessageController(
    private val template : RabbitTemplate
) {

    @PostMapping("send")
    fun sendMessage(@RequestParam message : String ,@RequestParam route : String) : ResponseEntity<Boolean>{

        template.convertAndSend(EXCHANGE_NAME , ROUTING_KEY + route ,message)

        return ResponseEntity.ok(true)
    }
}