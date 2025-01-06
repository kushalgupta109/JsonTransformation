package com.example.datatransformation.jsonparserandfilter.controller;

import com.example.datatransformation.jsonparserandfilter.Service.ParserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ParserControllerTest {
    @InjectMocks
    private ParserService parserService;
    private ObjectMapper objectMapper;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        objectMapper = new ObjectMapper();
    }
    @Test
    void testTransformJson_basicTransformation() throws IOException {
        String inputJson = "{\"id\": 1, \"name\": \"Alice\", \"email\": \"alice@example.com\", \"age\": 30, \"subscriptionStatus\": \"active\"}," +
                "  {\"id\": 2, \"name\": \"Bob\", \"email\": \"bob@example.com\", \"age\": 25, \"subscriptionStatus\": \"inactive\"}";
        String jslTemplate = "\n" +
                "{\n" +
                "  \"id\" : .id,\n" +
                "  \"name\" : .name,\n" +
                "  \"email\" : .email,\n" +
                "  \"age\" : .age,\n" +
                "  \"isAdult\" : .age>18,\n" +
                "  \"isSubscribed\" : .subscriptionStatus==\"active\",\n" +
                "}";

        String expectedJson = "{\"id\":1,\"name\":\"Alice\",\"email\":\"alice@example.com\",\"age\":30," +
                "\"isAdult\":true,\"isSubscribed\":true}";

        String resultJson = parserService.parseJson(inputJson, jslTemplate);
        assertEquals(expectedJson, resultJson);
    }

    @Test
    void testTransformJson_withNullInputJson() throws IOException {
        String jslTemplate = "{name: .name}";

        assertThrows(NullPointerException.class, () -> {
            parserService.parseJson(null, jslTemplate);
        });
    }

    @Test
    void testTransformJson_withInvalidTemplate() throws IOException {
        String inputJson = "{name:John}";
        String invalidJslTemplate = "{age: .age}";

        assertThrows(IOException.class, () -> {
            parserService.parseJson(inputJson, invalidJslTemplate);
        });
    }
}