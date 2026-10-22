# Sistema de Gestão de Laboratórios Científicos

Projeto desenvolvido para a disciplina de Programação com Objetos (2025/2026).

## Descrição

Sistema completo para gestão de uma rede de laboratórios científicos que realizam projetos de investigação em diversas áreas (Biotecnologia, Robótica, Energias Renováveis).

## Funcionalidades Implementadas

### Entidades

- ✅ **Investigador** (com 3 especializações: Biotecnologia, Robótica, Energia)
  - Dados pessoais e área de especialização
  - Características específicas por tipo
  - Estatísticas calculadas automaticamente
  
- ✅ **Laboratório**
  - Gestão de investigadores
  - Projetos ativos
  
- ✅ **Coordenador de Projeto**
  - Gestão de múltiplos projetos
  
- ✅ **Administrador**
  - Gestão completa do sistema
  
- ✅ **Projeto**
  - Equipas de investigadores
  - Estados (em curso, concluído, suspenso)
  
- ✅ **Atividade**
  - Registro de atividades por investigador
  - Cálculo automático de horas

### Funcionalidades por Utilizador

#### Investigador
- ✅ Autenticação no sistema
- ✅ Ver/editar dados pessoais
- ✅ Ver projetos em que participa
- ✅ Consultar estatísticas pessoais

#### Coordenador
- ✅ Autenticação
- ✅ Criar e gerir projetos
- ✅ Adicionar/remover investigadores
- ✅ Registar atividades
- ✅ Acompanhar progresso e estado dos projetos

#### Administrador
- ✅ Autenticação
- ✅ Adicionar/remover investigadores e coordenadores
- ✅ Criar e gerir laboratórios
- ✅ Monitorizar estatísticas e progresso

## Requisitos Técnicos

- ✅ **Separação back-end/front-end** em pacotes distintos
- ✅ **Interface em modo texto** com menus interativos
- ✅ **Persistência de dados** via serialização
- ✅ **Tratamento robusto de erros** com exceções personalizadas
- ✅ **Validações de integridade** dos dados

## Estrutura do Projeto

```
backend/
├── modelo/              # Entidades do domínio
│   ├── Utilizador.java
│   ├── Investigador.java
│   ├── InvestigadorBiotecnologia.java
│   ├── InvestigadorRobotica.java
│   ├── InvestigadorEnergia.java
│   ├── Coordenador.java
│   ├── Administrador.java
│   ├── Laboratorio.java
│   ├── Projeto.java
│   └── Atividade.java
└── sistema/            # Lógica de negócio
    ├── SistemaLaboratorios.java
    ├── DadosIniciais.java
    └── excecoes/
        ├── AutenticacaoException.java
        └── EntidadeException.java

frontend/
└── InterfaceTexto.java  # Interface do utilizador

Main.java                # Ponto de entrada
```

## Como Executar

### Compilar

```bash
javac Main.java backend/**/*.java frontend/*.java
```

### Executar

```bash
java Main
```

## Credenciais de Teste

Na primeira execução, o sistema é populado com dados de exemplo:

- **Administrador:** admin@lab.pt / admin123
- **Coordenador:** joao.silva@lab.pt / coord123
- **Investigador:** ana.costa@lab.pt / inv123

## Conceitos de Programação OO Aplicados

- ✅ **Herança:** Utilizador → Investigador → InvestigadorBiotecnologia/Robótica/Energia
- ✅ **Polimorfismo:** Tratamento uniforme de diferentes tipos de investigadores
- ✅ **Encapsulamento:** Campos privados com getters/setters
- ✅ **Abstração:** Classes abstratas (Utilizador, Investigador)
- ✅ **Collections Framework:** ArrayList para gestão de dados
- ✅ **Exceções:** Tratamento robusto com exceções personalizadas
- ✅ **Serialização:** Persistência de dados em ficheiros
- ✅ **Information Hiding:** Separação de responsabilidades
- ✅ **Enums:** Estados de projeto tipados

## Autores

Projeto desenvolvido por [Nomes dos Alunos] - Turno PL [Número]

## Data de Entrega

20 de dezembro de 2025
