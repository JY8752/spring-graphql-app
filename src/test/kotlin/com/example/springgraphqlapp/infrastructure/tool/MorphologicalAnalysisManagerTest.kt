package com.example.springgraphqlapp.infrastructure.tool

import com.example.springgraphqlapp.application.tool.MorphologicalAnalysisManager
import io.kotest.assertions.throwables.shouldNotThrowAny
import io.kotest.core.spec.style.StringSpec
import io.kotest.matchers.shouldBe
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class MorphologicalAnalysisManagerTest(
    private val morphologicalAnalysisManager: MorphologicalAnalysisManager
) : StringSpec() {
    init {
        "形態素解析ができること" {
            val text = "これは形態素解析のテストです。kotlinはかわいいですね。"
            shouldNotThrowAny { morphologicalAnalysisManager.analyze(text).forEach { println(it) } }
        }
        "空文字" {
            val text = ""
            val tokens = morphologicalAnalysisManager.analyze(text)
            tokens.size shouldBe 0
        }
    }
}