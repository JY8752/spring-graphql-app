package com.example.springgraphqlapp.application.tool

import com.example.springgraphqlapp.application.enum.HtmlTag
import org.jsoup.nodes.Element
import org.jsoup.select.Elements

/**
 * htmlパーサーライブラリのラッパーインターフフェース
 */
interface CustomHtmlParser {
    /**
     * htmlから本文を抽出する
     */
    fun extractText(html: String): String?

    /**
     * htmlからタグ要素を抽出する
     */
    fun extractElements(html: String, tag: HtmlTag): Elements

    /**
     * htmlにタグ要素があるかどうか
     */
    fun hasElements(html: String, tag: HtmlTag): Boolean
}