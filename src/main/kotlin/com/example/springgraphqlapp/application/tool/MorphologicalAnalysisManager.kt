package com.example.springgraphqlapp.application.tool

import com.example.springgraphqlapp.domain.model.Token

/**
 * 形態素解析を実行するインターフェース
 */
interface MorphologicalAnalysisManager {
    /**
     * 形態素解析を実施する
     */
    fun analyze(text: String): List<Token>
}