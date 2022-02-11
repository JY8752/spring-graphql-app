package com.example.springgraphqlapp.infrastructure.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference

@Document(collection = "text_index")
open class TextIndexEntity(
    @Id
    val textIndexId: ObjectId = ObjectId.get(),
    val mainText: String,
    val textCount: Long,
    @DocumentReference
    var morphologicalAnalysisResults: List<MorphologicalAnalysisResultEntity> = emptyList()


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as TextIndexEntity

        if (textIndexId != other.textIndexId) return false
        if (mainText != other.mainText) return false
        if (textCount != other.textCount) return false
        if (morphologicalAnalysisResults != other.morphologicalAnalysisResults) return false

        return true
    }

    override fun hashCode(): Int {
        var result = textIndexId.hashCode()
        result = 31 * result + mainText.hashCode()
        result = 31 * result + textCount.hashCode()
        result = 31 * result + morphologicalAnalysisResults.hashCode()
        return result
    }

    override fun toString(): String {
        return "TextIndexEntity(textIndexId=$textIndexId, mainText='$mainText', textCount=$textCount, morphologicalAnalysisResults=${morphologicalAnalysisResults.map { it.morphologicalId }})"
    }
}
