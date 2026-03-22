export const API_BASE_URL = 'http://localhost:8080';

// Tipos base baseados na API Java
export interface Jogador {
  nomeJogador: string;
  posicao: string;
  valorMercado: number;
  timeAtual?: Time | null;
  agente?: Agente | null;
  contrato?: Contrato | null;
}

export interface Time {
  nomeTime: string;
  saldoCaixa: number;
  jogadores?: Jogador[];
}

export interface Agente {
  nomeAgente: string;
  jogadoresAgenciados?: Jogador[];
}

export interface Transferencia {
  jogador: Jogador;
  timeOrigem: Time | null;
  timeDestino: Time;
  multaRescisoria: number;
  luvas: number;
  valor: number;
  data: string;
  comissaoAgente: number;
}

export interface Contrato {
  inicio: string;
  fim: string;
  multaRescisoria: number;
  clausulas: string;
}

// Funções de requisição genéricas
async function fetchApi<T>(endpoint: string, options?: RequestInit): Promise<T> {
  const url = `${API_BASE_URL}${endpoint}`;
  try {
    const res = await fetch(url, {
      ...options,
      headers: {
        'Content-Type': 'application/json',
        ...(options?.headers || {}),
      },
    });
    if (!res.ok) {
      throw new Error(`Erro na requisição para ${endpoint}: ${res.statusText}`);
    }
    return await res.json();
  } catch (error) {
    console.error('API Error:', error);
    throw error;
  }
}

// Times
export const getTimes = () => fetchApi<Time[]>('/times', { cache: 'no-store' });
export const createTime = (data: { nomeTime: string; saldoCaixa: number }) => 
  fetchApi<Time>('/times', { method: 'POST', body: JSON.stringify(data) });

// Agentes
export const getAgentes = () => fetchApi<Agente[]>('/agentes', { cache: 'no-store' });
export const createAgente = (data: { nomeAgente: string }) => 
  fetchApi<Agente>('/agentes', { method: 'POST', body: JSON.stringify(data) });

// Jogadores
export const getJogadores = () => fetchApi<Jogador[]>('/jogadores', { cache: 'no-store' });
export const createJogador = (data: { 
  nomeJogador: string; posicao: string; valorMercado: number; 
  nomeAgente: string; nomeTime?: string; inicioContrato?: string; 
  fimContrato?: string; multaRescisoria?: number; clausulas?: string;
}) => fetchApi<string>('/jogadores', { method: 'POST', body: JSON.stringify(data) });

// Transferencias
export const getTransferencias = () => fetchApi<Transferencia[]>('/transferencias', { cache: 'no-store' });
export const createTransferencia = (data: {
  nomeJogador: string; nomeTimeDestino: string; valor: number;
  luvas: number; multaRescisoria: number; comissaoAgente: number;
  inicioContrato?: string; fimContrato?: string; multaContrato?: number; clausulasContrato?: string;
}) => fetchApi<string>('/transferencias', { method: 'POST', body: JSON.stringify(data) });
