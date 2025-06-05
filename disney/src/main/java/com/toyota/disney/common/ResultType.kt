package com.toyota.disney.common

import com.toyota.disney.repo.model.DisneyCharResponse

sealed interface ResultType {
    data class Success(val response: DisneyCharResponse) : ResultType
    data class Error(val message: String) : ResultType
}