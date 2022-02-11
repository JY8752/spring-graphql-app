package com.example.springgraphqlapp.domain.repository

import com.example.springgraphqlapp.infrastructure.entity.MorphologicalAnalysisResultEntity
import com.example.springgraphqlapp.infrastructure.entity.TextIndexEntity
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class TextIndexRepositoryTest(
    private val textIndexRepository: TextIndexRepository,
    private val morphologicalAnalysisResultRepository: MorphologicalAnalysisResultRepository
) : StringSpec() {
    private val mainText = "mainText"
    private val textCount = 10L
    private val word = "word"
    private val partOfSpeech = "名詞"
    private val wordCount = 5L

    init {
        "保存" {
            saveTextIndexAndMorphologicalAnalysisResult().also { assertTextIndex(it) }
        }
        "取得" {
            saveTextIndexAndMorphologicalAnalysisResult().apply {
                textIndexRepository.findOneByTextIndexId(textIndexId).also { assertTextIndex(it) }
            }
        }
    }

    /**
     * textIndexレコードとmorphologicalAnalysisResultレコードを1件ずつ関係性を持たせつつ保存する
     */
    private fun saveTextIndexAndMorphologicalAnalysisResult(
        mainText: String = this.mainText,
        textCount: Long = this.textCount,
        word: String = this.word,
        partOfSpeech: String = this.partOfSpeech,
        wordCount: Long = this.wordCount): TextIndexEntity {

        val textIndex = textIndexRepository.save(TextIndexEntity(mainText = mainText, textCount = textCount))
        val morpho = morphologicalAnalysisResultRepository.save(MorphologicalAnalysisResultEntity(
            word = word,
            partOfSpeech = partOfSpeech,
            count = wordCount,
            textIndex = textIndex
        ))
        textIndex.morphologicalAnalysisResults = listOf(morpho)
        return textIndexRepository.save(textIndex)
    }

    /**
     * textIndexEntityの項目を確認する
     */
    private fun assertTextIndex(
        entity: TextIndexEntity,
        mainText: String = this.mainText,
        textCount: Long = this.textCount,
        word: String = this.word,
        partOfSpeech: String = this.partOfSpeech,
        wordCount: Long = this.wordCount
    ) {
        entity.textIndexId shouldNotBe null
        entity.mainText shouldBe mainText
        entity.textCount shouldBe textCount
        entity.morphologicalAnalysisResults.size shouldBe 1

        val morpho = entity.morphologicalAnalysisResults[0]
        morpho.morphologicalId shouldNotBe null
        morpho.word shouldBe word
        morpho.partOfSpeech shouldBe partOfSpeech
        morpho.count shouldBe wordCount
    }
}