package com.example.springgraphqlapp.domain.repository

import com.example.springgraphqlapp.infrastructure.entity.ElementEntity
import com.example.springgraphqlapp.infrastructure.entity.WebElementIndexEntity
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ElementRepositoryTest(
    private val webElementIndexRepository: WebElementIndexRepository,
    private val elementRepository: ElementRepository
) : StringSpec({
    val webElementIndex = webElementIndexRepository.save(WebElementIndexEntity())
    val element = ElementEntity(elementTag = "a", count = 10, webElementIndex = webElementIndex)
    "保存" {
        elementRepository.save(element).apply {
            elementId shouldNotBe null
            elementTag shouldBe "a"
            count shouldBe 10L
        }
    }
    "取得" {
        elementRepository.save(element).apply {
            elementRepository.findOneByElementId(elementId).also {
                it.elementId shouldNotBe null
                it.elementTag shouldBe "a"
                it.count shouldBe 10L
            }
        }
    }
})