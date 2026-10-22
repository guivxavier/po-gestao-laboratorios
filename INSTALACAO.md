# Guia de Instalação e Execução - Windows

## Requisitos

- **Java JDK 11 ou superior**
- **IDE recomendada:** IntelliJ IDEA, Eclipse ou VS Code com extensão Java

## Verificar Instalação do Java

Abra o Prompt de Comando (cmd) e execute:

```cmd
java -version
javac -version
```

Ambos os comandos devem retornar a versão do Java (mínimo 11).

## Método 1: Usando IDE (Recomendado)

### IntelliJ IDEA

1. Abra o IntelliJ IDEA
2. **File** → **Open** → Selecione a pasta do projeto
3. Aguarde a indexação
4. Clique com botão direito em **Main.java**
5. Selecione **Run 'Main.main()'**

### Eclipse

1. Abra o Eclipse
2. **File** → **Open Projects from File System**
3. Selecione a pasta do projeto
4. Clique com botão direito em **Main.java**
5. **Run As** → **Java Application**

### VS Code

1. Instale a extensão **Extension Pack for Java**
2. Abra a pasta do projeto
3. Clique com botão direito em **Main.java**
4. Selecione **Run Java**

## Método 2: Linha de Comando (Manual)

### Compilar

Abra o Prompt de Comando na pasta do projeto e execute:

```cmd
javac -encoding UTF-8 -d bin backend/modelo/*.java backend/sistema/excecoes/*.java backend/sistema/*.java frontend/*.java Main.java
```

### Executar

```cmd
java -cp bin Main
```

## Primeira Execução

Na primeira vez que executar o programa:

1. O sistema será iniciado vazio
2. Dados de exemplo serão automaticamente carregados
3. Serão criados:
   - 3 Laboratórios
   - 6 Investigadores (2 de cada tipo)
   - 2 Coordenadores
   - 1 Administrador
   - 3 Projetos
   - 4 Atividades de exemplo

## Credenciais de Teste

Pode fazer login com:

- **Administrador:** 
  - Email: admin@lab.pt
  - Password: admin123

- **Coordenador:**
  - Email: joao.silva@lab.pt
  - Password: coord123

- **Investigador:**
  - Email: ana.costa@lab.pt
  - Password: inv123

## Persistência de Dados

- Os dados são guardados automaticamente no ficheiro `sistema_laboratorios.dat`
- Na próxima execução, o sistema carrega os dados salvos
- Pode optar por guardar manualmente ao sair

## Resolução de Problemas

### "javac não é reconhecido como comando"

1. Certifique-se que o Java JDK está instalado (não apenas JRE)
2. Adicione o Java ao PATH:
   - **Painel de Controlo** → **Sistema** → **Configurações avançadas do sistema**
   - **Variáveis de Ambiente** → Editar **Path**
   - Adicionar: `C:\Program Files\Java\jdk-XX\bin` (substituir XX pela versão)

### "Erro de codificação de caracteres"

No Prompt de Comando, execute antes de compilar:

```cmd
chcp 65001
```

### Erros de compilação

- Verifique se todos os ficheiros estão presentes
- Certifique-se que está no diretório raiz do projeto
- Use a opção `-encoding UTF-8` ao compilar

## Estrutura de Diretórios

```
projeto/
├── backend/
│   ├── modelo/              # Classes de domínio
│   └── sistema/             # Lógica de negócio
│       └── excecoes/        # Exceções personalizadas
├── frontend/
│   └── InterfaceTexto.java  # Interface de utilizador
├── Main.java                # Ponto de entrada
├── bin/                     # Classes compiladas (criado automaticamente)
└── sistema_laboratorios.dat # Dados persistidos (criado na 1ª execução)
```

## Suporte

Para dúvidas ou problemas, contacte os autores do projeto.
