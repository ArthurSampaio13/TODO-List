# TODO-List Application

## Descrição
Este projeto é uma aplicação de linha de comando para gerenciar tarefas, permitindo criar, listar, atualizar e excluir tarefas com várias opções de organização. A aplicação é projetada para ser simples e eficiente, com suporte a persistência de dados.

## Funcionalidades
- **Arquitetura MVC:** Segue o padrão de arquitetura Model-View-Controller, promovendo uma separação clara de responsabilidades no código.
- **Validações:** Todos os campos possuem validações simples utilizando enums, garantindo a consistência dos dados.
- **Padrão Strategy para Ordenação:** A ordenação das tarefas por categoria, prioridade, status e data utiliza o padrão Strategy, permitindo flexibilidade e facilidade para adicionar novos critérios de ordenação no futuro. Este padrão é eficiente porque separa os algoritmos de ordenação, facilitando manutenção e expansão.
- **Sistema de Login:** Um sistema simples de login assegura que cada usuário tenha suas tarefas salvas e acessíveis de forma segura.
- **Interface Simples:** Uma camada de view proporciona uma experiência minimalista para login, cadastro e criação de tarefas.
- **Parâmetros das Tarefas:**
    - Nome
    - Descrição
    - Data de término
    - Nível de prioridade (1 a 5)
    - Categoria
    - Status (ToDo, Doing, Done)
- **Listagem de Tarefas:**
    - Por categoria
    - Por prioridade
    - Por status
    - Por data
- **Atualização e Exclusão de Tarefas:** Permite modificar ou remover qualquer tarefa existente.
- **Estatísticas:** Consulta do número de tarefas em cada status (ToDo, Doing, Done).
- **Persistência de Dados:** Os dados são salvos em um banco de dados SQLite, garantindo que as informações permaneçam armazenadas entre execuções da aplicação.

## Como Usar
1. **Clonando o Repositório:**
   Faça o clone do projeto utilizando o comando:
   ```bash
   git clone https://github.com/ArthurSampaio13/TODO-List.git
   ```
2. **Executando a Aplicação:**
   Navegue até o diretório do projeto e execute a classe `Main` diretamente. Todo o código necessário já está configurado.

## Requisitos
- **Java 17**

## Estrutura de Diretórios
```
TODO-List-dev/
├── src/
│   ├── main/
│   │   ├── java/         # Código fonte Java
│   │   └── resources/    # Arquivos de recursos
├── pom.xml               # Configuração do Maven
├── README.md             # Documentação do projeto
└── .gitignore            # Arquivos ignorados pelo Git
```

