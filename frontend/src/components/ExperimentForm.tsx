import { useState } from 'react';
import type { AiGenerateExperimentRequest } from '../api/client';

interface ExperimentFormProps {
    loading: boolean;
    onSubmit: (payload: AiGenerateExperimentRequest) => void;
}

export function ExperimentForm({ loading, onSubmit }: ExperimentFormProps) {
    const [idea, setIdea] = useState('');
    const [page, setPage] = useState('https://example.com/checkout');
    const [goal, setGoal] = useState('increase_conversion');

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        if (!idea.trim() || !page.trim() || !goal.trim()) return;

        const payload: AiGenerateExperimentRequest = { idea, page, goal };
        onSubmit(payload);
    };

    return (
        <form onSubmit={handleSubmit} className="grid gap-3">

            <label className="flex flex-col gap-1 text-xs text-slate-300">
                Idea
                <input
                    type="text"
                    value={idea}
                    onChange={(e) => setIdea(e.target.value)}
                    placeholder="Test different CTA text and color"
                    required
                    className="w-full rounded-xl border border-slate-700 bg-slate-950 px-3 py-2 text-xs text-slate-100"
                />
            </label>

            <label className="flex flex-col gap-1 text-xs text-slate-300">
                Page URL
                <input
                    type="text"
                    value={page}
                    onChange={(e) => setPage(e.target.value)}
                    placeholder="https://example.com"
                    required
                    className="w-full rounded-xl border border-slate-700 bg-slate-950 px-3 py-2 text-xs text-slate-100"
                />
            </label>

            <label className="flex flex-col gap-1 text-xs text-slate-300">
                Goal
                <input
                    type="text"
                    value={goal}
                    onChange={(e) => setGoal(e.target.value)}
                    placeholder="increase_conversion"
                    required
                    className="w-full rounded-xl border border-slate-700 bg-slate-950 px-3 py-2 text-xs text-slate-100"
                />
            </label>

            <button
                type="submit"
                disabled={loading}
                className="mt-2 w-full rounded-full bg-emerald-500 px-4 py-2 text-sm font-semibold text-white"
            >
                {loading ? 'Generating…' : 'Generate experiment'}
            </button>
        </form>
    );
}
