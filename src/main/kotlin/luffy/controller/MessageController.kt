@file:Suppress("unused")

package luffy.controller

import luffy.model.MessageData
import luffy.model.QueueInformation
import luffy.service.MessageService
import luffy.config.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("message")
class MessageController(
    private val service : MessageService
) {

    companion object{
        @JvmStatic private val log = logger()
    }

    @PostMapping("send")
    fun sendMessage(@RequestBody messageData : MessageData) : ResponseEntity<QueueInformation>{
        return ResponseEntity.ok( service.sendMessage(messageData) )
    }

    @GetMapping("count")
    fun getQueueCount( @RequestParam queue : String ) : ResponseEntity<Int>{
        return ResponseEntity.ok(service.getMessageCount(queue))
    }

}