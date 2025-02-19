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

package com.paulrybitskyi.gamedge.commons.api

import com.paulrybitskyi.gamedge.commons.api.Error as ApiError
import com.paulrybitskyi.gamedge.data.commons.entities.Error as DataError
import javax.inject.Inject

class ErrorMapper @Inject constructor() {

    fun mapToDataError(apiError: ApiError): DataError = with(apiError) {
        return when {
            isServerError -> DataError.ApiError.ServiceUnavailable
            isHttpError -> DataError.ApiError.ClientError(httpErrorMessage)
            isNetworkError -> DataError.ApiError.NetworkError(networkErrorMessage)
            isUnknownError -> DataError.ApiError.Unknown(unknownErrorMessage)

            else -> throw IllegalStateException("Could not map the api error $this to a data error.")
        }
    }
}
