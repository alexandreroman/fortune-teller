/*
 * Copyright (c) 2018 Pivotal Software, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package fr.alexandreroman.demos.fortuneteller.ui

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.GetMapping

/**
 * Fortune API service interface.
 */
@FeignClient("fortune-service", fallback = FortuneServiceClientFallback::class)
interface FortuneServiceClient {
    // We use Feign to generate a Fortune API client at runtime.
    // This REST client is powered by Hystrix: if the remote endpoint becomes
    // unavailable, a fallback implementation is used.
    // Moreover, this REST client is also using Ribbon to load balance traffic
    // from the client side.
    // All these features are automatically managed by Spring Cloud Netflix.

    @GetMapping("/v1/random")
    fun randomFortune(): Fortune
}

/**
 * Component defining fallback methods used when the Fortune API is not available.
 */
@Component
class FortuneServiceClientFallback(private val props: FortuneProperties) : FortuneServiceClient {
    override fun randomFortune() = Fortune(props.fallbackFortune)
}

data class Fortune(val text: String, val host: String? = null)
