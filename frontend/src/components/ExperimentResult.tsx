import type { AiGenerateExperimentResponse } from '../api/client';

interface ExperimentResultProps {
    result: AiGenerateExperimentResponse | null;
}

export function ExperimentResult({ result }: ExperimentResultProps) {
    if (!result) return null;

    const { experiment, variants, rawIdea } = result;

    return (
        <div className="mt-8 space-y-6">
            <section>
                <h2 className="text-base md:text-lg font-semibold mb-3">Experiment</h2>
                <div className="rounded-2xl border border-slate-700 bg-slate-950/60 px-4 py-3 text-xs md:text-sm grid gap-1.5 md:grid-cols-2">
                    <p>
                        <span className="text-slate-400">ID: </span>
                        <span className="font-mono">{experiment.id}</span>
                    </p>
                    <p>
                        <span className="text-slate-400">Name: </span>
                        <span>{experiment.name}</span>
                    </p>
                    <p>
                        <span className="text-slate-400">Goal: </span>
                        <span>{experiment.goal}</span>
                    </p>
                    <p>
                        <span className="text-slate-400">Page: </span>
                        <span>{experiment.page}</span>
                    </p>
                    <p>
                        <span className="text-slate-400">Status: </span>
                        <span className="inline-flex items-center rounded-full bg-slate-800 px-2 py-0.5 text-[10px] uppercase tracking-wide text-slate-100">
              {experiment.status}
            </span>
                    </p>
                    <p className="col-span-full">
                        <span className="text-slate-400">Raw idea: </span>
                        <span>{rawIdea}</span>
                    </p>
                </div>
            </section>

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
                                <span className="text-[10px] text-slate-400">Variant {idx + 1}</span>
                            </div>
                            <p className="text-slate-300 mb-1.5">
                                <span className="font-semibold text-slate-200">Description: </span>
                                {v.description}
                            </p>
                            <p className="text-slate-300">
                                <span className="font-semibold text-slate-200">UI changes: </span>
                                {v.uiChanges}
                            </p>
                        </div>
                    ))}
                </div>
            </section>
        </div>
    );
}
