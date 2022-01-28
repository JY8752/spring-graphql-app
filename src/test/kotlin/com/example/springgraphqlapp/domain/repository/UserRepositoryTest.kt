package com.example.springgraphqlapp.domain.repository

import com.example.springgraphqlapp.infrastructure.entity.UserEntity
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class UserRepositoryTest(
    private val userRepository: UserRepository
) : StringSpec({
    "登録" {
        userRepository.save(UserEntity(name = "name", password = "password")).apply {
            userId shouldNotBe null
            name shouldBe "name"
            password shouldBe "password"
        }
    }
    "取得" {
        val user = userRepository.save(UserEntity(name = "name", password = "password"))
        userRepository.findOneByUserId(user.userId).apply {
            userId shouldNotBe null
            name shouldBe "name"
            password shouldBe "password"
        }
    }
})