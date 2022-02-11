package com.example.springgraphqlapp.domain.repository

import com.example.springgraphqlapp.infrastructure.entity.MorphologicalAnalysisResultEntity
import com.example.springgraphqlapp.infrastructure.entity.TextIndexEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface MorphologicalAnalysisResultRepository : MongoRepository<MorphologicalAnalysisResultEntity, String> {
    fun findOneByMorphologicalId(objectId: ObjectId): MorphologicalAnalysisResultEntity
    fun findByTextIndex(textIndex: TextIndexEntity): List<MorphologicalAnalysisResultEntity>
}