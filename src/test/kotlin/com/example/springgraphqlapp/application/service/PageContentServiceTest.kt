package com.example.springgraphqlapp.application.service

import com.example.springgraphqlapp.domain.repository.PageContentRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PageContentServiceTest(
    private val pageContentService: PageContentService,
    private val pageContentRepository: PageContentRepository
) : StringSpec() {
    init {
        "不正なURLがエラーになること" {
            val url = "http://test.com"
            val id = pageContentService.analyzeUrl(url)

            pageContentRepository.findOneByContentId(id).also {
                it.contentId shouldBe id
                it.url shouldBe url
                it.webElementIndexId shouldBe null
                it.textIndexId shouldBe null
                it.isError shouldBe true
                it.errorReason shouldBe "ConnectionError"
                it.code shouldBe null
            }
        }
        "指定のURLを解析しレコード登録できていること" {
            val url = "https://yahoo.com"
            val id = pageContentService.analyzeUrl(url)

            pageContentRepository.findOneByContentId(id).also {
                it.contentId shouldBe id
                it.url shouldBe url
                it.webElementIndexId shouldNotBe null
                it.textIndexId shouldNotBe null
                it.isError shouldBe false
                it.errorReason shouldBe null
                it.code shouldBe 200
            }
        }
    }
}