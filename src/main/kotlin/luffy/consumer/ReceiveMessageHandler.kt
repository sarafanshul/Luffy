package luffy.consumer

import luffy.util.logger
import org.slf4j.Logger
import org.springframework.stereotype.Service

@Service
class ReceiveMessageHandler {

    companion object{

        @JvmStatic
        private val log : Logger = logger()

    }

    fun handleMessage( messageBody : String ){
        log.info(messageBody)

    }

}