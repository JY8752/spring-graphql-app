package com.example.springgraphqlapp.application.service

import com.example.springgraphqlapp.domain.repository.MorphologicalAnalysisResultRepository
import com.example.springgraphqlapp.domain.repository.TextIndexRepository
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TextIndexServiceTest(
    private val textIndexService: TextIndexService,
    private val textIndexRepository: TextIndexRepository,
    private val morphologicalAnalysisResultRepository: MorphologicalAnalysisResultRepository
) : StringSpec() {
    private val html = """
        <html>
            <body>
                <h1>h1タグです.</h1>
                <div><p>pタグです.</p></div>
            </body>
        </html>
    """.trimIndent()

    init {
        "本文抽出しレコードが登録できること" {
            val textIndexId = textIndexService.extractMainText(html)
            textIndexId shouldNotBe null
            textIndexRepository.findOneByTextIndexId(textIndexId!!).also {
                it.textIndexId shouldBe textIndexId
                it.mainText shouldBe "pタグです.\nh1タグです."
                it.textCount shouldBe 14
                it.morphologicalAnalysisResults shouldBe emptyList()
            }
        }
        "本文抽出できなかったときnullが返ること" {
            val textIndexId = textIndexService.extractMainText("<html></html>")
            textIndexId shouldBe null
        }
        "形態素解析を実行しレコード登録ができること" {
            //本文抽出・形態素解析
            val textIndexId = textIndexService.extractMainText(html) ?: throw RuntimeException("textIndexレコードが登録されていません")
            val registeredCount = textIndexService.morphologicalAnalyze(textIndexId)

            //レコード取得
            val textIndex = textIndexRepository.findOneByTextIndexId(textIndexId)
            val morphoRecords = morphologicalAnalysisResultRepository.findByTextIndex(textIndex)

            registeredCount shouldBe morphoRecords.size
            textIndex.morphologicalAnalysisResults.size shouldBe morphoRecords.size
            println(morphoRecords)
        }
    }
}