package com.example.springgraphqlapp.domain.repository

import com.example.springgraphqlapp.infrastructure.entity.PageContentEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface PageContentRepository : MongoRepository<PageContentEntity, String> {
    fun findOneByContentId(objectId: ObjectId): PageContentEntity
}