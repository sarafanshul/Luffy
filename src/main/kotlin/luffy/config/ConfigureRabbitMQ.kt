package luffy.config

import luffy.consumer.ReceiveMessageHandler
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.Queue
import org.springframework.amqp.core.TopicExchange
import org.springframework.amqp.rabbit.connection.ConnectionFactory
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ConfigureRabbitMQ {

    companion object {

        const val QUEUE_NAME = "messaging_queue"
        const val EXCHANGE_NAME = "messaging_exchange"
        const val ROUTING_KEY = "messaging_routing_key."
    }

    @Bean
    fun createQueue(): Queue = Queue(QUEUE_NAME, false)

    @Bean
    fun createExchange(exchangeName: String? = null): TopicExchange =
        TopicExchange(EXCHANGE_NAME)

    @Bean
    fun createBinding(queue: Queue, exchange: TopicExchange): Binding =
        BindingBuilder.bind(queue).to(exchange).with("$ROUTING_KEY#")

    @Bean
    fun createContainer(
        connectionFactory: ConnectionFactory,
        messageListenerAdapter: MessageListenerAdapter
    ): SimpleMessageListenerContainer =
        SimpleMessageListenerContainer().apply {
            setMessageListener(messageListenerAdapter)
            setConnectionFactory(connectionFactory)
            setQueueNames(QUEUE_NAME ,)
        }

    @Bean
    fun createListenerAdapter( handler : ReceiveMessageHandler ) : MessageListenerAdapter =
        MessageListenerAdapter(handler ,handler::handleMessage.name)
}