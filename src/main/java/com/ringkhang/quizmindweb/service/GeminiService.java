package com.ringkhang.quizmindweb.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.google.common.reflect.TypeToken;
import com.google.genai.Client;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.ringkhang.quizmindweb.model.Questions;
import com.ringkhang.quizmindweb.model.ScoreHistoryTable;
import com.ringkhang.quizmindweb.model.UserInput;
import com.ringkhang.quizmindweb.utils.GeminiUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GeminiService {

    private final Client client = new Client();

    public List<Questions> getQuestions(UserInput input) throws JsonProcessingException {
        GeminiUtils geminiUtils = new GeminiUtils();
        String res = client
                .models
                .generateContent("gemini-2.5-flash", geminiUtils.getPrompt(input), null)
                .text();
        String properJson = geminiUtils.getProperJson(res);

        // Write JSON to file
        String filePath = "response.json";
        try (FileWriter fileWriter = new FileWriter(filePath)) {
            fileWriter.write(properJson);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write response to file", e);
        }

        // Read JSON from file and map to List<Questions>
        String fileContent;
        try {
            fileContent = new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            throw new RuntimeException("Failed to read response from file", e);
        }
        Gson gson = new Gson();
        try {
            Type type = new TypeToken<List<Questions>>() {}.getType();
            return gson.fromJson(fileContent, type);
        } catch (JsonSyntaxException e) {
            System.err.println("Failed to parse JSON: " + e.getMessage());
            return null;
        }
    }

    public ScoreHistoryTable evaluateResult() {
        return new ScoreHistoryTable();
    }
}