package com.example.datatransformation.jsonparserandfilter.controller;

import com.example.datatransformation.jsonparserandfilter.Service.ParserService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/api")
public class ParserController {
    @Autowired
    private ParserService parserService;
    @PostMapping("/parse")
    public String parserJson(@RequestParam String inputJson, @RequestParam String jslTemplate) throws IOException {
        return parserService.parseJson(inputJson, jslTemplate);
    }
}
