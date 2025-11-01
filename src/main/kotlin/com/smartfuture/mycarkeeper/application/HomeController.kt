package com.smartfuture.mycarkeeper.application

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import org.springframework.http.ResponseEntity

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController


@RestController
@Tag(name = "Home", description = "Welcome endpoint")
class HomeController {

    @GetMapping("/")
    @Operation(summary = "Welcome message", description = "Returns a welcome message for the My Car Keeper application")
    fun home(): ResponseEntity<Map<String, String>> =
        ResponseEntity.ok(mapOf("message" to "Welcome to My Car Keeper Application!"))
}
