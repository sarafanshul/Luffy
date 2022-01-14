package luffy.config

import org.springframework.amqp.core.*
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.core.RabbitAdmin
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    companion object {
        private const val DEFAULT = "default_"
        const val EXCHANGE_NAME = DEFAULT+"messaging_exchange"
        const val ROUTING_KEY = DEFAULT+"messaging_routing_key."

        // default queue properties
        private const val QUEUE_IS_DURABLE = true // survives broker restart
        private const val QUEUE_IS_EXCLUSIVE = false
        private const val QUEUE_IS_AUTO_DELETE = false // deleted when last consumer unsubscribes
        private val QUEUE_ARGS = mapOf<String , Any>(
            "x-expires" to (1L * 30 * 24 * 60 * 60 * 1000) // 30 days of ttl period after last use (https://www.rabbitmq.com/ttl.html#queue-ttl)
        )

        fun getRoutingKey( queueName : String ) = "${ROUTING_KEY}_${queueName}"

        /**
         * Creates a new [Queue](https://www.rabbitmq.com/queues.html). -_-
         * - Durable : Durable (the queue will survive a broker restart)
         * - Exclusive (used by only one connection and the queue will be deleted when that connection closes)
         * @return [Queue] with [Queue.durable] = false
         */
        fun createMessagingQueue( queueName: String ) =
            Queue(queueName , QUEUE_IS_DURABLE , QUEUE_IS_EXCLUSIVE , QUEUE_IS_AUTO_DELETE , QUEUE_ARGS)
    }

    @Bean
    fun amqpAdmin( connectionFactory: ConnectionFactory ): AmqpAdmin = RabbitAdmin(connectionFactory)

    /**
     * Topic exchange for debugging purposes , as we can listen to `generic_queue.all`
     *
     * Change to Direct Exchange for prod purposes.
     */
    @Bean
    fun createExchange(): TopicExchange = TopicExchange(EXCHANGE_NAME)

    @Bean
    fun createGenericQueue() : Queue =
        Queue("generic_queue.all" ,false)

    @Bean
    fun createGenericBinding(genericQueue : Queue ,exchange : TopicExchange) : Binding =
        BindingBuilder.bind(genericQueue).to(exchange).with("$ROUTING_KEY*")

    @Bean
    fun getMessageConverter() : Jackson2JsonMessageConverter = Jackson2JsonMessageConverter()

}