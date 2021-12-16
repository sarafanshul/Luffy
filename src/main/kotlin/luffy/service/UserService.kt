package luffy.service

import luffy.model.User
import luffy.repository.UserRepository
import luffy.config.logger
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(
    private val repository : UserRepository
) {

    companion object{
        @JvmStatic private val log = logger()
    }

    fun addAndUpdateUser(user : User) : User {
        return repository.save(user)
    }

    fun getUserById( id : String ) : User {
        val x = repository.findById(id)
        if( ! x.isPresent )
            throw ResponseStatusException(
                HttpStatus.BAD_REQUEST ,"User not found!"
            )
        return x.get()
    }

    fun userExists( id : String ) : Boolean = repository.existsById(id)

    fun getFriends( userId : String ) : List<User>{
        return getUserById(userId)
            .connections?.map { name ->
                getUserById( name )
            } ?: listOf()
    }

}