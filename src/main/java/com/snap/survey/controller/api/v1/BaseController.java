package com.snap.survey.controller.api.v1;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class BaseController {

  @GetMapping("/ping")
  public ResponseEntity<String> ping() {
    return ResponseEntity.ok("Pong \n");
  }
}
