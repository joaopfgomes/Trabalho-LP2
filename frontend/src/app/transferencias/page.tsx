"use client";
import { useEffect, useState } from "react";
import { getTransferencias, getTimes, getJogadores, createTransferencia, Transferencia, Time, Jogador } from "@/services/api";

export default function TransferenciasPage() {
  const [transferencias, setTransferencias] = useState<Transferencia[]>([]);
  const [times, setTimes] = useState<Time[]>([]);
  const [jogadores, setJogadores] = useState<Jogador[]>([]);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState("");

  const [nomeJogador, setNomeJogador] = useState("");
  const [nomeTimeDestino, setNomeTimeDestino] = useState("");
  const [valor, setValor] = useState("");
  const [luvas, setLuvas] = useState("");
  const [multaRescisoria, setMultaRescisoria] = useState("");
  const [comissaoAgente, setComissaoAgente] = useState("");

  const [inicioContrato, setInicioContrato] = useState("");
  const [fimContrato, setFimContrato] = useState("");
  const [multaContrato, setMultaContrato] = useState("");
  const [clausulasContrato, setClausulasContrato] = useState("");

  const loadData = async () => {
    try {
      setLoading(true);
      const [transData, timesData, jogsData] = await Promise.all([getTransferencias(), getTimes(), getJogadores()]);
      setTransferencias(transData);
      setTimes(timesData);
      setJogadores(jogsData);
      setError("");
    } catch (err: any) {
      setError("Não foi possível carregar o painel. Conecte o servidor Java em localhost:8080.");
    } finally {
      setLoading(false);
    }
  };

  useEffect(() => {
    loadData();
  }, []);

  const handleCreate = async (e: React.FormEvent) => {
    e.preventDefault();
    if (!nomeJogador || !nomeTimeDestino || !valor) {
      setError("Selecione Jogador, Time Destino e estipule o Valor base.");
      return;
    }
    try {
      await createTransferencia({
        nomeJogador,
        nomeTimeDestino,
        valor: parseFloat(valor),
        luvas: luvas ? parseFloat(luvas) : 0,
        multaRescisoria: multaRescisoria ? parseFloat(multaRescisoria) : 0,
        comissaoAgente: comissaoAgente ? parseFloat(comissaoAgente) : 0,
        inicioContrato: inicioContrato || undefined,
        fimContrato: fimContrato || undefined,
        multaContrato: multaContrato ? parseFloat(multaContrato) : undefined,
        clausulasContrato: clausulasContrato || undefined
      });
      setNomeJogador(""); setNomeTimeDestino(""); setValor(""); setLuvas(""); setMultaRescisoria("");
      setComissaoAgente(""); setInicioContrato(""); setFimContrato(""); setMultaContrato(""); setClausulasContrato("");
      loadData();
    } catch (err) {
      setError("Falha ao registrar a transferência no BID.");
    }
  };

  return (
    <div className="space-y-8 animate-in fade-in duration-500">
      <div className="flex justify-between items-center">
        <h1 className="text-3xl font-bold">Boletim Informativo Diário (BID)</h1>
      </div>

      {error && <div className="bg-red-500/20 text-red-200 p-4 rounded-xl border border-red-500/50">{error}</div>}

      <div className="grid lg:grid-cols-3 gap-6">
        <div className="glass-panel p-6 h-fit text-sm">
          <h2 className="text-xl font-semibold mb-4 text-indigo-300">Nova Negociação</h2>
          <form onSubmit={handleCreate} className="space-y-4">
            <div>
              <label className="block text-slate-400 mb-1">Jogador *</label>
              <select value={nomeJogador} onChange={e => setNomeJogador(e.target.value)} className="w-full bg-slate-800/50 border border-slate-700/50 rounded-lg p-2 text-white focus:outline-none focus:border-indigo-500 transition-colors" required>
                <option value="">Selecione o atleta</option>
                {jogadores.map(j => <option key={j.nomeJogador} value={j.nomeJogador}>{j.nomeJogador} (Atual: {j.timeAtual?.nomeTime || 'Livre'})</option>)}
              </select>
            </div>
            <div>
              <label className="block text-slate-400 mb-1">Time Destino *</label>
              <select value={nomeTimeDestino} onChange={e => setNomeTimeDestino(e.target.value)} className="w-full bg-slate-800/50 border border-slate-700/50 rounded-lg p-2 text-white focus:outline-none focus:border-indigo-500 transition-colors" required>
                <option value="">Selecione o clube destino</option>
                {times.map(t => <option key={t.nomeTime} value={t.nomeTime}>{t.nomeTime} (Caixa: {t.saldoCaixa})</option>)}
              </select>
            </div>

            <div className="grid grid-cols-2 gap-2">
              <div>
                <label className="block text-slate-400 mb-1">Valor Venda *</label>
                <input type="number" step="0.01" value={valor} onChange={e => setValor(e.target.value)} className="w-full bg-slate-800/50 border border-slate-700/50 rounded-lg p-2 text-white" required placeholder="0.00" />
              </div>
              <div>
                <label className="block text-slate-400 mb-1">Luvas</label>
                <input type="number" step="0.01" value={luvas} onChange={e => setLuvas(e.target.value)} className="w-full bg-slate-800/50 border border-slate-700/50 rounded-lg p-2 text-white" placeholder="0.00" />
              </div>
              <div>
                <label className="block text-slate-400 mb-1">Pag. Multa</label>
                <input type="number" step="0.01" value={multaRescisoria} onChange={e => setMultaRescisoria(e.target.value)} className="w-full bg-slate-800/50 border border-slate-700/50 rounded-lg p-2 text-white" placeholder="0.00" />
              </div>
              <div>
                <label className="block text-slate-400 mb-1">Com. Agente</label>
                <input type="number" step="0.01" value={comissaoAgente} onChange={e => setComissaoAgente(e.target.value)} className="w-full bg-slate-800/50 border border-slate-700/50 rounded-lg p-2 text-white" placeholder="0.00" />
              </div>
            </div>

            <div className="bg-slate-800/30 p-3 rounded-lg border border-slate-700/30">
              <p className="text-xs text-slate-400 font-medium tracking-wider mb-2">NOVO CONTRATO</p>
              <div className="grid grid-cols-2 gap-2 mb-2">
                <input type="date" value={inicioContrato} onChange={e => setInicioContrato(e.target.value)} className="w-full bg-slate-900/50 border border-slate-700/50 rounded-lg p-2 text-white text-xs" title="Início" />
                <input type="date" value={fimContrato} onChange={e => setFimContrato(e.target.value)} className="w-full bg-slate-900/50 border border-slate-700/50 rounded-lg p-2 text-white text-xs" title="Fim" />
              </div>
              <input type="number" value={multaContrato} onChange={e => setMultaContrato(e.target.value)} className="w-full bg-slate-900/50 border border-slate-700/50 rounded-lg p-2 text-white text-xs mb-2" placeholder="Nova Multa Rescisória" />
              <input type="text" value={clausulasContrato} onChange={e => setClausulasContrato(e.target.value)} className="w-full bg-slate-900/50 border border-slate-700/50 rounded-lg p-2 text-white text-xs" placeholder="Cláusulas Extras" />
            </div>

            <button type="submit" className="w-full bg-indigo-600/80 hover:bg-indigo-500 text-white font-medium py-2.5 rounded-lg transition-colors border border-indigo-400/20 shadow-lg shadow-indigo-500/20">
              Processar Transferência
            </button>
          </form>
        </div>

        <div className="lg:col-span-2 space-y-4">
          <h2 className="text-xl font-semibold">Histórico de Transações</h2>
          {loading ? (
            <div className="text-slate-400 animate-pulse">Sincronizando livro contábil...</div>
          ) : transferencias.length === 0 ? (
            <div className="glass-panel p-8 text-center text-slate-400 border-dashed">Nenhuma transferência realizada ainda.</div>
          ) : (
            <div className="overflow-x-auto glass-panel p-0">
              <table className="w-full text-left border-collapse text-sm">
                <thead>
                  <tr className="border-b border-slate-700/50 bg-slate-800/50 text-slate-400">
                    <th className="p-4 font-semibold">Data</th>
                    <th className="p-4 font-semibold">Jogador</th>
                    <th className="p-4 font-semibold">Origem ➔ Destino</th>
                    <th className="p-4 font-semibold">Transação Bruta</th>
                  </tr>
                </thead>
                <tbody>
                  {transferencias.map((trans, idx) => (
                    <tr key={idx} className="border-b border-slate-800/50 hover:bg-slate-800/80 transition-colors group">
                      <td className="p-4 text-slate-500">{trans.data}</td>
                      <td className="p-4 font-medium text-slate-200">{trans.jogador.nomeJogador}</td>
                      <td className="p-4">
                        <span className="text-rose-400 text-xs">{trans.timeOrigem?.nomeTime || 'Livre'}</span> 
                        <span className="mx-2 text-slate-500">➔</span> 
                        <span className="text-emerald-400 font-medium">{trans.timeDestino.nomeTime}</span>
                      </td>
                      <td className="p-4 text-indigo-300 font-medium tracking-wide">
                        R$ {trans.valor.toLocaleString('pt-BR')}
                      </td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          )}
        </div>
      </div>
    </div>
  );
}
