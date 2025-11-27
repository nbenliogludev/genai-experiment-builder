export interface ExperimentResponse {
    id: number;
    name: string;
    goal: string;
    page: string;
    status: string;
    createdAt: string;
    updatedAt: string;
}

export interface AiGeneratedVariantDto {
    name: string;
    description: string;
    uiChanges: string;
}

export interface AiGenerateExperimentResponse {
    experiment: ExperimentResponse;
    variants: AiGeneratedVariantDto[];
    rawIdea: string;
}

export interface AiGenerateExperimentRequest {
    idea: string;
    page: string;
    goal: string;
    pageHtml: string;
}

export async function generateExperiment(
    req: AiGenerateExperimentRequest
): Promise<AiGenerateExperimentResponse> {
    const response = await fetch('/api/experiments/ai-generate', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(req),
    });

    if (!response.ok) {
        throw new Error(`Request failed with status ${response.status}`);
    }

    return response.json();
}
