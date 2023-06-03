package main

import org.springframework.http.HttpStatus

class ErrorResponse(val status: HttpStatus, val error: String)