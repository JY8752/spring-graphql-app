package com.example.springgraphqlapp.domain.model

/**
 * 形態素解析結果の1単語と対応するデータクラス
 */
data class Token(
    val surface: String,
    val position: Int,
    val partOfSpeechOfLevel1: String,
    val partOfSpeechOfLevel2: String,
    val partOfSpeechOfLevel3: String,
    val partOfSpeechOfLevel4: String,
)
