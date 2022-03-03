package com.example.springgraphqlapp.application.service

import com.example.springgraphqlapp.application.tool.CustomHtmlParser
import com.example.springgraphqlapp.application.tool.MorphologicalAnalysisManager
import com.example.springgraphqlapp.domain.model.Token
import com.example.springgraphqlapp.domain.repository.MorphologicalAnalysisResultRepository
import com.example.springgraphqlapp.domain.repository.TextIndexRepository
import com.example.springgraphqlapp.generated.types.MorphologicalAnalysisResult
import com.example.springgraphqlapp.generated.types.TextIndex
import com.example.springgraphqlapp.infrastructure.entity.MorphologicalAnalysisResultEntity
import com.example.springgraphqlapp.infrastructure.entity.TextIndexEntity
import org.bson.types.ObjectId
import org.springframework.stereotype.Service

/**
 * 取得したhtmlコンテンツからテキスト指標を抽出するサービスクラス
 */
@Service
class TextIndexService(
    private val customHtmlParser: CustomHtmlParser,
    private val morphologicalAnalysisManager: MorphologicalAnalysisManager,
    private val textIndexRepository: TextIndexRepository,
    private val morphologicalAnalysisResultRepository: MorphologicalAnalysisResultRepository
) {

    /**
     * htmlから本文を抽出しレコードを登録する
     */
    fun extractMainText(html: String): ObjectId? {
        customHtmlParser.extractText(html)?.let {
            return textIndexRepository.save(TextIndexEntity(
                mainText = it,
                textCount = it.length.toLong(),
            )).textIndexId
        }
        return null
    }

    /**
     * 指定されたテキストを形態素解析し、単語ごとにレコード登録する
     * 戻り値: 登録したレコード数
     */
    fun morphologicalAnalyze(textIndexId: ObjectId): Int {
        val textIndex = textIndexRepository.findOneByTextIndexId(textIndexId)
        val tokens = morphologicalAnalysisManager.analyze(textIndex.mainText).filter { it.surface.isNotBlank() }
        val wordCountMap = tokens.groupBy { it.surface }.mapValues { TokenCountAndPartOfSpeech(it.value) }

        val morphologicalEntities = wordCountMap.map {
           morphologicalAnalysisResultRepository.save(MorphologicalAnalysisResultEntity(
               word = it.key,
               count = it.value.count,
               partOfSpeech = it.value.partOfSpeech,
               textIndex = textIndex
           ))
        }

        //親レコード更新
        textIndex.morphologicalAnalysisResults = morphologicalEntities
        textIndexRepository.save(textIndex)

        return wordCountMap.size
    }

    /**
     * textIndexを取得する
     */
    fun getTextIndex(id: ObjectId): TextIndex {
        val entity = textIndexRepository.findOneByTextIndexId(id)
        return TextIndex(
            id.toString(),
            entity.mainText,
            entity.textCount.toInt(),
            entity.morphologicalAnalysisResults.map {
                MorphologicalAnalysisResult(
                    it.morphologicalId.toString(),
                    it.word,
                    it.partOfSpeech,
                    it.count.toInt()
                )
            }
        )
    }
}

private data class TokenCountAndPartOfSpeech(val count: Long, val partOfSpeech: String) {
    constructor(tokens: List<Token>) : this(tokens.size.toLong(), tokens[0].partOfSpeechOfLevel1)
}