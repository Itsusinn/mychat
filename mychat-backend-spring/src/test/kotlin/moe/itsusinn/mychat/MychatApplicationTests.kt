package moe.itsusinn.mychat

import moe.itsusinn.mychat.security.tool.generateToken
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MychatApplicationTests

fun main() {
    println(generateToken(0L, "UUID", "GUEST"))
}
