package org.scraper.common;

import java.util.Calendar;
import java.util.Optional;

import com.google.genai.Client;
import com.google.genai.types.FinishReason;
import com.google.genai.types.GenerateContentResponse;

public class GeminiService {
    private static final String AI_PROMPT = """
        Today's date is %s
        Summarize this book announcement. Focus on the title, author, special features, prices, and dates.
        Only get dates and prices that is specific to the US (not International like UK and Canada).
        If a year is not specified, then assume it is the current year if the date has not passed. Else, assume it is next year.
        At the end, create a link that would create a google calendar entry with the US Early Access Date
        and the description as the summary with proper multiple lines. Again, if a year is not specified,
        then assume it is the current year if the date has not passed. Else, assume it is next year.: \n%s
        """;
    private final Client client;

    public GeminiService() {
        // Automatically picks up API key from GOOGLE_API_KEY environment variable
        // Or you can use: Client.builder().apiKey("YOUR_KEY").build();
        this.client = Client.builder().build();
    }

    public String summarize(String text) {
        try {
            // Using gemini-2.0-flash-001 for state-of-the-art speed/quality
            GenerateContentResponse response = client.models.generateContent(
                "gemini-2.5-flash", 
                String.format(AI_PROMPT, Calendar.getInstance().getTime(), text), 
                null
            );

            Optional<FinishReason> finishReason = response.candidates().get().get(0).finishReason();
            if (!finishReason.isPresent() && finishReason.get().knownEnum().equals(FinishReason.Known.STOP)) {
                System.out.println("Finish Reason: " + response.candidates().get().get(0).finishReason().get());
            }
            
            // The .text() method is a quick accessor for the generated string
            return response.text();
        } catch (Exception e) {
            return "Failed to summarize: " + e.getMessage();
        }
    }
}