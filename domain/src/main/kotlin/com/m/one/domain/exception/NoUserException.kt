package com.m.one.domain.exception

import java.lang.RuntimeException

class NoUserException: RuntimeException {

    constructor(msg: String): super(msg)
    constructor(msg: String, cause: Throwable): super(msg, cause)

}