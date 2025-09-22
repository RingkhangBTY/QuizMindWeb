package com.ringkhang.quizmindweb.utils;

import com.ringkhang.quizmindweb.model.UserInput;

public class GeminiUtils {

    public String getPrompt(UserInput input){

        return "Hi, please generate " + input.getNoOfQ() + " multiple-choice questions (MCQs) for the subject or " +
                "programming language: \"" + input.getProgrammingLanguage_Subject() + "\" " +
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

    public String getProperJson(String res){
        int start = res.indexOf('[');
        int end = res.lastIndexOf(']');
        if (start != -1 && end != -1 && end > start) {
            return res.substring(start, end + 1);
        } else {
            System.err.println("No valid JSON array found in the response.");
            return null;
        }
    }

}