import { ExperimentForm } from './components/ExperimentForm';
import { ExperimentResult } from './components/ExperimentResult';
import { useAiExperiment } from './hooks/useAiExperiment';

function App() {
    const { loading, error, result, runExperiment } = useAiExperiment();

    return (
        <div className="min-h-screen flex items-center justify-center px-4 py-10 bg-slate-950 bg-[radial-gradient(circle_at_top,_#1f2937_0,_#020617_55%,_#000000_100%)]">
            <div className="w-full max-w-3xl rounded-3xl bg-slate-900/95 border border-slate-800 shadow-2xl shadow-black/60 px-6 py-7 text-slate-100">

                <h1 className="text-3xl md:text-4xl font-bold tracking-tight mb-2">
                    GenAI Experiment Builder
                </h1>

                <p className="text-xs md:text-sm text-slate-400 mb-6 max-w-xl">
                    Enter an idea and a URL. If the URL belongs to localhost, HTML will be
                    fetched from the browser. If not, it will be fetched from the backend.
                </p>

                <ExperimentForm loading={loading} onSubmit={runExperiment} />

                {error && (
                    <div className="mt-4 rounded-lg border border-red-400/70 bg-red-900/40 px-3 py-2 text-xs text-red-100">
                        {error}
                    </div>
                )}

                <ExperimentResult result={result} />
            </div>
        </div>
    );
}

export default App;
