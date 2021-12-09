package luffy.config

import com.mongodb.client.MongoClient
import com.mongodb.client.MongoClients
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Lazy
import org.springframework.data.mongodb.MongoDatabaseFactory
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.convert.DbRefResolver
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper
import org.springframework.data.mongodb.core.convert.MappingMongoConverter
import org.springframework.data.mongodb.core.mapping.MongoMappingContext

@Suppress("PropertyName", "RedundantExplicitType")
@Configuration
class MongoConfig : AbstractMongoClientConfiguration() {

    @Value("\${database.name}")
    lateinit var DATABASE_NAME : String

    @Value("\${database.host}")
    lateinit var CONNECTION_URL : String

    override fun getDatabaseName(): String = DATABASE_NAME

    override fun mongoClient(): MongoClient =
        MongoClients.create(CONNECTION_URL)

    @Bean
    fun mongoTemplate() : MongoTemplate =
        MongoTemplate(mongoClient() ,DATABASE_NAME)

    override fun autoIndexCreation(): Boolean {
        return true
    }

}