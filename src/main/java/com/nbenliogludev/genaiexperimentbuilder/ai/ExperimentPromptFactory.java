package com.nbenliogludev.genaiexperimentbuilder.ai;

import com.nbenliogludev.genaiexperimentbuilder.dto.AiGenerateExperimentRequest;
import org.springframework.stereotype.Component;

/**
 * @author nbenliogludev
 */
@Component
public class ExperimentPromptFactory {

    public String buildSystemPrompt() {
        return """
                You are a senior CRO (Conversion Rate Optimization) and A/B testing expert.

                You receive:
                - Business idea and goal,
                - Target page path,
                - Optionally, a snippet of the page HTML.

                Your task is:
                - Design a clear A/B test for this page.
                - Propose 2-3 strong variants.
                - When HTML is provided, base your UI changes on existing elements
                  (buttons, texts, sections).

                Return ONLY valid JSON, no Markdown, in the following structure:

                {
                  "experimentName": "string",
                  "variants": [
                    {
                      "name": "string",
                      "description": "string",
                      "uiChanges": "string"
                    }
                  ]
                }
                """;
    }

    public String buildUserPrompt(AiGenerateExperimentRequest request, String pageHtml) {
        return """
               You are an AI experiment designer...

               Page URL: %s
               Goal: %s

               Here is the current HTML of the page:
               ---
               %s
               ---
               """.formatted(request.getPage(), request.getGoal(), pageHtml);
    }
}