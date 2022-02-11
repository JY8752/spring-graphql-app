package com.example.springgraphqlapp.infrastructure.tool

import com.atilika.kuromoji.ipadic.Token
import com.atilika.kuromoji.ipadic.Tokenizer
import com.example.springgraphqlapp.application.tool.MorphologicalAnalysisManager
import org.springframework.stereotype.Component

/**
 * 形態素解析を実施する実装クラス
 */
@Component
class MorphologicalAnalysisManagerImpl : MorphologicalAnalysisManager {
    private val tokenizer = Tokenizer()

    override fun analyze(text: String) = tokenizer.tokenize(text).map { toModel(it) }

    private fun toModel(token: Token) = com.example.springgraphqlapp.domain.model.Token(
        token.surface,
        token.position,
        token.partOfSpeechLevel1,
        token.partOfSpeechLevel2,
        token.partOfSpeechLevel3,
        token.partOfSpeechLevel4
    )
}