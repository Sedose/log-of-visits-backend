package com.example.work.controller.advice

import org.springframework.web.bind.annotation.ControllerAdvice
import lombok.RequiredArgsConstructor
import lombok.experimental.FieldDefaults
import com.example.work.exception.ErrorCode
import com.example.work.exception.GeneralException
import lombok.AccessLevel
import lombok.Value
import lombok.experimental.Delegate
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
class GeneralControllerAdvice (
    private val map: ErrorCodeToResponseEntityMap
) {
    @ExceptionHandler(value = [GeneralException::class])
    fun handleException(
        exception: GeneralException
    ) = map.map[exception.code]
}

@Value
class ErrorCodeToResponseEntityMap (
    @Delegate
    val map: Map<ErrorCode, ResponseEntity<ResponseBody>>
)

@Configuration
open class Config {

    @Bean
    open fun errorCodeToResponseMap() =
        ErrorCodeToResponseEntityMap(
            mapOf(
                ErrorCode.ACCESS_TOKEN_INVALID to
                        ResponseEntity.status(HttpStatus.FORBIDDEN)
                            .body(ResponseBody("access.token.invalid")),

                ErrorCode.CANNOT_GET_USER_BY_FULL_NAME to
                        ResponseEntity.status(HttpStatus.NOT_FOUND)
                            .body(ResponseBody("cannot.get.user.by.full.name")),

                ErrorCode.CANNOT_EXTRACT_PARTS_FROM_USER_FULL_NAME to
                        ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ResponseBody("cannot.extract.parts.from.user.full.name}")),

                ErrorCode.TOO_FREQUENT_FILE_UPLOADS to
                        ResponseEntity.status(HttpStatus.BAD_REQUEST)
                            .body(ResponseBody("to.frequent.file.uploads"))
            )
        )
}

class ResponseBody (
    val errorCode: String
)
