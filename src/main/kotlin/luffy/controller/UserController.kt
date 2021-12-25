package luffy.controller

import luffy.model.User
import luffy.service.UserService
import luffy.model.ConnectionData
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("user")
class UserController(
    private val service : UserService
) {

    @GetMapping("get")
    fun getUser( @RequestParam id : String ) : ResponseEntity<User>{
        return ResponseEntity.ok( service.getUserById(id) )
    }

    @GetMapping("tomodachi")
    fun getFriends( @RequestParam userId : String ) : ResponseEntity<List<User>>{
        return ResponseEntity.ok( service.getFriends(userId) )
    }

    @PutMapping("nakama")
    fun addUser( @RequestBody user: User ) : ResponseEntity<Boolean>{
        return ResponseEntity.ok( service.addUser(user) )
    }

    @PostMapping("nakama")
    fun connectUser( @RequestBody connectionData : ConnectionData ) : ResponseEntity<Boolean>{
        return ResponseEntity.ok( service.addFriend(connectionData) )
    }

    @PostMapping("nakama_bye")
    fun deleteConnection( @RequestBody connectionData: ConnectionData ) : ResponseEntity<Boolean>{
        return ResponseEntity.ok( service.deleteConnection(connectionData) )
    }
}