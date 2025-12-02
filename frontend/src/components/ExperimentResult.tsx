import { useState } from 'react';
import type { AiGenerateExperimentResponse } from '../api/client';

interface ExperimentResultProps {
    result: AiGenerateExperimentResponse | null;
}

export function ExperimentResult({ result }: ExperimentResultProps) {
    const [previewHtml, setPreviewHtml] = useState<string | null>(null);

    if (!result) return null;

    const { experiment, variants, rawIdea } = result;

    const decodeHtml = (b64: string) => {
        try {
            return atob(b64);
        } catch (err) {
            console.error("Base64 decode error:", err);
            return `
                <html>
                    <body style="padding:20px; font-family:sans-serif;">
                        <h2>Error decoding HTML</h2>
                        <p>This variant HTML could not be decoded.</p>
                    </body>
                </html>
            `;
        }
    };

    return (
        <div className="mt-8 space-y-6">

            {/* ======================
                EXPERIMENT INFO
            ======================= */}
            <section>
                <h2 className="text-base md:text-lg font-semibold mb-3">Experiment</h2>

                <div className="rounded-2xl border border-slate-700 bg-slate-950/60 px-4 py-3 text-xs grid gap-1.5">
                    <p><b>ID:</b> {experiment.id}</p>
                    <p><b>Name:</b> {experiment.name}</p>
                    <p><b>Goal:</b> {experiment.goal}</p>
                    <p><b>Page:</b> {experiment.page}</p>
                    <p><b>Status:</b> {experiment.status}</p>
                    <p><b>Idea:</b> {rawIdea}</p>
                </div>
            </section>


            {/* ======================
                GENERATED VARIANTS
            ======================= */}
            <section>
                <h2 className="text-base md:text-lg font-semibold mb-3">Generated variants</h2>

                <div className="grid gap-4 md:grid-cols-2">
                    {variants.map((v, idx) => (
                        <div
                            key={idx}
                            className="rounded-2xl border border-slate-700 bg-slate-950/60 px-4 py-3 text-xs md:text-sm"
                        >
                            <div className="flex items-center justify-between mb-1.5">
                                <h3 className="font-semibold">{v.name}</h3>
                                <span className="text-[10px] text-slate-400">#{idx + 1}</span>
                            </div>

                            <p className="mb-1.5">
                                <b>Description:</b> {v.description}
                            </p>

                            <p className="mb-1.5">
                                <b>UI changes:</b> {v.uiChanges}
                            </p>

                            {v.explanation && (
                                <p className="mb-1.5">
                                    <b>Explanation:</b> {v.explanation}
                                </p>
                            )}

                            <button
                                className="mt-3 w-full rounded-xl bg-sky-500 px-3 py-2 text-white text-xs hover:bg-sky-400 transition"
                                onClick={() => setPreviewHtml(decodeHtml(v.modifiedHtmlBase64))}
                            >
                                Preview Variant
                            </button>
                        </div>
                    ))}
                </div>
            </section>


            {/* ======================
                PREVIEW MODAL (IFRAME)
            ======================= */}
            {previewHtml && (
                <div className="fixed inset-0 bg-black/70 flex items-center justify-center p-6 z-50">
                    <div className="bg-white w-full h-full md:w-[90%] md:h-[90%] rounded-xl shadow-xl relative overflow-hidden">

                        <button
                            className="absolute top-3 right-3 bg-red-500 text-white px-3 py-1 rounded hover:bg-red-600"
                            onClick={() => setPreviewHtml(null)}
                        >
                            Close
                        </button>

                        <iframe
                            className="w-full h-full border-none"
                            srcDoc={previewHtml}
                            sandbox="allow-same-origin allow-scripts allow-popups allow-forms"
                        />
                    </div>
                </div>
            )}
        </div>
    );
}
