"use client";
import { useEffect, useState } from "react";
import { getAgentes, createAgente, Agente } from "@/services/api";

export default function AgentesPage() {
  const [agentes, setAgentes] = useState<Agente[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");
  const [nome, setNome] = useState("");

  const loadAgentes = async () => {
    try {
      setLoading(true);
      const data = await getAgentes();
      setAgentes(data);
      setError("");
    } catch (err: any) {
      setError("A API não está respondendo. Verifique se o servidor backend Java foi iniciado.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadAgentes();
  }, []);

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!nome) return;
    try {
      await createAgente({ nomeAgente: nome });
      setNome("");
      loadAgentes();
    } catch (err) {
      setError("Falha ao registrar agente empresarial.");
    }
  };

  return (
    <div className="space-y-8 animate-in fade-in duration-500">
      <h1 className="text-3xl font-bold">Agentes Empresariais</h1>
      {error && <div className="bg-red-500/20 text-red-200 p-4 rounded-xl border border-red-500/50">{error}</div>}

      <div className="grid lg:grid-cols-3 gap-6">
        <div className="glass-panel p-6 h-fit">
          <h2 className="text-xl font-semibold mb-4">Registrar Agente</h2>
          <form onSubmit={handleCreate} className="space-y-4">
            <div>
              <label className="block text-sm text-slate-400 mb-1">Nome Completo do Agente</label>
              <input type="text" value={nome} onChange={e => setNome(e.target.value)} className="w-full bg-slate-800/50 border border-slate-700/50 rounded-lg p-2.5 text-white focus:outline-none focus:border-cyan-500 transition-colors" required />
            </div>
            <button type="submit" className="w-full bg-cyan-600/80 hover:bg-cyan-500 text-white font-medium py-2.5 rounded-lg transition-colors shadow-lg shadow-cyan-500/20 border border-cyan-400/20">
              Cadastrar
            </button>
          </form>
        </div>

        <div className="lg:col-span-2 space-y-4">
          <h2 className="text-xl font-semibold">Agentes Licenciados</h2>
          {loading ? (
            <div className="text-slate-400 animate-pulse">Sincronizando com a base de dados...</div>
          ) : agentes.length === 0 ? (
            <div className="glass-panel p-8 text-center text-slate-400 border-dashed">Nenhum agente registrado no sistema FIFA.</div>
          ) : (
            <div className="grid sm:grid-cols-2 gap-4">
              {agentes.map((agente, idx) => (
                <div key={idx} className="glass-panel p-5 space-y-2 relative overflow-hidden group">
                  <div className="absolute top-0 right-0 w-16 h-16 bg-cyan-500/10 rounded-full blur-2xl group-hover:bg-cyan-500/30 transition-colors"></div>
                  <h3 className="text-lg font-bold text-white group-hover:text-cyan-400 transition-colors">💼 {agente.nomeAgente}</h3>
                  <div className="text-sm text-slate-400">
                    <p>Clientes agenciados: <span className="text-white font-medium">{agente.jogadoresAgenciados?.length || 0}</span></p>
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
