const API_URL = "http://localhost:8080";

const times = [
  { nomeTime: "Flamengo", saldoCaixa: 250000000.0 },
  { nomeTime: "Palmeiras", saldoCaixa: 300000000.0 },
  { nomeTime: "São Paulo", saldoCaixa: 150000000.0 },
  { nomeTime: "Atlético Mineiro", saldoCaixa: 120000000.0 },
  { nomeTime: "Fluminense", saldoCaixa: 80000000.0 },
  { nomeTime: "Botafogo", saldoCaixa: 400000000.0 },
  { nomeTime: "Cruzeiro", saldoCaixa: 100000000.0 },
  { nomeTime: "Vasco da Gama", saldoCaixa: 90000000.0 },
  { nomeTime: "Grêmio", saldoCaixa: 75000000.0 },
  { nomeTime: "Corinthians", saldoCaixa: 110000000.0 }
];

const agentes = [
  { nomeAgente: "André Cury" },
  { nomeAgente: "Carlos Leite" },
  { nomeAgente: "Giuliano Bertolucci" },
  { nomeAgente: "Eduardo Uram" },
  { nomeAgente: "Wagner Ribeiro" }
];

const jogadores = [
  { nomeJogador: "Pedro", posicao: "Atacante", valorMercado: 120000000, nomeAgente: "André Cury", nomeTime: "Flamengo", inicioContrato: "2023-01-01", fimContrato: "2027-12-31", multaRescisoria: 500000000, clausulas: "Cláusula artilheiro" },
  { nomeJogador: "Arrascaeta", posicao: "Meio-Campo", valorMercado: 90000000, nomeAgente: "Giuliano Bertolucci", nomeTime: "Flamengo", inicioContrato: "2022-01-01", fimContrato: "2026-12-31", multaRescisoria: 300000000, clausulas: "Direitos de imagem 100%" },
  { nomeJogador: "Dudu", posicao: "Atacante", valorMercado: 50000000, nomeAgente: "André Cury", nomeTime: "Palmeiras", inicioContrato: "2020-01-01", fimContrato: "2025-12-31", multaRescisoria: 200000000, clausulas: "Capitão do time" },
  { nomeJogador: "Calleri", posicao: "Atacante", valorMercado: 60000000, nomeAgente: "Carlos Leite", nomeTime: "São Paulo", inicioContrato: "2021-06-01", fimContrato: "2026-06-01", multaRescisoria: 250000000, clausulas: "Tocar no Calleri é gol" },
  { nomeJogador: "Hulk", posicao: "Atacante", valorMercado: 40000000, nomeAgente: "Eduardo Uram", nomeTime: "Atlético Mineiro", inicioContrato: "2021-02-01", fimContrato: "2025-12-31", multaRescisoria: 100000000, clausulas: "Marketing e patrocínios exclusivos" },
  { nomeJogador: "Jhon Arias", posicao: "Meio-Campo", valorMercado: 75000000, nomeAgente: "Wagner Ribeiro", nomeTime: "Fluminense", inicioContrato: "2022-01-01", fimContrato: "2026-12-31", multaRescisoria: 350000000, clausulas: "Preferência venda exterior" },
  { nomeJogador: "Tiquinho Soares", posicao: "Atacante", valorMercado: 45000000, nomeAgente: "André Cury", nomeTime: "Botafogo", inicioContrato: "2022-08-01", fimContrato: "2025-12-31", multaRescisoria: 120000000, clausulas: "Bônus por gols na Série A" },
  { nomeJogador: "Matheus Pereira", posicao: "Meio-Campo", valorMercado: 85000000, nomeAgente: "Giuliano Bertolucci", nomeTime: "Cruzeiro", inicioContrato: "2023-07-01", fimContrato: "2027-07-01", multaRescisoria: 450000000, clausulas: "Multa em clubes nacionais" },
  { nomeJogador: "Pablo Vegetti", posicao: "Atacante", valorMercado: 35000000, nomeAgente: "Carlos Leite", nomeTime: "Vasco da Gama", inicioContrato: "2023-08-01", fimContrato: "2025-12-31", multaRescisoria: 150000000, clausulas: "Nenhuma" },
  { nomeJogador: "Yuri Alberto", posicao: "Atacante", valorMercado: 60000000, nomeAgente: "Eduardo Uram", nomeTime: "Corinthians", inicioContrato: "2022-01-01", fimContrato: "2027-12-31", multaRescisoria: 300000000, clausulas: "Titularidade protegida" },
  { nomeJogador: "Estêvão", posicao: "Atacante", valorMercado: 150000000, nomeAgente: "Wagner Ribeiro", nomeTime: "Palmeiras", inicioContrato: "2023-01-01", fimContrato: "2028-12-31", multaRescisoria: 600000000, clausulas: "Base e Profissional" }
];

async function post(endpoint, data) {
  try {
    const response = await fetch(`${API_URL}${endpoint}`, {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(data)
    });
    if (!response.ok) throw new Error(`Falha no POST ${endpoint}: ${response.statusText}`);
    console.log(`[SUCESSO] Inserido em ${endpoint}: ${JSON.stringify(data).substring(0, 50)}...`);
  } catch (e) {
    console.error(`[ERRO] ${e.message}`);
  }
}

async function runSeed() {
  console.log("Iniciando injeção de dados da SÉRIE A...");
  
  // Times
  for (const time of times) await post("/times", time);
  
  // Agentes
  for (const agente of agentes) await post("/agentes", agente);
  
  // Jogadores
  for (const jogador of jogadores) await post("/jogadores", jogador);
  
  // Transferência Simulação (Exemplo: Alguem vendendo Estêvão pro Botafogo pagando 50 milhoes)
  await post("/transferencias", {
      nomeJogador: "Estêvão",
      nomeTimeDestino: "Botafogo",
      valor: 80000000.0,
      luvas: 5000000.0,
      multaRescisoria: 0.0,
      comissaoAgente: 8000000.0,
      inicioContrato: "2024-05-01",
      fimContrato: "2029-05-01",
      multaContrato: 1000000000.0,
      clausulasContrato: "Projeto SAF Global"
  });

  console.log("Seed da Série A finalizado com sucesso!");
}

runSeed();
