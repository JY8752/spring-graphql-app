package com.example.springgraphqlapp.infrastructure.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference

@Document(collection = "element")
data class ElementEntity(
    @Id
    val elementId: ObjectId = ObjectId.get(),
    val elementTag: String,
    val count: Long,
    @DocumentReference(lazy=true)
    private val webElementIndex: WebElementIndexEntity
)