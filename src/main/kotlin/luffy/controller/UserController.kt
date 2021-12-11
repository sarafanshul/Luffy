package luffy.controller

import luffy.model.User
import luffy.service.UserService
import luffy.config.logger
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController(
    private val service : UserService
) {

    companion object{
        @JvmStatic private val log = logger()
    }

    @GetMapping("get")
    fun getUser( @RequestParam id : String ) : ResponseEntity<User>{
        return ResponseEntity.ok( service.getUserById(id) )
    }

    @PostMapping("nakama")
    fun updateUser( @RequestBody user : User ) : ResponseEntity<User>{
        return ResponseEntity.ok( service.addAndUpdateUser(user) )
    }
}