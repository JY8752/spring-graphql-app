package com.example.springgraphqlapp.infrastructure.tool

import com.example.springgraphqlapp.application.enum.HtmlTag
import com.example.springgraphqlapp.application.tool.CustomHtmlParser
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import org.springframework.stereotype.Component

/**
 * htmlパーサーインターフェースの実装クラス
 * Jsoupをラップして使用
 */
@Component
class CustomHtmlParserImpl : CustomHtmlParser {
    companion object {
        /**
         * テキストだけhtmlから抽出するのが簡単ではないので一旦決め打ち
         */
        private val TARGET_TAG_LIST = listOf("title", "p", "h1", "h2", "h3", "h4", "h5")
    }

    override fun extractText(html: String): String? {
        val document = toDocument(html)
        document?.let {
            return TARGET_TAG_LIST.mapNotNull { tag ->
                //対象のタグが存在しない
                val elements = it.select(tag)
                if (elements.isEmpty()) return@mapNotNull null

                //全ての要素からテキストを取り出し結合する
                val text = elements.mapNotNull { elm ->
                    if (elm.hasText() && elm.text().isNotBlank()) elm.text() else null
                }.joinToString(separator = "\n")

                //要素はあるがテキストがなかった場合
                text.ifBlank { null }
            }.joinToString(separator = "\n").ifBlank { null }
        } ?: println("htmlパースに失敗しました")
        return null
    }

    override fun extractElements(html: String, tag: HtmlTag): Elements = toDocument(html)?.let {
        return it.select(tag.tagName)
    } ?: Elements()

    override fun hasElements(html: String, tag: HtmlTag): Boolean = toDocument(html)?.let {
        return extractElements(html, tag).size > 0
    } ?: false

    /**
     * htmlをパースする
     */
    private fun toDocument(html: String): Document? {
        return kotlin.runCatching {
            Jsoup.parse(html)
        }.getOrNull()
    }
}