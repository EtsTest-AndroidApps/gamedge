/*
 * Copyright 2020 Paul Rybitskyi, paul.rybitskyi.work@gmail.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.paulrybitskyi.gamedge.data.games.datastores

import com.paulrybitskyi.gamedge.data.commons.Pagination
import com.paulrybitskyi.gamedge.data.games.DataCompany
import com.paulrybitskyi.gamedge.data.games.DataGame
import kotlinx.coroutines.flow.Flow

interface GamesLocalDataStore {
    suspend fun saveGames(games: List<DataGame>)
    suspend fun getGame(id: Int): DataGame?
    suspend fun getCompanyDevelopedGames(company: DataCompany, pagination: Pagination): List<DataGame>
    suspend fun getSimilarGames(game: DataGame, pagination: Pagination): List<DataGame>
    suspend fun searchGames(searchQuery: String, pagination: Pagination): List<DataGame>
    suspend fun observePopularGames(pagination: Pagination): Flow<List<DataGame>>
    suspend fun observeRecentlyReleasedGames(pagination: Pagination): Flow<List<DataGame>>
    suspend fun observeComingSoonGames(pagination: Pagination): Flow<List<DataGame>>
    suspend fun observeMostAnticipatedGames(pagination: Pagination): Flow<List<DataGame>>
}
