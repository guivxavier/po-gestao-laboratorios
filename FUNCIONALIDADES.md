# Relatório de Funcionalidades Implementadas

## Funcionalidades Implementadas ✅

### 1. Sistema de Autenticação
- ✅ Login com email e password
- ✅ Validação de credenciais
- ✅ Gestão de sessões (logout)
- ✅ Suporte para 3 tipos de utilizadores (Investigador, Coordenador, Administrador)

### 2. Gestão de Investigadores
- ✅ Adicionar investigadores (3 tipos: Biotecnologia, Robótica, Energia)
- ✅ Remover investigadores
- ✅ Listar investigadores
- ✅ Ver/editar dados pessoais
- ✅ Associação a laboratórios
- ✅ Cálculo automático de estatísticas (projetos, atividades, horas)

### 3. Gestão de Coordenadores
- ✅ Adicionar coordenadores
- ✅ Remover coordenadores
- ✅ Listar coordenadores
- ✅ Associação a projetos

### 4. Gestão de Laboratórios
- ✅ Criar laboratórios
- ✅ Remover laboratórios
- ✅ Listar laboratórios
- ✅ Associar investigadores
- ✅ Contagem de projetos ativos

### 5. Gestão de Projetos
- ✅ Criar projetos
- ✅ Adicionar/remover investigadores de projetos
- ✅ Alterar estado do projeto (Em Curso, Concluído, Suspenso)
- ✅ Listar projetos (geral, por coordenador, por investigador)
- ✅ Associação a laboratórios
- ✅ Cálculo de horas totais por projeto

### 6. Gestão de Atividades
- ✅ Registar atividades (experiência, teste, simulação, publicação)
- ✅ Associar atividade a projeto e investigador
- ✅ Registo de data e duração
- ✅ Listar atividades (geral, por projeto, por investigador)
- ✅ Validação de permissões (investigador deve estar no projeto)

### 7. Estatísticas
- ✅ Estatísticas por investigador (projetos, atividades, horas)
- ✅ Estatísticas por projeto (horas totais, número de investigadores)
- ✅ Estatísticas gerais do sistema
- ✅ Ranking de investigadores por horas

### 8. Persistência de Dados
- ✅ Guardar dados em ficheiro (serialização)
- ✅ Carregar dados do ficheiro
- ✅ Carregamento automático ao iniciar
- ✅ Opção de guardar ao sair

### 9. Interface de Utilizador
- ✅ Menu de login
- ✅ Menu específico para Investigador
- ✅ Menu específico para Coordenador
- ✅ Menu específico para Administrador
- ✅ Navegação intuitiva
- ✅ Mensagens de feedback claras
- ✅ Validação de entradas

### 10. Robustez e Validações
- ✅ Tratamento de exceções personalizadas
- ✅ Validação de emails únicos
- ✅ Validação de IDs
- ✅ Prevenção de duplicados
- ✅ Verificação de integridade referencial
- ✅ Confirmações para operações críticas

### 11. Dados Iniciais
- ✅ População automática com dados de exemplo
- ✅ Credenciais de teste para cada tipo de utilizador
- ✅ Relações pré-estabelecidas entre entidades

## Funcionalidades Não Implementadas ❌

Todas as funcionalidades especificadas no enunciado foram implementadas com sucesso.

## Melhorias Adicionais Implementadas 🌟

- ✅ Sistema de ranking de investigadores
- ✅ Estatísticas completas para administrador
- ✅ Validação avançada de permissões
- ✅ Interface com formatação visual melhorada
- ✅ Mensagens de erro detalhadas
- ✅ Confirmações para operações destrutivas

## Observações Técnicas

### Paradigma Orientado a Objetos
- Uso extensivo de **herança** (hierarquia de Utilizador)
- **Polimorfismo** para tratamento uniforme de entidades
- **Encapsulamento** rigoroso com validações
- **Abstração** através de classes abstratas
- **Interfaces** implícitas via comportamentos comuns

### Estruturas de Dados
- **ArrayList** para listas ordenadas
- **Streams** para operações funcionais

### Qualidade do Código
- Comentários JavaDoc em classes principais
- Nomenclatura clara em português (conforme solicitado)
- Separação clara de responsabilidades
- Código modular e reutilizável
- Tratamento consistente de erros

## Conclusão

O projeto atende a todos os requisitos especificados no enunciado, implementando um sistema robusto e completo de gestão de laboratórios científicos, com forte aplicação dos princípios de Programação Orientada a Objetos.
