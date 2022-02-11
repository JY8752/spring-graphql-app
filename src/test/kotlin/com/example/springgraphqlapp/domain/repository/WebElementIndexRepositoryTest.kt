package com.example.springgraphqlapp.domain.repository

import com.example.springgraphqlapp.infrastructure.entity.ElementEntity
import com.example.springgraphqlapp.infrastructure.entity.WebElementIndexEntity
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WebElementIndexRepositoryTest(
    private val webElementIndexRepository: WebElementIndexRepository,
    private val elementRepository: ElementRepository
) : StringSpec({
    "保存" {
        val webElementIndex = WebElementIndexEntity()
        webElementIndexRepository.save(webElementIndex).apply {
            webElementId shouldNotBe null
            elements.size shouldBe 0
        }
    }
    "取得" {
        val webElementIndex = webElementIndexRepository.save(WebElementIndexEntity())
        val element = elementRepository.save(ElementEntity(
            elementTag = "h1",
            count = 5L,
            webElementIndex = webElementIndex
        ))
        webElementIndex.elements = listOf(element)
        webElementIndexRepository.save(webElementIndex)

        webElementIndexRepository.findOneByWebElementId(webElementIndex.webElementId).also {
            it.webElementId shouldBe webElementIndex.webElementId
            it.elements[0].elementTag shouldBe "h1"
            it.elements[0].count shouldBe 5L
        }
    }
})