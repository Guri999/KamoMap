package kr.co.common.mapper

import kr.co.common.model.ApiResponse
import kr.co.common.model.EntityWrapper

abstract class ApiMapper<LEFT, RIGHT> {
    fun convertResponse(result: ApiResponse<LEFT>): EntityWrapper<RIGHT> =
        when (result) {
            is ApiResponse.Success -> EntityWrapper.Success(convert(result.data))
            is ApiResponse.Error -> EntityWrapper.Error(result.error)
        }

    protected abstract fun convert(left: LEFT): RIGHT
}