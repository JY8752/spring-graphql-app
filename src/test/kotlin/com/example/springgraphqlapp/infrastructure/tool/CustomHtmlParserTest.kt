package com.example.springgraphqlapp.infrastructure.tool

import com.example.springgraphqlapp.application.enum.HtmlTag
import com.example.springgraphqlapp.application.tool.CustomHtmlParser
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.jsoup.select.Elements
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CustomHtmlParserTest(
    private val customHtmlParser: CustomHtmlParser
) : StringSpec() {
    init {
        "本文抽出ができること" {
            val html = """
               <html>
                <head><title>titleタグです</title></head>
                <body>
                    <h1>h1タグです</h1>
                    <h2>h2タグです</h2>
                    <h3>h3タグです</h3>
                    <h4>h4タグです</h4>
                    <h5>h5タグです</h5>
                </body>
               </html> 
            """

            val text = customHtmlParser.extractText(html)

            text shouldBe """
                titleタグです
                h1タグです
                h2タグです
                h3タグです
                h4タグです
                h5タグです
            """.trimIndent()
            println(text)
        }
        "タグにテキストがない" {
            val html = """
               <html>
                <head><title>titleタグです</title></head>
                <body>
                    <h1>h1タグです</h1>
                    <h2>h2タグです</h2>
                    <h3></h3>
                    <h4>h4タグです</h4>
                    <h5>h5タグです</h5>
                </body>
               </html> 
            """

            val text = customHtmlParser.extractText(html)

            text shouldBe """
                titleタグです
                h1タグです
                h2タグです
                h4タグです
                h5タグです
            """.trimIndent()
            println(text)

        }
        "html構造が壊れている" {
            val html = """
               <html>
                <head><title>titleタグです</title></head>
                <body>
                    <h1>h1タグです<h1>
                    <h2>h2タグです
                    <h3>h3タグです</h3
                    <h4>h4タグです</h4>
                    <h5>h5タグです</h5>
                </body>
               </html> 
            """

            val text = customHtmlParser.extractText(html)

            text shouldBe """
                titleタグです
                h1タグです
                h2タグです
                h3タグです
                h4タグです
                h5タグです
            """.trimIndent()
            println(text)

        }
        "テキストが存在しない" {
            val html = "<html></html>"
            val text = customHtmlParser.extractText(html)
            text shouldBe null
        }
        "タグ要素が抽出できること" {
            val html = """
                <html>
                    <body>
                        <a href="http://test.com">test</a>
                        <a href="http://test2.com">test2</a>
                    </body>
                </html>
            """.trimIndent()
            val elements = customHtmlParser.extractElements(html, HtmlTag.A)
            elements.size shouldBe 2
        }
        "対象のタグ要素がなかったとき空のインスタンスが返ること" {
            customHtmlParser.extractElements("<html></html>", HtmlTag.A) shouldBe Elements()
        }
        "hasElements" {
            val html = """
                <html>
                    <body>
                        <p>pタグ</p>
                    </body>
                </html>
            """.trimIndent()
            customHtmlParser.hasElements(html, HtmlTag.P) shouldBe true
            customHtmlParser.hasElements(html, HtmlTag.A) shouldBe false
            customHtmlParser.hasElements(html, HtmlTag.IMG) shouldBe false
        }
    }
}