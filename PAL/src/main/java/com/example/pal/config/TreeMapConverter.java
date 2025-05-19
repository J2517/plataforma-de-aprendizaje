package com.example.pal.config;

import java.io.IOException;
import java.util.TreeMap;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

@Converter
public class TreeMapConverter implements AttributeConverter<TreeMap<Integer, String>, String> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(TreeMap<Integer, String> attribute) {
        try {
            return objectMapper.writeValueAsString(attribute);
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error serializing TreeMap", e);
        }
    }

    @Override
    public TreeMap<Integer, String> convertToEntityAttribute(String dbData) {
        try {
            return objectMapper.readValue(dbData, new TypeReference<TreeMap<Integer, String>>() {});
        } catch (IOException e) {
            throw new RuntimeException("Error deserializing TreeMap", e);
        }
    }
}
