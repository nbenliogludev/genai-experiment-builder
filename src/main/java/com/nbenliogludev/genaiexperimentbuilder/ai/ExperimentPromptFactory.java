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

    public String buildUserPrompt(AiGenerateExperimentRequest request) {

        String base = """
                Create an A/B test based on the following input:

                Idea: %s
                Page: %s
                Goal: %s

                The goal is to improve UX and conversions. Propose 2-3 strong variants.
                """
                .formatted(
                        request.getIdea(),
                        request.getPage(),
                        request.getGoal()
                );

        if (request.getPageHtml() == null || request.getPageHtml().isBlank()) {
            return base;
        }

        String htmlPart = """
                
                Here is the HTML of the page. Use it to propose concrete UI changes.
                Focus on modifying existing elements (texts, colors, buttons) instead of
                inventing a totally new layout.

                HTML:
                ---
                %s
                ---
                """.formatted(request.getPageHtml());

        return base + htmlPart;
    }
}