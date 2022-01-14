package luffy.service

import luffy.config.RabbitMQConfig
import luffy.model.User
import luffy.repository.UserRepository
import luffy.model.ConnectionData
import org.springframework.amqp.core.AmqpAdmin
import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.TopicExchange
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    private val repository: UserRepository,
    private val amqpAdmin: AmqpAdmin,
    private val exchange: TopicExchange,
) {

    private fun addAndUpdateUser(user: User): User {
        return repository.save(user)
    }

    fun addFriend(connectionData: ConnectionData): Boolean {
        val sender = getUserById(connectionData.senderUser!!)
        val receiver = getUserById(connectionData.receiverUser!!)

        if (sender.connections?.contains(receiver.id) == true ||
            receiver.connections?.contains(sender.id) == true
        ) return false

        sender.connections?.add(receiver.id!!)
        receiver.connections?.add(sender.id!!)

        addAndUpdateUser(sender)
        addAndUpdateUser(receiver)

        return true
    }

    fun deleteConnection( connectionData: ConnectionData ) : Boolean{
        val sender = getUserById(connectionData.senderUser!!)
        val receiver = getUserById(connectionData.receiverUser!!)

        if (sender.connections?.contains(receiver.id) == false ||
            receiver.connections?.contains(sender.id) == false
        ) return false

        sender.connections?.remove(receiver.id!!)
        receiver.connections?.remove(sender.id!!)

        addAndUpdateUser(sender)
        addAndUpdateUser(receiver)

        return true
    }

    fun getUserById(id: String): User {
        val x = repository.findById(id)
        if (!x.isPresent)
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST, "User not found!"
            )
        return x.get()
    }

    fun addUser(user: User): Boolean {
        if( userExists(user.id!!) )
            throw ResponseStatusException(
                HttpStatus.CONFLICT ,"User Already Exists!"
            )
        user.connections?.clear()
        addAndUpdateUser(user)
        openUserQueue(user)
        return true
    }

    private fun openUserQueue(user: User) {
        val queueName = user.id!!
        val routingKey = RabbitMQConfig.getRoutingKey(queueName)

        if( amqpAdmin.getQueueInfo(queueName) == null ){
            val queue = RabbitMQConfig.createMessagingQueue(queueName)
            val binding : Binding = BindingBuilder.bind(queue).to(exchange).with(routingKey)
            amqpAdmin.declareQueue(queue)
            amqpAdmin.declareBinding(binding)
        }
    }

    fun userExists(id: String): Boolean = repository.existsById(id)

    fun getFriends(userId: String): List<User> {
        return getUserById(userId)
            .connections?.map { name ->
                getUserById(name)
            } ?: listOf()
    }

}