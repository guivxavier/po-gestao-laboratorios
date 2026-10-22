# Sistema de Gestão de Laboratórios Científicos

Projeto da unidade curricular de **Programação Orientada a Objetos** (2.º ano, 2025/2026), feito em Java.

Aplicação em modo texto para gerir uma rede de laboratórios científicos: investigadores de
várias áreas (Biotecnologia, Robótica e Energia), coordenadores, projetos de investigação e
as atividades registadas em cada projeto. Os dados são guardados em ficheiro entre execuções.

## Funcionalidades

Existem três tipos de utilizador, cada um com o seu menu:

**Investigador**
- ver e editar os dados pessoais (nome, email, password)
- ver os projetos em que participa e as suas atividades
- ver estatísticas pessoais (nº de projetos, atividades e horas)

**Coordenador**
- criar e remover os seus projetos
- adicionar e remover investigadores dos projetos
- registar atividades (experiência, teste, simulação, publicação)
- alterar o estado do projeto (em curso, planeamento, suspenso, concluído)

**Administrador**
- criar, listar e remover investigadores, coordenadores e laboratórios
- ver todos os projetos, todas as atividades e as estatísticas globais

Algumas regras que o sistema valida:
- o email tem de ser único entre todos os utilizadores
- um coordenador só pode alterar os seus próprios projetos
- só se registam atividades de investigadores que pertencem ao projeto, e apenas em
  projetos em curso ou em planeamento
- não se pode remover um laboratório com investigadores nem um coordenador com projetos

## Conceitos de POO usados

- **Herança e classes abstratas**: `Utilizador` → `Investigador` → `InvestigadorBiotecnologia`,
  `InvestigadorRobotica`, `InvestigadorEnergia`; `Coordenador` e `Administrador` também
  estendem `Utilizador`
- **Polimorfismo**: `getTipo()` e `getDetalhesEspecificos()` são implementados por cada subclasse;
  o login devolve um `Utilizador` e o menu é escolhido pelo tipo real do objeto
- **Encapsulamento**: atributos privados e cópias defensivas das listas
- **Exceções próprias**: `AutenticacaoException` e `EntidadeException`
- **Enums**: estado do projeto (`Projeto.EstadoProjeto`)
- **Serialização**: o sistema inteiro é guardado em `sistema_laboratorios.dat`

O relatório completo está em [RELATORIO.md](RELATORIO.md).

## Estrutura

```
projetopo/
├── Main.java                      # ponto de entrada
├── backend/
│   ├── modelo/                    # entidades (Utilizador, Investigador, Projeto, ...)
│   └── sistema/
│       ├── SistemaLaboratorios.java   # lógica e regras do sistema
│       ├── DadosIniciais.java         # dados de exemplo
│       └── excecoes/
└── frontend/
    └── InterfaceTexto.java        # menus em modo texto
```

O `frontend` só lê o input e mostra resultados; as regras estão todas no `backend`.

## Como executar

É preciso o JDK 11 ou superior.

```bash
cd projetopo
javac -encoding UTF-8 -d bin Main.java backend/modelo/*.java backend/sistema/*.java backend/sistema/excecoes/*.java frontend/*.java
java -cp bin Main
```

No Windows, se os acentos aparecerem mal na consola, correr `chcp 65001` antes.

Também se pode abrir a pasta `projetopo` no IntelliJ, Eclipse ou VS Code e correr o `Main.java`.

## Contas de teste

Na primeira execução (sem ficheiro de dados) o sistema é preenchido com 3 laboratórios,
6 investigadores, 2 coordenadores, 3 projetos e algumas atividades.

| Tipo          | Email                  | Password   |
|---------------|------------------------|------------|
| Administrador | admin@lab.pt           | admin123   |
| Coordenador   | manuel.vieira@lab.pt   | coord123   |
| Coordenador   | ana.pereira@lab.pt     | coord456   |
| Investigador  | gui.xavier@lab.pt      | inv123     |

Os dados são guardados ao sair pelo menu inicial (opção 0). Para voltar aos dados de exemplo
basta apagar o ficheiro `sistema_laboratorios.dat`.

## Limitações

- as passwords ficam guardadas em texto simples
- as pesquisas são feitas percorrendo listas (`ArrayList`), o que chega para o volume de dados
  do projeto
