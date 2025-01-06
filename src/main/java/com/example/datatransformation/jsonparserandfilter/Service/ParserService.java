package com.example.datatransformation.jsonparserandfilter.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.schibsted.spt.data.jslt.Expression;
import com.schibsted.spt.data.jslt.Parser;
import org.springframework.stereotype.Service;

@Service
public class ParserService {
    private final ObjectMapper objectMapper = new ObjectMapper();


    public String parseJson(String inputJson, String jslTemplate) throws JsonProcessingException {

        if (inputJson == null) {
            throw new NullPointerException("Input JSON cannot be null");
        }
        JsonNode inputNode = objectMapper.readTree(inputJson);
        //JsonNode templateNode = objectMapper.readTree(jslTemplate);
        Expression jslt = Parser.compileString(jslTemplate);
        return jslt.apply(inputNode).toString();
    }
}
