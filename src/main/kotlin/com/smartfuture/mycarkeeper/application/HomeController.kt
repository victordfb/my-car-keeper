package com.smartfuture.mycarkeeper.application

import com.smartfuture.mycarkeeper.application.dto.MessageResponse
import org.springframework.boot.autoconfigure.SpringBootApplication

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@SpringBootApplication
class SmartFutureApplication

@RestController
class HomeController {

    @GetMapping("/")
    fun home(): MessageResponse {
        return MessageResponse("Welcome to My Car Keeper Application!")
    }
}
