package com.example.controller;

import com.example.dto.MLResultRequest;
import com.example.entity.Mould;
import com.example.service.MouldService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ml")
public class MLCallbackController {

    private final MouldService mouldService;

    public MLCallbackController(MouldService mouldService) {
        this.mouldService = mouldService;
    }

    @PostMapping("/result")
    public Mould receiveResult(@RequestBody MLResultRequest request) {
        return mouldService.handleMLResult(request);
    }
}
