package com.smartfuture.mycarkeeper.application.web

import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping

@Controller
class WebHomeController {

    @GetMapping("/web/home")
    fun home(): String = "index"
}
