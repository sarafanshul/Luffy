package luffy.controller

import luffy.model.User
import luffy.service.UserService
import luffy.util.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("user")
class UserController(
    private val service : UserService
) {

    companion object{
        @JvmStatic private val log = logger()
    }

    @PostMapping("add")
    fun addUser( @RequestBody user : User ) : ResponseEntity<User>{
        return ResponseEntity.ok( service.addUser(user) )
    }
}