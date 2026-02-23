package com.ringkhang.quizmindweb.config;

import com.google.genai.Client;
import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GeminiConfig {

    @Bean
    public Client geminiClient() {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        String apiKey = dotenv.get("GOOGLE_API_KEY");

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalStateException(
                    "❌ GOOGLE_API_KEY not found in .env file. Please add it as GOOGLE_API_KEY=your_key"
            );
        }

        System.out.println("✅ Gemini API Key loaded successfully!");

        return new Client.Builder()
                .apiKey(apiKey)
                .build();
    }
}
