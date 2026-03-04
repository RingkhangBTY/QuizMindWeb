package com.ringkhang.quizmindweb.utils;

import com.ringkhang.quizmindweb.model.UserInput;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GeminiUtils {

    public String getPrompt(UserInput input){

        return "Hi, please generate " + input.getNoOfQ() + " multiple-choice questions (MCQs) for the subject or " +
                "programming language/subject: \"" + input.getProgrammingLanguage_Subject() + "\" " +
                "based on the topic/concepts: \"" + input.getShortDes_Topic_Concepts() + "\" " +
                "with difficulty level: \"" + input.getLevel() + "\". " +
                "Each question should have exactly 4 distinct options labeled as optionA, optionB, optionC, and optionD. " +
                "The correct answer should be provided as the exact text of the correct option (not just the letter). " +
                "Also give me a short explanation why that option is correct"+
                "Return the output in the following strict JSON format, and do not include any explanation or extra text " +
                "before or after the JSON:\n\n" +
                "{\n" +

                "  \"questions\": [\n" +
                "    {\n" +
                "      \"question\": \"<Question text>\",\n" +
                "      \"optionA\": \"<Option A>\",\n" +
                "      \"optionB\": \"<Option B>\",\n" +
                "      \"optionC\": \"<Option C>\",\n" +
                "      \"optionD\": \"<Option D>\",\n" +
                "      \"answer\": \"<Correct Option Text>\"\n" +
                "      \"explanation\": \"<Explanation>\"\n" +
                "    }\n" +
                "  ]\n" +
                "}\n\n" +
                "Plz ensures that the response start with { and also ended with }"+
                "Please ensure the questions are clear, relevant to the topic, and all options are distinct and plausible," +
                "And tried to make the options a bit tricky and ask the questions in a bit different ways.";
    }

    public String getProperJson(String rawResponse) {
        if (rawResponse == null || rawResponse.isEmpty()) {
            return "";
        }

        try {
            // Remove markdown code blocks
            String cleaned = rawResponse
                    .replaceAll("```json\\s*", "")
                    .replaceAll("```\\s*", "")
                    .trim();

            //Find JSON array boundaries
            int startIndex = cleaned.indexOf('[');
            int endIndex = cleaned.lastIndexOf(']');

            if (startIndex == -1 || endIndex == -1 || startIndex >= endIndex) {
                log.error("No valid JSON array found in response");
                return "";
            }

            String jsonArray = cleaned.substring(startIndex, endIndex + 1);

            //Basic validation
            if (!isValidJsonArray(jsonArray)) {
                log.warn("JSON array structure may be invalid");
            }

            return jsonArray;

        } catch (Exception e) {
            log.error("Error cleaning JSON: {}", e.getMessage());
            return "";
        }
    }

    private boolean isValidJsonArray(String json) {
        if (json == null || json.length() < 2) {
            return false;
        }

        json = json.trim();
        return json.startsWith("[") && json.endsWith("]");
    }


}