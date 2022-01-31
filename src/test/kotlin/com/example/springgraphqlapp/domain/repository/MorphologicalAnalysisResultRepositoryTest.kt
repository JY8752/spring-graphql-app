package com.example.springgraphqlapp.domain.repository

import com.example.springgraphqlapp.infrastructure.entity.MorphologicalAnalysisResultEntity
import com.example.springgraphqlapp.infrastructure.entity.TextIndexEntity
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MorphologicalAnalysisResultRepositoryTest(
    private val textIndexRepository: TextIndexRepository,
    private val morphologicalAnalysisResultRepository: MorphologicalAnalysisResultRepository
) : StringSpec({
    val textIndex = textIndexRepository.save(TextIndexEntity(
        mainText = "text",
        textCount = 10L,
    ))
    val morpho = MorphologicalAnalysisResultEntity(
        word = "test",
        partOfSpeech = "名詞",
        count = 10L,
        textIndex = textIndex
    )
    "保存" {
        morphologicalAnalysisResultRepository.save(morpho).also {
            it.morphologicalId shouldNotBe null
            it.word shouldBe "test"
            it.partOfSpeech shouldBe "名詞"
            it.count shouldBe 10L
        }
    }
    "取得" {
        morphologicalAnalysisResultRepository.save(morpho).apply {
            morphologicalAnalysisResultRepository.findOneByMorphologicalId(morphologicalId).also {
                it.morphologicalId shouldBe morphologicalId
                it.word shouldBe "test"
                it.partOfSpeech shouldBe "名詞"
                it.count shouldBe 10L
            }
        }
    }
})