package luffy.config

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.MongoTemplate

@Suppress("PropertyName", "RedundantExplicitType")
@Configuration
class MongoConfig : AbstractMongoClientConfiguration() {

    @Value("\${database.name}")
    lateinit var DATABASE_NAME : String

    @Value("\${database.host}")
    lateinit var CONNECTION_URL : String

    @Value("\${database.port}")
    lateinit var CONNECTION_PORT : String

    override fun getDatabaseName(): String = DATABASE_NAME

    override fun mongoClient(): MongoClient =
        MongoClients.create("$CONNECTION_URL:$CONNECTION_PORT")

    @Bean
    fun mongoTemplate() : MongoTemplate =
        MongoTemplate(mongoClient() ,DATABASE_NAME)

    override fun autoIndexCreation(): Boolean {
        return true
    }

}