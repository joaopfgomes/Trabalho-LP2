"use client";
import { useEffect, useState } from "react";
import { getJogadores, Jogador, getAgentes, getTimes, Agente, Time, createJogador } from "@/services/api";

export default function JogadoresPage() {
  const [jogadores, setJogadores] = useState<Jogador[]>([]);
  const [agentes, setAgentes] = useState<Agente[]>([]);
  const [times, setTimes] = useState<Time[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [nome, setNome] = useState("");
  const [posicao, setPosicao] = useState("");
  const [valorMercado, setValorMercado] = useState("");
  const [nomeAgente, setNomeAgente] = useState("");
  const [nomeTime, setNomeTime] = useState("");
  const [inicioContrato, setInicioContrato] = useState("");
  const [fimContrato, setFimContrato] = useState("");
  const [multaRescisoria, setMultaRescisoria] = useState("");
  const [clausulas, setClausulas] = useState("");

  const loadData = async () => {
    try {
      setLoading(true);
      const [jogs, agts, tms] = await Promise.all([getJogadores(), getAgentes(), getTimes()]);
      setJogadores(jogs);
      setAgentes(agts);
      setTimes(tms);
      setError("");
    } catch (err: any) {
      setError("Backend indisponível no momento. Inicie-o para visualizar os dados.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!nome || !posicao || !valorMercado || !nomeAgente) {
      setError("Preencha os campos obrigatórios (Nome, Posição, Valor e Agente).");
      return;
    }
    try {
      await createJogador({
        nomeJogador: nome,
        posicao,
        valorMercado: parseFloat(valorMercado),
        nomeAgente,
        nomeTime: nomeTime || undefined,
        inicioContrato: inicioContrato || undefined,
        fimContrato: fimContrato || undefined,
        multaRescisoria: multaRescisoria ? parseFloat(multaRescisoria) : undefined,
        clausulas: clausulas || undefined,
      });
      // Clear
      setNome(""); setPosicao(""); setValorMercado(""); setNomeAgente(""); setNomeTime("");
      setInicioContrato(""); setFimContrato(""); setMultaRescisoria(""); setClausulas("");
      loadData();
    } catch (err) {
      setError("Falha ao registrar jogador.");
    }
  };

  return (
    <div className="space-y-8 animate-in fade-in duration-500">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Mercado de Jogadores</h1>
      </div>

      {error && <div className="bg-red-500/20 text-red-200 p-4 rounded-xl border border-red-500/50">{error}</div>}

      <div className="grid lg:grid-cols-3 gap-6">
        <div className="glass-panel p-6 h-fit text-sm">
          <h2 className="text-xl font-semibold mb-4 text-emerald-300">Novo Atleta</h2>
          <form onSubmit={handleCreate} className="space-y-4">
            <div>
              <label className="block text-slate-400 mb-1">Nome do Jogador *</label>
              <input type="text" value={nome} onChange={e => setNome(e.target.value)} className="w-full bg-slate-800/50 border border-slate-700/50 rounded-lg p-2 text-white focus:outline-none focus:border-emerald-500 transition-colors" required />
            </div>
            <div className="grid grid-cols-2 gap-2">
              <div>
                <label className="block text-slate-400 mb-1">Posição *</label>
                <input type="text" value={posicao} onChange={e => setPosicao(e.target.value)} className="w-full bg-slate-800/50 border border-slate-700/50 rounded-lg p-2 text-white focus:outline-none focus:border-emerald-500 transition-colors" required placeholder="Ex: Atacante" />
              </div>
              <div>
                <label className="block text-slate-400 mb-1">Valor (R$) *</label>
                <input type="number" step="0.01" value={valorMercado} onChange={e => setValorMercado(e.target.value)} className="w-full bg-slate-800/50 border border-slate-700/50 rounded-lg p-2 text-white focus:outline-none focus:border-emerald-500 transition-colors" required />
              </div>
            </div>
            
            <div>
              <label className="block text-slate-400 mb-1">Agente Responsável *</label>
              <select value={nomeAgente} onChange={e => setNomeAgente(e.target.value)} className="w-full bg-slate-800/50 border border-slate-700/50 rounded-lg p-2 text-white focus:outline-none focus:border-emerald-500 transition-colors" required>
                <option value="">Selecione um agente</option>
                {agentes.map(a => <option key={a.nomeAgente} value={a.nomeAgente}>{a.nomeAgente}</option>)}
              </select>
            </div>

            <div className="border-t border-slate-700/50 pt-4 mt-4">
              <label className="block text-slate-400 mb-1">Time Atual (Opcional)</label>
              <select value={nomeTime} onChange={e => setNomeTime(e.target.value)} className="w-full bg-slate-800/50 border border-slate-700/50 rounded-lg p-2 text-white focus:outline-none focus:border-emerald-500 transition-colors">
                <option value="">Sem clube (Agente Livre)</option>
                {times.map(t => <option key={t.nomeTime} value={t.nomeTime}>{t.nomeTime}</option>)}
              </select>
            </div>

            {nomeTime && (
              <div className="space-y-3 bg-slate-800/30 p-3 rounded-lg border border-slate-700/30 animate-in slide-in-from-top-2">
                <p className="text-xs text-slate-400 font-medium uppercase tracking-wider">Dados do Contrato</p>
                <div className="grid grid-cols-2 gap-2">
                  <div>
                    <label className="block text-slate-400 mb-1 text-xs">Início</label>
                    <input type="date" value={inicioContrato} onChange={e => setInicioContrato(e.target.value)} className="w-full bg-slate-900/50 border border-slate-700/50 rounded-lg p-2 text-white text-xs" required={!!nomeTime} />
                  </div>
                  <div>
                    <label className="block text-slate-400 mb-1 text-xs">Fim</label>
                    <input type="date" value={fimContrato} onChange={e => setFimContrato(e.target.value)} className="w-full bg-slate-900/50 border border-slate-700/50 rounded-lg p-2 text-white text-xs" required={!!nomeTime} />
                  </div>
                </div>
                <div>
                  <label className="block text-slate-400 mb-1 text-xs">Multa Rescisória</label>
                  <input type="number" step="0.01" value={multaRescisoria} onChange={e => setMultaRescisoria(e.target.value)} className="w-full bg-slate-900/50 border border-slate-700/50 rounded-lg p-2 text-white text-xs" required={!!nomeTime} />
                </div>
                <div>
                  <label className="block text-slate-400 mb-1 text-xs">Cláusulas</label>
                  <input type="text" value={clausulas} onChange={e => setClausulas(e.target.value)} className="w-full bg-slate-900/50 border border-slate-700/50 rounded-lg p-2 text-white text-xs" required={!!nomeTime} />
                </div>
              </div>
            )}

            <button type="submit" className="w-full bg-emerald-600/80 hover:bg-emerald-500 text-white font-medium py-2.5 rounded-lg transition-colors border border-emerald-400/20 shadow-lg shadow-emerald-500/20 mt-4">
              Registrar Jogador
            </button>
          </form>
        </div>

        <div className="lg:col-span-2 space-y-4">
          <h2 className="text-xl font-semibold">Jogadores Ativos</h2>
          {loading ? (
            <div className="text-slate-400 animate-pulse">Carregando lista de ativos...</div>
          ) : jogadores.length === 0 && !error ? (
            <div className="glass-panel p-8 text-center text-slate-400 border-dashed">
              Não há jogadores na base. Crie um time e agente, e registre ao lado.
            </div>
          ) : (
            <div className="grid sm:grid-cols-2 gap-4">
              {jogadores.map((jog, idx) => (
                <div key={idx} className="glass-panel p-5 relative overflow-hidden group hover:-translate-y-1 transition-all">
                  <div className="absolute -top-10 -right-10 w-24 h-24 bg-emerald-500/10 rounded-full blur-2xl group-hover:bg-emerald-500/20 transition-all"></div>
                  
                  <div className="flex justify-between items-start mb-3">
                    <h3 className="text-lg font-bold text-white tracking-tight">{jog.nomeJogador}</h3>
                    <span className="bg-emerald-500/20 text-emerald-300 text-[10px] px-2 py-0.5 rounded-full border border-emerald-500/30 font-medium">
                      {jog.posicao}
                    </span>
                  </div>
                  
                  <div className="space-y-1.5 text-xs text-slate-300">
                    <div className="flex justify-between">
                      <span className="text-slate-500">Valor</span>
                      <span className="font-semibold text-white">R$ {jog.valorMercado.toLocaleString('pt-BR')}</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-slate-500">Clube</span>
                      <span className="font-medium text-slate-200">{jog.timeAtual?.nomeTime || 'Livre'}</span>
                    </div>
                    <div className="flex justify-between">
                      <span className="text-slate-500">Agente</span>
                      <span className="font-medium text-slate-200">{jog.agente?.nomeAgente || 'S/ Agente'}</span>
                    </div>
                  </div>
                  
                  {jog.contrato && (
                    <div className="mt-3 pt-3 border-t border-slate-700/50 text-[10px] text-slate-400">
                      Vínculo até: <span className="text-slate-300">{jog.contrato.fim}</span>
                    </div>
                  )}
                </div>
              ))}
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
