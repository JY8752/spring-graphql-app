package com.example.springgraphqlapp.domain.repository

import com.example.springgraphqlapp.infrastructure.entity.PageContentEntity
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.bson.types.ObjectId
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PageContentRepositoryTest(
    private val pageContentRepository: PageContentRepository
) : StringSpec() {
    init {
        "保存" {
            val url = "http://test.com"
            savePageContent(url = url).also { assertPageContent(it, url) }
        }
        "取得" {
            val url = "http://test.com"
            savePageContent(url = url).apply { pageContentRepository.findOneByContentId(contentId) }.also { assertPageContent(it, url) }
        }
    }

    /**
     * PageContentレコードを1件登録する
     */
    private fun savePageContent(
        url: String,
        textIndexId: ObjectId? = null,
        webElementIndexId: ObjectId? = null
    ) = pageContentRepository.save(PageContentEntity(url = url, textIndexId = textIndexId, webElementIndexId = webElementIndexId))

    /**
     * PageContentレコードの中身を確認する
     */
    private fun assertPageContent(
        pageContent: PageContentEntity,
        url: String,
        textIndexId: Long? = null,
        webElementIndexId: Long? = null
    ) {
        pageContent.contentId shouldNotBe null
        pageContent.url shouldBe url
        pageContent.textIndexId shouldBe textIndexId
        pageContent.webElementIndexId shouldBe webElementIndexId
        pageContent.isError shouldBe false
        pageContent.errorReason shouldBe null
    }
}