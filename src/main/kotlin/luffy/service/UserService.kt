package luffy.service

import luffy.model.User
import luffy.util.logger
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService {

    companion object{
        @JvmStatic private val log = logger()
    }

    fun addUser(user : User) : User {
        if( false ){
            // add user
            return user
        } else{
            log.warn("User : $user already exists!")
            throw ResponseStatusException(
                HttpStatus.CONFLICT,
                "User already exists"
            )
        }
    }
}