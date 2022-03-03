package com.example.springgraphqlapp.application.datafetchers

import com.example.springgraphqlapp.application.service.PageContentService
import com.example.springgraphqlapp.generated.types.PageContent
import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsQuery
import org.bson.types.ObjectId

@DgsComponent
class PageContentFetchers(
    private val pageContentService: PageContentService
) {
    @DgsQuery
    fun pageContent(id: String): PageContent =
        pageContentService.getPageContent(ObjectId(id))
}