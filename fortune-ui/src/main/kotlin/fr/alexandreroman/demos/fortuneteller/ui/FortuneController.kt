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

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * REST controller exposing fortunes to the UI.
 */
@RestController
class FortuneController(private val client: FortuneServiceClient) {
    @GetMapping("/fortune")
    fun getFortune(): FortuneResponse {
        val fortune = client.randomFortune()
        return FortuneResponse(fortune.text, fortune.host)
    }
}

/**
 * Class holding a fortune and maybe the host which generated it.
 * The host may not be set if the Fortune API is not available.
 */
data class FortuneResponse(val text: String, val host: String?)
