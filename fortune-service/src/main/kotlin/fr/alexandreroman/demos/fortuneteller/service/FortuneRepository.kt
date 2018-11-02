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

package fr.alexandreroman.demos.fortuneteller.service

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

/**
 * [Fortune] repository.
 */
interface FortuneRepository : JpaRepository<Fortune, Long> {
    /**
     * Get a random fortune.
     */
    // Implementation note: we use a native query in order to use the LIMIT keyword
    // (which is not available in JPA query language).
    // This query may not be fully portable across database implementations, since
    // we are using a raw SQL construct.
    @Query("select text from fortunes order by RAND() limit 1", nativeQuery = true)
    fun getRandom(): String
}
