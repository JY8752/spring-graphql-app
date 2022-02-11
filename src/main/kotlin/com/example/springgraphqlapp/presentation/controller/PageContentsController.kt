package com.example.springgraphqlapp.presentation.controller

import com.example.springgraphqlapp.application.service.PageContentService
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
class PageContentsController(
    private val pageContentService: PageContentService
) {

    @PutMapping("/analyze")
    fun analyze(@RequestParam urls: List<String>) {
       runBlocking {
           urls.forEach {
               launch { pageContentService.analyzeUrl(it) }
           }
       }
    }
}