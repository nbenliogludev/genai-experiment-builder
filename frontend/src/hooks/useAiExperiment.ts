import { useState } from 'react';
import { generateExperiment } from '../api/client';
import type {
    AiGenerateExperimentRequest,
    AiGenerateExperimentResponse,
} from '../api/client';

export function useAiExperiment() {
    const [loading, setLoading] = useState(false);
    const [error, setError] = useState<string | null>(null);
    const [result, setResult] = useState<AiGenerateExperimentResponse | null>(null);

    const runExperiment = async (req: AiGenerateExperimentRequest) => {
        setLoading(true);
        setError(null);
        setResult(null);

        try {
            const response = await generateExperiment(req);
            setResult(response);
        } catch (e: any) {
            setError(e?.message ?? 'Request failed');
        } finally {
            setLoading(false);
        }
    };

    return { loading, error, result, runExperiment };
}
