package luffy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LuffyApplication

fun main(args: Array<String>) {
	runApplication<LuffyApplication>(*args)
}
