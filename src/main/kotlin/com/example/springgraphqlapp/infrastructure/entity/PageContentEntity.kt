package com.example.springgraphqlapp.infrastructure.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

@Document(collection = "page_content")
data class PageContentEntity(
    @Id
    val contentId: ObjectId = ObjectId.get(),
    val url: String,
    val textIndexId: ObjectId? = null,
    val webElementIndexId: ObjectId? = null,
    val isError: Boolean = false,
    val errorReason: String? = null,
    val code: Int? = null
)