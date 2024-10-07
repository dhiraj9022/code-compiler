package com.codecompiler.controller;

import com.codecompiler.dto.CodeRequest;
import com.codecompiler.dto.CodeResponse;
import com.codecompiler.service.CompilerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/compiler")
public class CompilerController {

    @Autowired
    private CompilerService compilerService;

    @PostMapping("/run")
    public CodeResponse compileAndRunCode(@RequestBody CodeRequest codeRequest) {
        return compilerService.compileAndRun(codeRequest);
    }

}
