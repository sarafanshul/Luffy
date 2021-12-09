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
        // TODO move these to application.properties
        private const val DEFAULT = "default_"
        const val EXCHANGE_NAME = DEFAULT+"messaging_exchange"
        const val ROUTING_KEY = DEFAULT+"messaging_routing_key."

        fun getRoutingKey( queueName : String) =
            "${ROUTING_KEY}_${queueName}"

        /**
         * Creates a new [Queue](https://www.rabbitmq.com/queues.html). -_-
         * - Durable : Durable (the queue will survive a broker restart)
         * - Exclusive (used by only one connection and the queue will be deleted when that connection closes)
         * @return [Queue] with [Queue.durable] = false
         */
        fun createQueue( queueName: String ) =
            Queue(queueName ,false)
    }

    @Bean
    fun amqpAdmin( connectionFactory: ConnectionFactory ): AmqpAdmin = RabbitAdmin(connectionFactory)

//    @Bean
//    fun createExchange(): DirectExchange = DirectExchange(EXCHANGE_NAME)

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