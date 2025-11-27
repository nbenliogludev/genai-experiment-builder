import { useState } from 'react';
import type { AiGenerateExperimentRequest } from '../api/client';

type Mode = 'basic' | 'html';

interface ExperimentFormProps {
    loading: boolean;
    onSubmit: (payload: AiGenerateExperimentRequest) => void;
}

export function ExperimentForm({ loading, onSubmit }: ExperimentFormProps) {
    const [mode, setMode] = useState<Mode>('basic');
    const [idea, setIdea] = useState('');
    const [page, setPage] = useState('https://example.com/checkout');
    const [goal, setGoal] = useState('increase_conversion');
    const [pageHtml, setPageHtml] = useState('');

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        const payload: AiGenerateExperimentRequest = {
            idea,
            page,
            goal,
            pageHtml: mode === 'html' ? pageHtml : '',
        };

        onSubmit(payload);
    };

    return (
        <form onSubmit={handleSubmit} className="grid gap-3">
            {/* Tabs */}
            <div className="flex gap-2 bg-slate-950 rounded-full p-1 mb-1">
                <button
                    type="button"
                    onClick={() => setMode('basic')}
                    className={[
                        'flex-1 rounded-full px-3 py-2 text-xs font-medium transition',
                        mode === 'basic'
                            ? 'bg-sky-400 text-slate-950 shadow-[0_0_0_1px_rgba(56,189,248,0.7)]'
                            : 'bg-slate-950 text-slate-200 shadow-[0_0_0_1px_#111827]',
                    ].join(' ')}
                >
                    Basic (URL only)
                </button>
                <button
                    type="button"
                    onClick={() => setMode('html')}
                    className={[
                        'flex-1 rounded-full px-3 py-2 text-xs font-medium transition',
                        mode === 'html'
                            ? 'bg-sky-400 text-slate-950 shadow-[0_0_0_1px_rgba(56,189,248,0.7)]'
                            : 'bg-slate-950 text-slate-200 shadow-[0_0_0_1px_#111827]',
                    ].join(' ')}
                >
                    Advanced (with HTML)
                </button>
            </div>

            {/* Fields */}
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

            {mode === 'html' && (
                <label className="flex flex-col gap-1 text-xs text-slate-300">
                    Page HTML (optional snippet)
                    <textarea
                        value={pageHtml}
                        onChange={(e) => setPageHtml(e.target.value)}
                        placeholder="<!DOCTYPE html>..."
                        className="w-full min-h-[120px] rounded-xl border border-slate-700 bg-slate-950 px-3 py-2 text-xs text-slate-100 outline-none focus:border-sky-400 focus:ring-1 focus:ring-sky-400 font-mono resize-y"
                    />
                </label>
            )}

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
