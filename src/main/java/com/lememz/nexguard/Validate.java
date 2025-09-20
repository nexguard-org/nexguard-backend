package com.lememz.nexguard;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class Validate {
    
    @GetMapping("/validate")
    public ResponseEntity<ValidateResponse> validate() {
        return ResponseEntity.ok().build();
    }
}
