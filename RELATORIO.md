# Relatório do Projeto - Sistema de Gestão de Laboratórios Científicos

**Disciplina:** Programação Orientada a Objetos  
**Ano Letivo:** 2025/2026

---

## 1. Introdução

Este relatório descreve passo a passo a implementação de um sistema de gestão de laboratórios científicos em Java, explicando os conceitos de Programação Orientada a Objetos (POO) aplicados e as decisões de design tomadas.

O objetivo é que qualquer estudante possa compreender como o código funciona e porque foi estruturado desta forma.

---

## 2. Arquitetura do Sistema

### 2.1 Estrutura de Pacotes

O projeto está organizado em 3 pacotes principais:

```
├── backend/
│   ├── modelo/          # Classes de domínio (entidades)
│   └── sistema/         # Lógica de negócio e exceções
├── frontend/            # Interface do utilizador
└── Main.java           # Ponto de entrada da aplicação
```

**Porquê esta separação?**
- **Backend:** Contém toda a lógica e dados do sistema. Pode funcionar de forma independente.
- **Frontend:** Apenas apresenta informação e recebe inputs. Não tem lógica de negócio.
- Esta separação facilita a manutenção e permite trocar a interface (ex: de texto para gráfica) sem alterar o backend.

---

## 3. Conceitos de POO Aplicados

### 3.1 Herança

**O que é?** Uma classe filha herda atributos e métodos de uma classe pai.

**Onde usamos?**

```
Utilizador (classe pai abstrata)
    ├── Investigador (classe filha abstrata)
    │   ├── InvestigadorBiotecnologia
    │   ├── InvestigadorRobotica
    │   └── InvestigadorEnergia
    ├── Coordenador
    └── Administrador
```

**Porquê?**
- Todos os utilizadores têm ID, nome, email e password → vão para a classe `Utilizador`
- Todos os investigadores têm laboratório, projetos e atividades → vão para a classe `Investigador`
- Cada tipo específico só adiciona o que é único (ex: InvestigadorBiotecnologia tem `areaInvestigacao`)

**Exemplo prático:**
```java
public abstract class Utilizador {
    private int id;
    private String nomeCompleto;
    private String email;
    // ... todos os utilizadores têm isto
}

public abstract class Investigador extends Utilizador {
    private int laboratorioId;
    private List<Integer> projetosIds;
    // ... todos os investigadores têm isto + o que vem de Utilizador
}

public class InvestigadorBiotecnologia extends Investigador {
    private String areaInvestigacao;
    // ... só este tipo tem esta característica específica
}
```

### 3.2 Polimorfismo

**O que é?** Tratar objetos de classes diferentes de forma uniforme através da classe pai.

**Onde usamos?**

No método de autenticação, não sabemos se o utilizador é Investigador, Coordenador ou Administrador:

```java
Utilizador utilizadorAtual = sistema.autenticar(email, password);
// utilizadorAtual pode ser qualquer tipo de utilizador

// Depois verificamos o tipo específico:
if (utilizadorAtual instanceof Investigador) {
    menuInvestigador((Investigador) utilizadorAtual);
} else if (utilizadorAtual instanceof Coordenador) {
    menuCoordenador((Coordenador) utilizadorAtual);
}
```

**Benefício:** Não precisamos de 3 métodos diferentes de login, um único serve para todos.

### 3.3 Encapsulamento

**O que é?** Esconder os detalhes internos de uma classe e só permitir acesso através de métodos públicos.

**Como fizemos?**
- Todos os atributos são `private`
- Acesso através de `getters` e `setters`

```java
public class Investigador extends Utilizador {
    private String areaEspecializacao;  // private = ninguém acede diretamente
    
    // Acesso controlado através de métodos públicos
    public String getAreaEspecializacao() {
        return areaEspecializacao;
    }
    
    public void setAreaEspecializacao(String areaEspecializacao) {
        this.areaEspecializacao = areaEspecializacao;
    }
}
```

**Porquê?** Se precisarmos adicionar validações mais tarde (ex: nome não pode estar vazio), só alteramos o setter.

### 3.4 Classes Abstratas

**O que são?** Classes que não podem ser instanciadas diretamente, servem apenas como base para outras.

**Onde usamos?**
- `Utilizador`: Nunca criamos um "Utilizador genérico", sempre um Investigador, Coordenador ou Administrador específico
- `Investigador`: Nunca criamos um "Investigador genérico", sempre Biotecnologia, Robótica ou Energia

```java
public abstract class Investigador extends Utilizador {
    // ...
    
    // Método abstrato - cada tipo implementa de forma diferente
    public abstract String getDetalhesEspecificos();
}

// Implementação concreta:
public class InvestigadorBiotecnologia extends Investigador {
    @Override
    public String getDetalhesEspecificos() {
        return "Área de Investigação: " + areaInvestigacao;
    }
}
```

---

## 4. Collections Framework

### 4.1 ArrayList como Repositório

**O que é?** Lista dinâmica que cresce automaticamente e guarda objetos numa sequência ordenada.

**Onde usamos?**
```java
private ArrayList<Investigador> investigadores;
private ArrayList<Coordenador> coordenadores;
private ArrayList<Projeto> projetos;
```

**Porquê ArrayList?**
- É a estrutura de dados mais simples e direta
- Fácil de compreender e usar
- Ideal para começar em POO

**Como funciona?**
```java
// Adicionar
investigadores.add(investigador);

// Remover
investigadores.remove(investigador);

// Percorrer
for (Investigador inv : investigadores) {
    System.out.println(inv.getNomeCompleto());
}

// Tamanho
int total = investigadores.size();
```

### 4.2 Procurar Elementos por ID

Como ArrayList guarda elementos numa sequência, criamos métodos auxiliares para procurar por ID:

```java
private Investigador encontrarInvestigadorPorId(int id) {
    for (Investigador inv : investigadores) {
        if (inv.getId() == id) {
            return inv;
        }
    }
    return null;  // Não encontrado
}
```

**Vantagem:** Código simples e fácil de entender
**Desvantagem:** Mais lento para listas grandes (percorre toda a lista)

**Nota:** Para projetos com muitos dados, outras estruturas poderiam ser mais eficientes, mas para aprendizagem ArrayList é o mais adequado.

### 4.3 ArrayList vs Array Normal

**Array normal:**
```java
Investigador[] investigadores = new Investigador[10];  // Tamanho fixo
investigadores[0] = new Investigador(...);
```

**ArrayList:**
```java
ArrayList<Investigador> investigadores = new ArrayList<>();  // Tamanho dinâmico
investigadores.add(new Investigador(...));  // Cresce automaticamente
```

**Porquê ArrayList?** Não precisamos saber quantos investigadores vamos ter.

### 4.4 ArrayList para Relações

Cada investigador tem uma lista de projetos:
```java
private List<Integer> projetosIds;
```

Guardamos apenas os IDs (não os objetos completos) para evitar duplicação e problemas de serialização.

---

## 5. Tratamento de Exceções

### 5.1 Exceções Personalizadas

Criamos 2 tipos de exceções:

```java
public class AutenticacaoException extends Exception {
    // Para erros de login
}

public class EntidadeException extends Exception {
    // Para erros de operações (ex: investigador não encontrado)
}
```

**Porquê?** Distinguir tipos de erros facilita o tratamento específico.

### 5.2 Como Usar

```java
public Utilizador autenticar(String email, String password) 
        throws AutenticacaoException {
    // ... procurar utilizador ...
    
    if (utilizadorNaoEncontrado) {
        throw new AutenticacaoException("Email ou password incorretos");
    }
    
    return utilizador;
}
```

**No código que chama:**
```java
try {
    utilizadorAtual = sistema.autenticar(email, password);
    System.out.println("Login com sucesso!");
} catch (AutenticacaoException e) {
    System.out.println("Erro: " + e.getMessage());
}
```

---

## 6. Persistência de Dados

### 6.1 Serialização

**O que é?** Converter objetos Java em bytes para guardar em ficheiro.

**Como fizemos?**

1. Todas as classes implementam `Serializable`:
```java
public class SistemaLaboratorios implements Serializable {
    private static final long serialVersionUID = 1L;
    // ...
}
```

2. Guardar:
```java
public void guardarDados(String ficheiro) throws IOException {
    try (ObjectOutputStream oos = new ObjectOutputStream(
            new FileOutputStream(ficheiro))) {
        oos.writeObject(this);
    }
}
```

3. Carregar:
```java
public static SistemaLaboratorios carregarDados(String ficheiro) 
        throws IOException, ClassNotFoundException {
    try (ObjectInputStream ois = new ObjectInputStream(
            new FileInputStream(ficheiro))) {
        return (SistemaLaboratorios) ois.readObject();
    }
}
```

**Benefício:** Todos os dados (investigadores, projetos, atividades) são guardados automaticamente numa única operação.

---

## 7. Padrões de Design

### 7.1 Singleton Implícito

Embora não seja um Singleton puro, só existe uma instância de `SistemaLaboratorios`:

```java
// No Main.java
SistemaLaboratorios sistema;
try {
    sistema = SistemaLaboratorios.carregarDados("sistema_laboratorios.dat");
} catch (Exception e) {
    sistema = new SistemaLaboratorios();
    // Carregar dados iniciais
}
```

Esta única instância é passada para a interface e usada em todo o programa.

### 7.2 Separação de Responsabilidades

- **Modelo** (`backend.modelo`): Representa os dados (Investigador, Projeto, etc.)
- **Sistema** (`backend.sistema`): Gere a lógica e regras de negócio
- **Interface** (`frontend`): Apenas apresenta e recolhe inputs

---

## 8. Fluxo de Funcionamento

### 8.1 Inicialização

1. `Main.java` executa
2. Tenta carregar dados do ficheiro `sistema_laboratorios.dat`
3. Se não existir, cria sistema novo e carrega dados iniciais
4. Lança a interface de texto

### 8.2 Autenticação

1. Utilizador insere email e password
2. Sistema procura nas 3 listas (investigadores, coordenadores, administradores)
3. Compara passwords
4. Se correto, guarda o utilizador autenticado
5. Mostra menu apropriado ao tipo de utilizador

### 8.3 Operação Típica (Criar Projeto)

1. Coordenador escolhe "Criar projeto" no menu
2. Interface pede título e área científica
3. Interface chama `sistema.criarProjeto()`
4. Sistema:
   - Valida se coordenador existe
   - Cria objeto Projeto
   - Adiciona ao ArrayList de projetos
   - Adiciona ID ao coordenador
   - Retorna o projeto criado
5. Interface mostra confirmação

### 8.4 Guardar e Sair

1. Utilizador escolhe "Sair"
2. Sistema serializa tudo para `sistema_laboratorios.dat`
3. Programa termina

---

## 9. Validações Importantes

### 9.1 Email Único

```java
if (encontrarInvestigadorPorEmail(email) != null || 
    encontrarCoordenadorPorEmail(email) != null ||
    encontrarAdministradorPorEmail(email) != null) {
    throw new EntidadeException("Email já está em uso");
}
```

### 9.2 Investigador no Projeto

```java
if (!projeto.getInvestigadoresIds().contains(investigadorId)) {
    throw new EntidadeException("Investigador não pertence ao projeto");
}
```

### 9.3 Remoção Segura

```java
// Não pode remover coordenador com projetos ativos
if (!coord.getProjetosIds().isEmpty()) {
    throw new EntidadeException("Coordenador ainda tem projetos atribuídos");
}
```

---

## 10. Estatísticas

### 10.1 Cálculo de Horas (versão simples)

```java
public double calcularTotalHorasInvestigador(int investigadorId) {
    List<Atividade> atividadesInv = listarAtividadesDoInvestigador(investigadorId);
    double total = 0;
    for (Atividade at : atividadesInv) {
        total += at.getDuracaoHoras();
    }
    return total;
}
```

**Explicação:**
1. Obtém todas as atividades do investigador
2. Percorre cada atividade
3. Soma as durações
4. Retorna o total

### 10.2 Contagens

```java
public int contarProjetosAtivos() {
    int total = 0;
    for (Projeto p : projetos) {
        if (p.getEstado() == Projeto.EstadoProjeto.EM_CURSO) {
            total++;
        }
    }
    return total;
}
```

---

## 11. Interface do Utilizador

### 11.1 Menus Dinâmicos

Cada tipo de utilizador vê opções diferentes:

- **Investigador:** Ver dados pessoais, projetos, atividades
- **Coordenador:** Criar projetos, gerir investigadores, registar atividades
- **Administrador:** Gerir tudo (investigadores, coordenadores, laboratórios)

### 11.2 Navegação

```java
public void iniciar() {
    while (true) {
        if (utilizadorAtual == null) {
            menuLogin();
        } else {
            // Mostra menu específico
            if (utilizadorAtual instanceof Investigador) {
                menuInvestigador((Investigador) utilizadorAtual);
            } // ...
        }
    }
}
```

Loop infinito que:
1. Mostra menu de login se não estiver autenticado
2. Mostra menu específico se estiver autenticado
3. Cada opção executa e volta ao menu

---

## 12. Boas Práticas Aplicadas

### 12.1 IDs Automáticos

```java
private static int contadorId = 1;

public Utilizador() {
    this.id = contadorId++;
}
```

Cada entidade recebe um ID único automaticamente.

Como o contador é `static`, não é guardado no ficheiro. Por isso, depois de carregar os dados,
o sistema acerta os contadores com o maior ID existente (`atualizarContador`), para não
voltar a criar IDs repetidos.

### 12.2 Cópias Defensivas

```java
public List<Integer> getProjetosIds() {
    return new ArrayList<>(projetosIds);  // Retorna cópia, não a lista original
}
```

**Porquê?** Se retornássemos a lista original, o código externo poderia modificá-la diretamente, quebrando o encapsulamento.

### 12.3 Try-with-Resources

```java
try (ObjectOutputStream oos = new ObjectOutputStream(
        new FileOutputStream(ficheiro))) {
    oos.writeObject(this);
}
```

**Benefício:** O ficheiro é fechado automaticamente, mesmo se ocorrer erro.

---

## 13. Como Compilar e Executar

### 13.1 Via Linha de Comandos (Windows)

1. Abrir Command Prompt na pasta do projeto

2. Compilar:
```cmd
javac -d bin -encoding UTF-8 backend/modelo/*.java backend/sistema/*.java backend/sistema/excecoes/*.java frontend/*.java Main.java
```

3. Executar:
```cmd
java -cp bin Main
```

### 13.2 Via IDE (IntelliJ/Eclipse)

1. Abrir o projeto na IDE
2. Clicar botão direito em `Main.java`
3. Escolher "Run"

A IDE compila automaticamente.

---

## 14. Dados Iniciais

No primeiro arranque, o sistema cria:

- 1 Administrador (admin@lab.pt / admin123)
- 3 Laboratórios (Biotecnologia, Robótica, Energia)
- 2 Coordenadores (ex: manuel.vieira@lab.pt / coord123)
- 6 Investigadores, 2 de cada tipo (ex: gui.xavier@lab.pt / inv123)
- 3 Projetos de exemplo
- Algumas atividades de exemplo

**Localização:** Classe `DadosIniciais.java`

---

## 15. Extensibilidade

### 15.1 Adicionar Novo Tipo de Investigador

1. Criar classe que estende `Investigador`:
```java
public class InvestigadorQuimica extends Investigador {
    private String tipoComposto;
    
    @Override
    public String getDetalhesEspecificos() {
        return "Tipo de composto: " + tipoComposto;
    }
}
```

2. Adicionar no switch de `adicionarInvestigador()`:
```java
case "quimica":
    investigador = new InvestigadorQuimica(...);
    break;
```

### 15.2 Adicionar Nova Estatística

Basta adicionar método em `SistemaLaboratorios`:
```java
public int contarProjetosConcluidos() {
    int total = 0;
    for (Projeto p : projetos) {
        if (p.getEstado() == Projeto.EstadoProjeto.CONCLUIDO) {
            total++;
        }
    }
    return total;
}
```

---

## 16. Limitações e Melhorias Futuras

### 16.1 Limitações Atuais

- Passwords em texto simples (não encriptadas)
- Sem limite de tentativas de login
- IDs reiniciam se apagar o ficheiro
- Sem logs de auditoria
- Procura linear em ArrayList (mais lenta para muitos dados)

### 16.2 Melhorias Possíveis

- **Segurança:** Hash das passwords (SHA-256, bcrypt)
- **Base de Dados:** Usar MySQL/PostgreSQL em vez de ficheiros
- **Interface Gráfica:** JavaFX ou Swing
- **Relatórios:** Exportar estatísticas para PDF/Excel
- **Validações:** Email válido, nome mínimo, etc.
- **Pesquisa:** Procurar projetos por título, investigadores por nome
- **Performance:** Índices ou estruturas otimizadas para listas muito grandes

---

## 17. Conclusão

Este projeto demonstra os principais conceitos de POO:

- **Herança:** Reutilização de código através de hierarquia de classes
- **Polimorfismo:** Tratamento uniforme de objetos diferentes
- **Encapsulamento:** Proteção de dados com acesso controlado
- **Abstração:** Classes abstratas como contratos

Além disso, aplica:

- **Collections Framework:** ArrayList e listas
- **Exceções:** Tratamento robusto de erros
- **Persistência:** Serialização para ficheiros
- **Separação de Responsabilidades:** Backend vs Frontend

O resultado é um sistema funcional, simples de entender, e que segue as boas práticas de POO para nível de aprendizagem.

---

## 18. Glossário

- **Classe:** Molde para criar objetos
- **Objeto:** Instância de uma classe
- **Herança:** Classe filha herda de classe pai
- **Polimorfismo:** Tratar objetos diferentes de forma uniforme
- **Encapsulamento:** Esconder detalhes internos
- **Abstração:** Representar conceitos essenciais
- **ArrayList:** Lista dinâmica que cresce automaticamente
- **Serialização:** Converter objetos em bytes
- **Exception:** Erro que pode ser tratado
- **Repositório:** Estrutura que guarda e gere entidades

---

**Fim do Relatório**
