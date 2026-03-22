"use client";
import { useEffect, useState } from "react";
import { getTimes, createTime, Time } from "@/services/api";

export default function TimesPage() {
  const [times, setTimes] = useState<Time[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [nome, setNome] = useState("");
  const [saldo, setSaldo] = useState("");

  const loadTimes = async () => {
    try {
      setLoading(true);
      const data = await getTimes();
      setTimes(data);
      setError("");
    } catch (err: any) {
      setError("Erro ao conectar com a API Backend (Verifique se o Spring Boot está rodando na porta 8080).");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadTimes();
  }, []);

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!nome || !saldo) return;
    try {
      await createTime({ nomeTime: nome, saldoCaixa: parseFloat(saldo) });
      setNome("");
      setSaldo("");
      loadTimes();
    } catch (err) {
      setError("Falha ao criar. Verifique o backend.");
    }
  };

  return (
    <div className="space-y-8 animate-in fade-in duration-500">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Times & Elencos</h1>
      </div>

      {error && <div className="bg-red-500/20 text-red-200 p-4 rounded-xl border border-red-500/50">{error}</div>}

      <div className="grid lg:grid-cols-3 gap-6">
        <div className="glass-panel p-6 h-fit">
          <h2 className="text-xl font-semibold mb-4">Novo Time</h2>
          <form onSubmit={handleCreate} className="space-y-4">
            <div>
              <label className="block text-sm text-slate-400 mb-1">Nome do Time</label>
              <input type="text" value={nome} onChange={e => setNome(e.target.value)} className="w-full bg-slate-800/50 border border-slate-700/50 rounded-lg p-2.5 text-white focus:outline-none focus:border-blue-500 transition-colors" required />
            </div>
            <div>
              <label className="block text-sm text-slate-400 mb-1">Saldo em Caixa Inicial</label>
              <input type="number" step="0.01" value={saldo} onChange={e => setSaldo(e.target.value)} className="w-full bg-slate-800/50 border border-slate-700/50 rounded-lg p-2.5 text-white focus:outline-none focus:border-blue-500 transition-colors" required />
            </div>
            <button type="submit" className="w-full bg-blue-600/80 hover:bg-blue-500 text-white font-medium py-2.5 rounded-lg transition-colors border border-blue-400/20 shadow-lg shadow-blue-500/20">
              Registrar Clube
            </button>
          </form>
        </div>

        <div className="lg:col-span-2 space-y-4">
          <h2 className="text-xl font-semibold">Clubes Cadastrados</h2>
          {loading ? (
            <div className="text-slate-400 animate-pulse">Sincronizando com a base de dados...</div>
          ) : times.length === 0 ? (
            <div className="glass-panel p-8 text-center text-slate-400 border-dashed">Nenhum clube registrado.</div>
          ) : (
            <div className="grid sm:grid-cols-2 gap-4">
              {times.map((time, idx) => (
                <div key={idx} className="glass-panel p-5 space-y-2 relative overflow-hidden group">
                  <div className="absolute top-0 right-0 w-16 h-16 bg-blue-500/10 rounded-full blur-2xl group-hover:bg-blue-500/30 transition-colors"></div>
                  <h3 className="text-lg font-bold text-white group-hover:text-blue-400 transition-colors">{time.nomeTime}</h3>
                  <div className="text-sm text-slate-400 space-y-1">
                    <p>Caixa: <span className="text-emerald-400 font-medium tracking-wide">R$ {time.saldoCaixa.toLocaleString('pt-BR')}</span></p>
                    <p>Total de Jogadores: <span className="text-white font-medium">{time.jogadores?.length || 0}</span></p>
                  </div>
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
