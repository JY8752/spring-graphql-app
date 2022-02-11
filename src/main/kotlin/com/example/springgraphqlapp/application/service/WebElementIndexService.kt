package com.example.springgraphqlapp.application.service


import com.example.springgraphqlapp.application.enum.HtmlTag
import com.example.springgraphqlapp.application.tool.CustomHtmlParser
import com.example.springgraphqlapp.domain.repository.ElementRepository
import com.example.springgraphqlapp.domain.repository.WebElementIndexRepository
import com.example.springgraphqlapp.infrastructure.entity.ElementEntity
import com.example.springgraphqlapp.infrastructure.entity.WebElementIndexEntity
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

/**
 * 取得したhtmlからタグを抽出し解析するサービスクラス
 */
@Service
class WebElementIndexService(
    private val customHtmlParser: CustomHtmlParser,
    private val webElementIndexRepository: WebElementIndexRepository,
    private val elementRepository: ElementRepository
) {
    /**
     * htmlから対象のタグ要素を抽出しレコード登録する
     */
    fun analyzeElements(html: String): ObjectId? {
        //対象のタグが1つもなければ終了
        if (!HtmlTag.values().all { customHtmlParser.hasElements(html, it) }) return null

        //一旦親レコードを登録
        val webElementIndex = webElementIndexRepository.save(WebElementIndexEntity())
        val elementEntities = mutableListOf<ElementEntity>()

        //Elementレコード登録
        HtmlTag.values().forEach { tag ->
            val elements = customHtmlParser.extractElements(html, tag)
            elementRepository.save(ElementEntity(
                elementTag = tag.tagName,
                count = elements.size.toLong(),
                webElementIndex = webElementIndex
            )).also { elementEntities.add(it) }
        }

        //WebElementIndexレコードを更新する
        webElementIndex.elements = elementEntities
        return webElementIndexRepository.save(webElementIndex).webElementId
    }
}