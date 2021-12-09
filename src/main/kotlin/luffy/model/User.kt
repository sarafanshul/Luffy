package luffy.model

import com.fasterxml.jackson.annotation.JsonInclude
import luffy.util.DatabaseConstants.USER_COLLECTION
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.io.Serializable

@JsonInclude(JsonInclude.Include.NON_NULL)
@Document(collection = USER_COLLECTION)
data class User(
    @Id
    val id : String? = null,
    val name : String? = null,
    val connections : List<String>? = null,
) : Serializable
