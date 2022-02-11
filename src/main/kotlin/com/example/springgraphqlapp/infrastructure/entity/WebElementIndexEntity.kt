package com.example.springgraphqlapp.infrastructure.entity

import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import org.springframework.data.mongodb.core.mapping.DocumentReference

@Document(collection = "web_element_index")
open class WebElementIndexEntity(
    @Id
    val webElementId: ObjectId = ObjectId.get(),
    @DocumentReference
    var elements: List<ElementEntity> = emptyList()


) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as WebElementIndexEntity

        if (webElementId != other.webElementId) return false
        if (elements != other.elements) return false

        return true
    }

    override fun hashCode(): Int {
        var result = webElementId.hashCode()
        result = 31 * result + elements.hashCode()
        return result
    }

    override fun toString(): String {
        return "WebElementIndexEntity(webElementId=$webElementId, elements=$elements)"
    }
}


