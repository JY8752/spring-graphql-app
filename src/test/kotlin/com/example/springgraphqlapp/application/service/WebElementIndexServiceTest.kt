package com.example.springgraphqlapp.application.service

import com.example.springgraphqlapp.domain.repository.WebElementIndexRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.collections.shouldBeIn
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class WebElementIndexServiceTest(
    private val webElementIndexService: WebElementIndexService,
    private val webElementIndexRepository: WebElementIndexRepository
) : StringSpec() {
    init {
        "対象タグを抽出しレコード登録ができていること" {
            val html = """
                <html>
                    <body>
                        <p>テスト</p>
                        <div>
                            <h1>画像です</h1>
                            <img src="/xxx">
                            <a href="http://test.com"><img src="/xxx"></a>
                        </div>
                    </body>
                </html>
            """.trimIndent()
            webElementIndexService.analyzeElements(html)?.let {
                val elements = webElementIndexRepository.findOneByWebElementId(it).elements
                elements.size shouldBe 3
                elements.forEach { element ->
                    element.elementId shouldNotBe null
                    element.elementTag shouldBeIn listOf("a", "img", "p")
                    when(element.elementTag) {
                        "a" -> element.count shouldBe 1
                        "img" -> element.count shouldBe 2
                        "p" -> element.count shouldBe 1
                    }
                }
            }
        }
        "対象タグがなかった場合にnullが返ること" {
            webElementIndexService.analyzeElements("<html></html>") shouldBe null
        }
    }
}