package com.example.springgraphqlapp.infrastructure.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference

@Document(collection = "morphological_analysis_result")
data class MorphologicalAnalysisResultEntity(
    @Id
    val morphologicalId: ObjectId = ObjectId.get(),
    val word: String,
    val partOfSpeech: String,
    val count: Long,
    @DocumentReference(lazy = true)
    private val textIndex: TextIndexEntity
)