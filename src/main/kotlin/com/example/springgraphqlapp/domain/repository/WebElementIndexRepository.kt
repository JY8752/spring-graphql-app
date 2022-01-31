package com.example.springgraphqlapp.domain.repository

import com.example.springgraphqlapp.infrastructure.entity.WebElementIndexEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface WebElementIndexRepository : MongoRepository<WebElementIndexEntity, String> {
    fun findOneByWebElementId(objectId: ObjectId): WebElementIndexEntity
}