import type { Metadata } from 'next';
import { Inter } from 'next/font/google';
import './globals.css';
import Link from 'next/link';

const inter = Inter({ subsets: ['latin'] });

export const metadata: Metadata = {
  title: 'Sistema de Elencos',
  description: 'Gerenciamento de Clubes e Transferências',
};

export default function RootLayout({
  children,
}: {
  children: React.ReactNode;
}) {
  return (
    <html lang="pt-BR">
      <body className={inter.className}>
        <nav className="fixed w-full z-50 glass-panel !rounded-none !border-t-0 !border-l-0 !border-r-0 border-b border-white/10 px-6 py-4">
          <div className="max-w-7xl mx-auto flex items-center justify-between">
            <Link href="/" className="text-xl font-bold bg-gradient-to-r from-blue-400 to-emerald-400 bg-clip-text text-transparent">
              ⚽ ElencoPro
            </Link>
            <div className="flex gap-6 text-sm font-medium text-slate-300">
              <Link href="/times" className="hover:text-white transition-colors">Times</Link>
              <Link href="/agentes" className="hover:text-white transition-colors">Agentes</Link>
              <Link href="/jogadores" className="hover:text-white transition-colors">Jogadores</Link>
              <Link href="/transferencias" className="hover:text-white transition-colors">Transferências</Link>
            </div>
          </div>
        </nav>
        <main className="max-w-7xl mx-auto px-6 pt-24 pb-12">
          {children}
        </main>
      </body>
    </html>
  );
}
