package com.example.springgraphqlapp.application.datafetchers

import com.netflix.graphql.dgs.DgsComponent
import com.netflix.graphql.dgs.DgsSubscription
import org.reactivestreams.Publisher
import reactor.core.publisher.Flux
import java.time.Duration

@DgsComponent
class CountFetchers {
    @DgsSubscription
    fun stocks(): Publisher<Stock> {
        return Flux.interval(Duration.ofSeconds(1)).map { Stock("test", 500 + it) }
    }
}

data class Stock(val name: String, val price: Long)