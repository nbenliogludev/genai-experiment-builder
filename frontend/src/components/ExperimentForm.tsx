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

        if (!idea.trim() || !page.trim() || !goal.trim()) {
            return;
        }

        const payload: AiGenerateExperimentRequest = {
            idea,
            page,
            goal,
            // если в типе AiGenerateExperimentRequest пока ещё есть pageHtml,
            // можно временно передать пустую строку:
            // pageHtml: '',
        } as AiGenerateExperimentRequest;

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
                    className="w-full rounded-xl border border-slate-700 bg-slate-950 px-3 py-2 text-xs text-slate-100 outline-none focus:border-sky-400 focus:ring-1 focus:ring-sky-400"
                />
            </label>

            <label className="flex flex-col gap-1 text-xs text-slate-300">
                Page URL or path
                <input
                    type="text"
                    value={page}
                    onChange={(e) => setPage(e.target.value)}
                    placeholder="https://example.com/checkout or /checkout"
                    required
                    className="w-full rounded-xl border border-slate-700 bg-slate-950 px-3 py-2 text-xs text-slate-100 outline-none focus:border-sky-400 focus:ring-1 focus:ring-sky-400"
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
                    className="w-full rounded-xl border border-slate-700 bg-slate-950 px-3 py-2 text-xs text-slate-100 outline-none focus:border-sky-400 focus:ring-1 focus:ring-sky-400"
                />
            </label>

            <button
                type="submit"
                disabled={loading}
                className={[
                    'mt-2 inline-flex items-center justify-center rounded-full px-4 py-2 text-sm font-semibold',
                    'w-full text-slate-50 shadow-lg shadow-emerald-500/40',
                    loading
                        ? 'bg-slate-600 cursor-default shadow-none'
                        : 'bg-emerald-500 hover:bg-emerald-400 transition',
                ].join(' ')}
            >
                {loading ? 'Generating…' : 'Generate experiment'}
            </button>
        </form>
    );
}
