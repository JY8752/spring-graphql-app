package com.example.springgraphqlapp.domain.repository

import com.example.springgraphqlapp.infrastructure.entity.ElementEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface ElementRepository : MongoRepository<ElementEntity, String> {
    fun findOneByElementId(objectId: ObjectId): ElementEntity
}