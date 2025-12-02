package com.nbenliogludev.genaiexperimentbuilder.ai;

import com.nbenliogludev.genaiexperimentbuilder.dto.request.AiGenerateExperimentRequest;
import org.springframework.stereotype.Component;

/**
 * @author nbenliogludev
 */
@Component
public class ExperimentPromptFactory {

    public String buildSystemPrompt() {
        return """
        You are an expert CRO & UX specialist.
        You return ONLY valid JSON.
        You NEVER include raw HTML inside JSON fields except in "modifiedHtml".
        You NEVER repeat the original HTML in your JSON output.
        """;
    }

    public String buildUserPrompt(AiGenerateExperimentRequest request, String pageHtml) {
        return """
        You will receive:

        - An experiment idea
        - A goal
        - A page URL
        - The full HTML of the page

        Your job:
        Produce several A/B test variants.

        SCHEMA (STRICT):

        {
          "experimentName": "string",
          "variants": [
            {
              "name": "string",
              "description": "string",
              "uiChanges": "string",
              "modifiedHtml": "string (raw HTML with modifications)",
              "explanation": "string",
              "trafficShare": 0.5
            }
          ]
        }

        RULES:
        - modifiedHtml MUST contain valid HTML.
        - DO NOT include backticks, markdown, comments, or explanations outside JSON.
        - DO NOT embed the original HTML into the JSON unless it is the modified version.
        - JSON ONLY.

        Input:

        idea: %s
        goal: %s
        pageUrl: %s

        ORIGINAL_HTML (do not repeat this directly in JSON unless modified):
        <<<HTML_START>>>
        %s
        <<<HTML_END>>>
        """.formatted(
                request.getIdea(),
                request.getGoal(),
                request.getPage(),
                pageHtml
        );
    }
}