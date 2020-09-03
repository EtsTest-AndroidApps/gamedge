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

package com.paulrybitskyi.gamedge.igdb.api.datastore

import com.paulrybitskyi.gamedge.data.entities.Error as DataError
import com.paulrybitskyi.gamedge.data.utils.*
import com.paulrybitskyi.gamedge.igdb.api.entities.*
import com.paulrybitskyi.gamedge.igdb.api.entities.Error as ApiError
import com.paulrybitskyi.gamedge.igdb.api.utils.*
import com.paulrybitskyi.gamedge.igdb.api.utils.ApiAgeRating
import com.paulrybitskyi.gamedge.igdb.api.utils.ApiGame
import com.paulrybitskyi.gamedge.igdb.api.utils.ApiImage
import com.paulrybitskyi.gamedge.igdb.api.utils.ApiTimeToBeat

internal class EntityMapper {


    fun mapToDataGame(apiGame: ApiGame): DataGame {
        return DataGame(
            id = apiGame.id,
            hypeCount = apiGame.hypeCount,
            releaseDate = apiGame.releaseDate,
            criticsRating = apiGame.criticsRating,
            usersRating = apiGame.usersRating,
            totalRating = apiGame.totalRating,
            popularity = apiGame.popularity,
            name = apiGame.name,
            summary = apiGame.summary,
            storyline = apiGame.storyline,
            cover = apiGame.cover.toDataImage(),
            timeToBeat = apiGame.timeToBeat.toDataTimeToBeat(),
            ageRatings = apiGame.ageRatings.toDataAgeRatings(),
            artworks = apiGame.artworks.toDataImages(),
            screenshots = apiGame.screenshots.toDataImages(),
            genres = apiGame.genres.toDataGenres(),
            platforms = apiGame.platforms.toDataPlatforms(),
            playerPerspectives = apiGame.playerPerspectives.toDataPlayerPerspectives(),
            themes = apiGame.themes.toDataThemes(),
            modes = apiGame.modes.toDataModes(),
            keywords = apiGame.keywords.toDataKeywords(),
            involvedCompanies = apiGame.involvedCompanies.toDataInvolvedCompanies(),
            websites = apiGame.websites.toDataWebsites(),
            similarGames = apiGame.similarGames
        )
    }


    private fun ApiImage.toDataImage(): DataImage {
        return DataImage(
            width = width,
            height = height,
            url = url
        )
    }


    private fun List<ApiImage>.toDataImages(): List<DataImage> {
        return map { it.toDataImage() }
    }


    private fun ApiTimeToBeat.toDataTimeToBeat(): DataTimeToBeat {
        return DataTimeToBeat(
            completely = completely,
            hastily = hastily,
            normally = normally
        )
    }


    private fun List<ApiAgeRating>.toDataAgeRatings(): List<DataAgeRating> {
        return map {
            DataAgeRating(
                category = DataAgeRatingCategory.valueOf(it.category.name),
                type = DataAgeRatingType.valueOf(it.type.name)
            )
        }
    }


    private fun List<ApiGenre>.toDataGenres(): List<DataGenre> {
        return map {
            DataGenre(
                name = it.name
            )
        }
    }


    private fun List<ApiPlatform>.toDataPlatforms(): List<DataPlatform> {
        return map {
            DataPlatform(
                abbreviation = it.abbreviation
            )
        }
    }


    private fun List<ApiPlayerPerspective>.toDataPlayerPerspectives(): List<DataPlayerPerspective> {
        return map {
            DataPlayerPerspective(
                name = it.name
            )
        }
    }


    private fun List<ApiTheme>.toDataThemes(): List<DataTheme> {
        return map {
            DataTheme(
                name = it.name
            )
        }
    }


    private fun List<ApiMode>.toDataModes(): List<DataMode> {
        return map {
            DataMode(
                name = it.name
            )
        }
    }


    private fun List<ApiKeyword>.toDataKeywords(): List<DataKeyword> {
        return map {
            DataKeyword(
                name = it.name
            )
        }
    }


    private fun List<ApiInvolvedCompany>.toDataInvolvedCompanies(): List<DataInvolvedCompany> {
        return map {
            DataInvolvedCompany(
                company = it.company.toDataCompany(),
                isDeveloper = it.isDeveloper,
                isPublisher = it.isPublisher,
                isPorter = it.isPorter
            )
        }
    }


    private fun ApiCompany.toDataCompany(): DataCompany {
        return DataCompany(
            name = name,
            developedGames = developedGames
        )
    }


    private fun List<ApiWebsite>.toDataWebsites(): List<DataWebsite> {
        return map {
            DataWebsite(
                url = it.url,
                category = DataWebsiteCategory.valueOf(it.category.name),
                isTrusted = it.isTrusted
            )
        }
    }


    fun mapToDataError(apiError: ApiError): DataError = with(apiError) {
        return when {
            isServerError -> DataError.ServiceUnavailable
            isHttpError -> DataError.ClientError(httpErrorMessage)
            isNetworkError -> DataError.NetworkError(networkErrorMessage)
            isUnknownError -> DataError.Unknown(unknownErrorMessage)

            else -> throw IllegalStateException("Could not map the api error $this to a data error.")
        }
    }


}


internal fun EntityMapper.mapToDataGames(apiGames: List<ApiGame>): List<DataGame> {
    return apiGames.map(::mapToDataGame)
}