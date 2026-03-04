package com.ringkhang.quizmindweb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.reflect.TypeToken;
import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;
import com.ringkhang.quizmindweb.DTO.QuizDetails;
import com.ringkhang.quizmindweb.model.UserInput;
import com.ringkhang.quizmindweb.utils.GeminiUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeminiService {

    private final Client client;
    private final Gson gson;

    public List<QuizDetails> getQuestions(UserInput input) throws JsonProcessingException {
        try {
            GeminiUtils geminiUtils = new GeminiUtils();

            // Generate content with streaming support
            GenerateContentResponse response = client.models.generateContent(
                    "gemini-2.5-flash",
                    geminiUtils.getPrompt(input),
                    null
            );

            String rawResponse = response.text();

            if (rawResponse == null || rawResponse.isEmpty()) {
                log.error("Gemini API returned empty response");
                return new ArrayList<>();
            }

            log.info("Raw response length: {} characters", rawResponse.length());

            // Clean the JSON response
            String cleanJson = geminiUtils.getProperJson(rawResponse);

            if (cleanJson == null || cleanJson.isEmpty()) {
                log.error("Failed to extract proper JSON from response");
                return new ArrayList<>();
            }

            log.info("Cleaned JSON length: {} characters", cleanJson.length());

            // ✅ Parse JSON directly without file operations using streaming
            return parseJsonStream(cleanJson);

        } catch (JsonSyntaxException e) {
            log.error("Failed to parse JSON response: {}", e.getMessage());
            throw new JsonProcessingException("Invalid JSON format from Gemini API", e) {};
        } catch (Exception e) {
            log.error("Error generating questions: {}", e.getMessage(), e);
            throw new RuntimeException("Failed to generate questions", e);
        }
    }

    // ✅ Use streaming JSON parser for large responses
    private List<QuizDetails> parseJsonStream(String jsonString) {
        try {
            JsonReader reader = new JsonReader(new StringReader(jsonString));
            reader.setLenient(true); // Handle malformed JSON more gracefully

            Type type = new TypeToken<List<QuizDetails>>() {}.getType();
            List<QuizDetails> questions = gson.fromJson(reader, type);

            if (questions == null) {
                log.warn("Parsed questions list is null, returning empty list");
                return new ArrayList<>();
            }

            log.info("Successfully parsed {} questions", questions.size());
            return questions;

        } catch (JsonSyntaxException e) {
            log.error("JSON parsing error: {}", e.getMessage());
            return new ArrayList<>();
        }
    }
}