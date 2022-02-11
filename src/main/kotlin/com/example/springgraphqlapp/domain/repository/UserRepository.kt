package com.example.springgraphqlapp.domain.repository

import com.example.springgraphqlapp.infrastructure.entity.UserEntity
import org.bson.types.ObjectId
import org.springframework.data.mongodb.repository.MongoRepository

interface UserRepository : MongoRepository<UserEntity, String> {
    fun findOneByUserId(objectId: ObjectId): UserEntity
}