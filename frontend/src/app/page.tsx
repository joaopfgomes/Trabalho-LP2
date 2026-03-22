import Link from 'next/link';

export default function Home() {
  return (
    <div className="flex flex-col items-center justify-center min-h-[70vh] text-center space-y-8">
      <div className="space-y-4 max-w-2xl">
        <h1 className="text-5xl md:text-7xl font-extrabold tracking-tight">
          Gerencie <span className="text-transparent bg-clip-text bg-gradient-to-r from-blue-400 to-emerald-400">Elencos</span> com Maestria
        </h1>
        <p className="text-lg text-slate-400 leading-relaxed">
          Plataforma definitiva para controlar times, contratos de jogadores, agentes e orquestrar transferências milionárias em tempo real.
        </p>
      </div>
      
      <div className="grid grid-cols-2 md:grid-cols-4 gap-4 w-full max-w-4xl mt-12">
        <DashboardCard title="Times" href="/times" icon="🛡️" color="from-red-500/20 to-orange-500/20" />
        <DashboardCard title="Agentes" href="/agentes" icon="👔" color="from-blue-500/20 to-cyan-500/20" />
        <DashboardCard title="Jogadores" href="/jogadores" icon="🏃" color="from-emerald-500/20 to-green-500/20" />
        <DashboardCard title="Transferências" href="/transferencias" icon="💱" color="from-purple-500/20 to-pink-500/20" />
      </div>
    </div>
  );
}

function DashboardCard({ title, href, icon, color }: { title: string, href: string, icon: string, color: string }) {
  return (
    <Link href={href} className={`glass-panel p-6 flex flex-col items-center justify-center gap-3 hover:-translate-y-1 transition-all duration-300 hover:shadow-2xl hover:shadow-cyan-500/10 group cursor-pointer`}>
      <div className={`w-16 h-16 rounded-full bg-gradient-to-br ${color} flex items-center justify-center text-2xl group-hover:scale-110 transition-transform`}>
        {icon}
      </div>
      <h2 className="font-semibold text-slate-200">{title}</h2>
    </Link>
  );
}
