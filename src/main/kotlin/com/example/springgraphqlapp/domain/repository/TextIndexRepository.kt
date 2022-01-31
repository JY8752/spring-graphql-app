package com.example.springgraphqlapp.domain.repository

import com.example.springgraphqlapp.infrastructure.entity.TextIndexEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface TextIndexRepository : MongoRepository<TextIndexEntity, String> {
    fun findOneByTextIndexId(objectId: ObjectId): TextIndexEntity
}