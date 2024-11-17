package com.segundop.clinicasystem.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/protected-route")
    public String protectedRoute(){
        return "Acceso permitido a la ruta protegida";
    }
}
