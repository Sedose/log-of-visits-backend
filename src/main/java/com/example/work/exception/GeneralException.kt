package com.example.work.exception

class GeneralException(
    val code: ErrorCode,
) : RuntimeException()
