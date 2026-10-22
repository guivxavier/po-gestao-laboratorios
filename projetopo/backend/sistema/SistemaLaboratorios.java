package backend.sistema;

import backend.modelo.*;
import backend.sistema.excecoes.*;
import java.io.*;
import java.time.LocalDate;
import java.util.*;

// Sistema central - aqui fica toda a lógica de negócio
public class SistemaLaboratorios implements Serializable {
    private static final long serialVersionUID = 1L;
    
    private ArrayList<Investigador> investigadores;
    private ArrayList<Coordenador> coordenadores;
    private ArrayList<Administrador> administradores;
    private ArrayList<Laboratorio> laboratorios;
    private ArrayList<Projeto> projetos;
    private ArrayList<Atividade> atividades;
    
    public SistemaLaboratorios() {
        this.investigadores = new ArrayList<>();
        this.coordenadores = new ArrayList<>();
        this.administradores = new ArrayList<>();
        this.laboratorios = new ArrayList<>();
        this.projetos = new ArrayList<>();
        this.atividades = new ArrayList<>();
    }
    
    // ========== Métodos auxiliares para procurar por ID ==========
    
    private Investigador encontrarInvestigadorPorId(int id) {
        for (Investigador inv : investigadores) {
            if (inv.getId() == id) {
                return inv;
            }
        }
        return null;
    }
    
    private Investigador encontrarInvestigadorPorEmail(String email) {
        for (Investigador inv : investigadores) {
            if (inv.getEmail().equals(email)) {
                return inv;
            }
        }
        return null;
    }
    
    private Coordenador encontrarCoordenadorPorId(int id) {
        for (Coordenador coord : coordenadores) {
            if (coord.getId() == id) {
                return coord;
            }
        }
        return null;
    }
    
    private Coordenador encontrarCoordenadorPorEmail(String email) {
        for (Coordenador coord : coordenadores) {
            if (coord.getEmail().equals(email)) {
                return coord;
            }
        }
        return null;
    }
    
    private Administrador encontrarAdministradorPorEmail(String email) {
        for (Administrador admin : administradores) {
            if (admin.getEmail().equals(email)) {
                return admin;
            }
        }
        return null;
    }
    
    private Laboratorio encontrarLaboratorioPorId(int id) {
        for (Laboratorio lab : laboratorios) {
            if (lab.getId() == id) {
                return lab;
            }
        }
        return null;
    }
    
    private Projeto encontrarProjetoPorId(int id) {
        for (Projeto proj : projetos) {
            if (proj.getId() == id) {
                return proj;
            }
        }
        return null;
    }
    
    private Atividade encontrarAtividadePorId(int id) {
        for (Atividade at : atividades) {
            if (at.getId() == id) {
                return at;
            }
        }
        return null;
    }
    
    // ========== Login ==========
    
    public Utilizador autenticar(String email, String password) throws AutenticacaoException {
        // Tenta primeiro como investigador
        Investigador inv = encontrarInvestigadorPorEmail(email);
        if (inv != null && inv.getPassword().equals(password)) {
            return inv;
        }
        
        // Depois como coordenador
        Coordenador coord = encontrarCoordenadorPorEmail(email);
        if (coord != null && coord.getPassword().equals(password)) {
            return coord;
        }
        
        // Por último como administrador
        Administrador admin = encontrarAdministradorPorEmail(email);
        if (admin != null && admin.getPassword().equals(password)) {
            return admin;
        }
        
        throw new AutenticacaoException("Email ou password incorretos");
    }
    
    // ========== Gerir Investigadores ==========
    
    public Investigador adicionarInvestigador(String tipo, String nomeCompleto, String email, 
                                             String password, int laboratorioId, 
                                             String detalheEspecifico) throws EntidadeException {
        // Verificar se o email já existe
        if (encontrarInvestigadorPorEmail(email) != null || 
            encontrarCoordenadorPorEmail(email) != null ||
            encontrarAdministradorPorEmail(email) != null) {
            throw new EntidadeException("Email já está em uso");
        }
        
        Laboratorio lab = encontrarLaboratorioPorId(laboratorioId);
        if (lab == null) {
            throw new EntidadeException("Laboratório não encontrado");
        }
        
        Investigador investigador;
        
        // Criar o investigador certo dependendo do tipo
        switch (tipo.toLowerCase()) {
            case "biotecnologia":
                investigador = new InvestigadorBiotecnologia(nomeCompleto, email, password, 
                                                             laboratorioId, detalheEspecifico);
                break;
            case "robotica":
            case "robótica":
                investigador = new InvestigadorRobotica(nomeCompleto, email, password, 
                                                        laboratorioId, detalheEspecifico);
                break;
            case "energia":
                investigador = new InvestigadorEnergia(nomeCompleto, email, password, 
                                                       laboratorioId, detalheEspecifico);
                break;
            default:
                throw new EntidadeException("Tipo de investigador inválido: " + tipo);
        }
        
        investigadores.add(investigador);
        lab.adicionarInvestigador(investigador.getId());
        
        return investigador;
    }
    
    public void removerInvestigador(int investigadorId) throws EntidadeException {
        Investigador inv = encontrarInvestigadorPorId(investigadorId);
        if (inv == null) {
            throw new EntidadeException("Investigador não encontrado");
        }
        
        // Remover de todos os projetos primeiro
        for (int projetoId : inv.getProjetosIds()) {
            Projeto projeto = encontrarProjetoPorId(projetoId);
            if (projeto != null) {
                projeto.removerInvestigador(investigadorId);
            }
        }
        
        // Remover do laboratório
        Laboratorio lab = encontrarLaboratorioPorId(inv.getLaboratorioId());
        if (lab != null) {
            lab.removerInvestigador(investigadorId);
        }
        
        investigadores.remove(inv);
    }
    
    public Investigador obterInvestigador(int id) throws EntidadeException {
        Investigador inv = encontrarInvestigadorPorId(id);
        if (inv == null) {
            throw new EntidadeException("Investigador não encontrado");
        }
        return inv;
    }
    
    public List<Investigador> listarInvestigadores() {
        return new ArrayList<>(investigadores);
    }
    
    public List<Investigador> listarInvestigadoresPorLaboratorio(int laboratorioId) {
        List<Investigador> resultado = new ArrayList<>();
        for (Investigador inv : investigadores) {
            if (inv.getLaboratorioId() == laboratorioId) {
                resultado.add(inv);
            }
        }
        return resultado;
    }
    
    // ========== Gerir Coordenadores ==========
    
    public Coordenador adicionarCoordenador(String nomeCompleto, String email, String password) 
            throws EntidadeException {
        if (encontrarInvestigadorPorEmail(email) != null || 
            encontrarCoordenadorPorEmail(email) != null ||
            encontrarAdministradorPorEmail(email) != null) {
            throw new EntidadeException("Email já está em uso");
        }
        
        Coordenador coordenador = new Coordenador(nomeCompleto, email, password);
        coordenadores.add(coordenador);
        
        return coordenador;
    }
    
    public void removerCoordenador(int coordenadorId) throws EntidadeException {
        Coordenador coord = encontrarCoordenadorPorId(coordenadorId);
        if (coord == null) {
            throw new EntidadeException("Coordenador não encontrado");
        }
        
        // Não pode remover se ainda tem projetos
        if (!coord.getProjetosIds().isEmpty()) {
            throw new EntidadeException("Coordenador ainda tem projetos atribuídos");
        }
        
        coordenadores.remove(coord);
    }
    
    public Coordenador obterCoordenador(int id) throws EntidadeException {
        Coordenador coord = encontrarCoordenadorPorId(id);
        if (coord == null) {
            throw new EntidadeException("Coordenador não encontrado");
        }
        return coord;
    }
    
    public List<Coordenador> listarCoordenadores() {
        return new ArrayList<>(coordenadores);
    }
    
    // ========== Gerir Administradores ==========
    
    public Administrador adicionarAdministrador(String nomeCompleto, String email, String password) 
            throws EntidadeException {
        if (encontrarInvestigadorPorEmail(email) != null || 
            encontrarCoordenadorPorEmail(email) != null ||
            encontrarAdministradorPorEmail(email) != null) {
            throw new EntidadeException("Email já está em uso");
        }
        
        Administrador admin = new Administrador(nomeCompleto, email, password);
        administradores.add(admin);
        
        return admin;
    }
    
    public List<Administrador> listarAdministradores() {
        return new ArrayList<>(administradores);
    }
    
    // ========== Gerir Laboratórios ==========
    
    public Laboratorio adicionarLaboratorio(String nome, String localizacao) throws EntidadeException {
        if (nome == null || nome.trim().isEmpty()) {
            throw new EntidadeException("Nome do laboratório não pode estar vazio");
        }
        
        Laboratorio laboratorio = new Laboratorio(nome, localizacao);
        laboratorios.add(laboratorio);
        
        return laboratorio;
    }
    
    public void removerLaboratorio(int laboratorioId) throws EntidadeException {
        Laboratorio lab = encontrarLaboratorioPorId(laboratorioId);
        if (lab == null) {
            throw new EntidadeException("Laboratório não encontrado");
        }
        
        // Não pode ter investigadores
        if (!lab.getInvestigadoresIds().isEmpty()) {
            throw new EntidadeException("Laboratório ainda tem investigadores");
        }
        
        laboratorios.remove(lab);
    }
    
    public Laboratorio obterLaboratorio(int id) throws EntidadeException {
        Laboratorio lab = encontrarLaboratorioPorId(id);
        if (lab == null) {
            throw new EntidadeException("Laboratório não encontrado");
        }
        return lab;
    }
    
    public List<Laboratorio> listarLaboratorios() {
        return new ArrayList<>(laboratorios);
    }
    
    // ========== Gerir Projetos ==========
    
    public Projeto criarProjeto(String titulo, String areaCientifica, int coordenadorId) 
            throws EntidadeException {
        Coordenador coord = encontrarCoordenadorPorId(coordenadorId);
        if (coord == null) {
            throw new EntidadeException("Coordenador não encontrado");
        }
        
        Projeto projeto = new Projeto(titulo, areaCientifica, coordenadorId);
        projetos.add(projeto);
        coord.adicionarProjeto(projeto.getId());
        
        return projeto;
    }

    // --- NOVO MÉTODO ADICIONADO ---
    public void removerProjeto(int projetoId) throws EntidadeException {
        Projeto projeto = encontrarProjetoPorId(projetoId);
        if (projeto == null) {
            throw new EntidadeException("Projeto não encontrado");
        }
        
        // 1. Remover da lista do coordenador
        Coordenador coord = encontrarCoordenadorPorId(projeto.getCoordenadorId());
        if (coord != null) {
            coord.removerProjeto(projetoId);
        }
        
        // 2. Remover das listas dos investigadores
        for (int invId : projeto.getInvestigadoresIds()) {
            Investigador inv = encontrarInvestigadorPorId(invId);
            if (inv != null) {
                inv.removerProjeto(projetoId);
            }
        }
        
        // 3. Remover das listas dos laboratórios
        for (int labId : projeto.getLaboratoriosIds()) {
            Laboratorio lab = encontrarLaboratorioPorId(labId);
            if (lab != null) {
                lab.removerProjeto(projetoId);
            }
        }
        
        // 4. Remover atividades associadas
        atividades.removeIf(at -> at.getProjetoId() == projetoId);

        // 5. Remover da lista geral de projetos
        projetos.remove(projeto);
    }
    
    public void adicionarInvestigadorAoProjeto(int projetoId, int investigadorId) 
            throws EntidadeException {
        Projeto projeto = encontrarProjetoPorId(projetoId);
        if (projeto == null) {
            throw new EntidadeException("Projeto não encontrado");
        }
        
        Investigador investigador = encontrarInvestigadorPorId(investigadorId);
        if (investigador == null) {
            throw new EntidadeException("Investigador não encontrado");
        }
        
        projeto.adicionarInvestigador(investigadorId);
        investigador.adicionarProjeto(projetoId);
        
        // Adicionar o laboratório do investigador ao projeto
        int labId = investigador.getLaboratorioId();
        if (!projeto.getLaboratoriosIds().contains(labId)) {
            projeto.adicionarLaboratorio(labId);
            Laboratorio lab = encontrarLaboratorioPorId(labId);
            if (lab != null) {
                lab.adicionarProjeto(projetoId);
            }
        }
    }
    
    public void removerInvestigadorDoProjeto(int projetoId, int investigadorId) 
            throws EntidadeException {
        Projeto projeto = encontrarProjetoPorId(projetoId);
        if (projeto == null) {
            throw new EntidadeException("Projeto não encontrado");
        }
        
        Investigador investigador = encontrarInvestigadorPorId(investigadorId);
        if (investigador == null) {
            throw new EntidadeException("Investigador não encontrado");
        }
        
        projeto.removerInvestigador(investigadorId);
        investigador.removerProjeto(projetoId);
    }
    
    public void alterarEstadoProjeto(int projetoId, Projeto.EstadoProjeto novoEstado) 
            throws EntidadeException {
        Projeto projeto = encontrarProjetoPorId(projetoId);
        if (projeto == null) {
            throw new EntidadeException("Projeto não encontrado");
        }
        
        projeto.setEstado(novoEstado);
    }
    
    public Projeto obterProjeto(int id) throws EntidadeException {
        Projeto proj = encontrarProjetoPorId(id);
        if (proj == null) {
            throw new EntidadeException("Projeto não encontrado");
        }
        return proj;
    }
    
    public List<Projeto> listarProjetos() {
        return new ArrayList<>(projetos);
    }
    
    public List<Projeto> listarProjetosDoCoordenador(int coordenadorId) {
        List<Projeto> resultado = new ArrayList<>();
        for (Projeto proj : projetos) {
            if (proj.getCoordenadorId() == coordenadorId) {
                resultado.add(proj);
            }
        }
        return resultado;
    }
    
    public List<Projeto> listarProjetosDoInvestigador(int investigadorId) {
        Investigador inv = encontrarInvestigadorPorId(investigadorId);
        if (inv == null) return new ArrayList<>();
        
        List<Projeto> resultado = new ArrayList<>();
        for (int projetoId : inv.getProjetosIds()) {
            Projeto proj = encontrarProjetoPorId(projetoId);
            if (proj != null) {
                resultado.add(proj);
            }
        }
        return resultado;
    }
    
    // ========== Gerir Atividades ==========
    
    public Atividade registarAtividade(int projetoId, int investigadorId, 
                                      String tipoAtividade, double duracaoHoras) 
            throws EntidadeException {
        Projeto projeto = encontrarProjetoPorId(projetoId);
        if (projeto == null) {
            throw new EntidadeException("Projeto não encontrado");
        }
        
        Investigador investigador = encontrarInvestigadorPorId(investigadorId);
        if (investigador == null) {
            throw new EntidadeException("Investigador não encontrado");
        }
        
        // Verificar se o investigador está no projeto
        if (!projeto.getInvestigadoresIds().contains(investigadorId)) {
            throw new EntidadeException("Investigador não pertence ao projeto");
        }
        
        Atividade atividade = new Atividade(projetoId, investigadorId, tipoAtividade, duracaoHoras);
        atividades.add(atividade);
        investigador.adicionarAtividade(atividade.getId());
        
        return atividade;
    }
    
    public Atividade registarAtividade(int projetoId, int investigadorId, 
                                      String tipoAtividade, LocalDate data, double duracaoHoras) 
            throws EntidadeException {
        Projeto projeto = encontrarProjetoPorId(projetoId);
        if (projeto == null) {
            throw new EntidadeException("Projeto não encontrado");
        }
        
        Investigador investigador = encontrarInvestigadorPorId(investigadorId);
        if (investigador == null) {
            throw new EntidadeException("Investigador não encontrado");
        }
        
        if (!projeto.getInvestigadoresIds().contains(investigadorId)) {
            throw new EntidadeException("Investigador não pertence ao projeto");
        }
        
        Atividade atividade = new Atividade(projetoId, investigadorId, tipoAtividade, data, duracaoHoras);
        atividades.add(atividade);
        investigador.adicionarAtividade(atividade.getId());
        
        return atividade;
    }
    
    public List<Atividade> listarAtividades() {
        return new ArrayList<>(atividades);
    }
    
    public List<Atividade> listarAtividadesDoProjeto(int projetoId) {
        List<Atividade> resultado = new ArrayList<>();
        for (Atividade at : atividades) {
            if (at.getProjetoId() == projetoId) {
                resultado.add(at);
            }
        }
        return resultado;
    }
    
    public List<Atividade> listarAtividadesDoInvestigador(int investigadorId) {
        Investigador inv = encontrarInvestigadorPorId(investigadorId);
        if (inv == null) return new ArrayList<>();
        
        List<Atividade> resultado = new ArrayList<>();
        for (int atividadeId : inv.getAtividadesIds()) {
            Atividade at = encontrarAtividadePorId(atividadeId);
            if (at != null) {
                resultado.add(at);
            }
        }
        return resultado;
    }
    
    // ========== Estatísticas ==========
    
    public double calcularTotalHorasInvestigador(int investigadorId) {
        List<Atividade> atividadesInv = listarAtividadesDoInvestigador(investigadorId);
        double total = 0;
        for (Atividade at : atividadesInv) {
            total += at.getDuracaoHoras();
        }
        return total;
    }
    
    public double calcularTotalHorasProjeto(int projetoId) {
        List<Atividade> atividadesProj = listarAtividadesDoProjeto(projetoId);
        double total = 0;
        for (Atividade at : atividadesProj) {
            total += at.getDuracaoHoras();
        }
        return total;
    }
    
    public Map<String, Integer> obterEstatisticasGerais() {
        Map<String, Integer> stats = new HashMap<>();
        stats.put("Total Investigadores", investigadores.size());
        stats.put("Total Coordenadores", coordenadores.size());
        stats.put("Total Laboratórios", laboratorios.size());
        stats.put("Total Projetos", projetos.size());
        
        // Contar projetos ativos
        int projetosAtivos = 0;
        for (Projeto p : projetos) {
            if (p.getEstado() == Projeto.EstadoProjeto.EM_CURSO) {
                projetosAtivos++;
            }
        }
        stats.put("Projetos Ativos", projetosAtivos);
        stats.put("Total Atividades", atividades.size());
        
        return stats;
    }
    
    // ========== Guardar e Carregar ==========
    
    public void guardarDados(String ficheiro) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(ficheiro))) {
            oos.writeObject(this);
        }
    }
    
    public static SistemaLaboratorios carregarDados(String ficheiro) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(ficheiro))) {
            return (SistemaLaboratorios) ois.readObject();
        }
    }
}
