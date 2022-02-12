package com.m.one.domain.exception

import java.lang.RuntimeException

class AlreadyUserException: RuntimeException {

    constructor(msg: String): super(msg)
    constructor(msg: String, cause: Throwable): super(msg, cause)

}