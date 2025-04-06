# Projeto Edital SEPLAG: PROJETO PRÁTICO IMPLEMENTAÇÃO BACK-END

Este projeto demonstra uma API RESTful construída com Java e Spring Boot, utilizando Docker e Docker Compose para orquestrar os serviços necessários, incluindo PostgreSQL, MinIO e Traefik como proxy reverso, 
atendendo aos requisitos para Desenvolvedor Java Backend.

**Informações do Canditado:**
*   Nome: Marcos Luciano Peres
*   CPF: 468.660.439-72
*   email: marcos@linhalivre.com.br
*   fone: (41) 99962-1632

**Funcionalidades Principais:**

*   Upload de uma ou mais imagens (fotos) para um bucket MinIO.
*   Associação de metadados da imagem (ID da pessoa, data, bucket, nome do objeto) em um banco de dados PostgreSQL.
*   Recuperação de imagens através de URLs pré-assinadas (temporárias) geradas pelo MinIO com tempo de expiração configurável.
*   Endpoints CRUD (Criar, Ler, Atualizar, Deletar) para diversas entidades (Cidade, Endereço, Pessoa, Unidade, etc.).
*   Consultas complexas:
    *   Listar servidores efetivos lotados em uma unidade específica.
    *   Buscar endereço da unidade de lotação pelo nome parcial do servidor efetivo.
*   Paginação implementada em todas as listagens de entidades e consultas complexas.
*   Autenticação simples baseada em Token (UUID) com tempo de expiração configurável e endpoint de renovação.
*   Roteamento e acesso via hostnames definidos gerenciados pelo Traefik.
*   Documentação da API disponível via Swagger UI (OpenAPI 3). através do link http://java17.marcosperes.com.br/swagger-ui/index.html

## Tecnologias Utilizadas

*   **Backend:** Java 17, Spring Boot 3.x, Spring Web, Spring Data JPA, Hibernate
*   **Banco de Dados:** PostgreSQL 13
*   **Armazenamento de Objetos:** MinIO (Compatível com S3)
*   **Proxy Reverso/Roteamento:** Traefik v2.9
*   **Containerização:** Docker, Docker Compose
*   **Build:** Maven (ou Gradle, dependendo da sua configuração)
*   **Documentação API:** SpringDoc OpenAPI (Swagger UI)
*   **Outros:** Lombok (opcional), SLF4J/Logback

## Pré-requisitos

*   [Docker](https://docs.docker.com/get-docker/)
*   [Docker Compose](https://docs.docker.com/compose/install/) (geralmente incluído na instalação do Docker Desktop)
*   Git (para clonar o repositório)
*   Um editor de texto ou IDE (ex: VS Code, IntelliJ IDEA)
*   Acesso à internet (para baixar imagens Docker)
*   Permissão para editar o arquivo `hosts` do seu sistema operacional (para facilitar o acesso pelos hostnames definidos) OU uma ferramenta como Postman para configurar o header `Host`.

## Configuração do Ambiente Local

1.  **Clonar o Repositório:**
    ```bash
    git clone https://github.com/marcosperescwb/seplag.git
    cd \SEPLAG
    ```

2.  **Configurar Resolução de Hostnames (Recomendado):**
    Para acessar os serviços pelos nomes definidos (ex: `java17.marcosperes.com.br`), você precisa que sua máquina local resolva esses nomes para o endereço IP da máquina onde o Docker está rodando (geralmente `127.0.0.1` se estiver rodando localmente, ou o IP da sua VM/servidor Docker).

    *   Edite o arquivo `hosts` do seu sistema **com privilégios de administrador/root**:
        *   **Windows:** `C:\Windows\System32\drivers\etc\hosts`
        *   **Linux/macOS:** `/etc/hosts`
    *   Adicione a seguinte linha (ajuste o IP se o Docker não estiver rodando em `127.0.0.1`):
        ```
        127.0.0.1 java17.marcosperes.com.br s3.marcosperes.com.br console.marcosperes.com.br pgadmin.marcosperes.com.br
        ```
    *   Salve o arquivo. Pode ser necessário limpar o cache DNS do seu sistema (`ipconfig /flushdns` no Windows, comandos específicos no macOS/Linux).
    *   **Alternativa (Sem editar hosts):** Ao usar ferramentas como Postman ou `curl`, envie as requisições para o IP do host Docker (ex: `http://192.168.0.10/...`) e adicione manualmente o cabeçalho `Host` apropriado (ex: `Host: java17.marcosperes.com.br`).

3.  **(Opcional) Variáveis de Ambiente:** Se o projeto usar um arquivo `.env` para configurar credenciais ou outros parâmetros no `docker-compose.yml`, certifique-se de criá-lo ou configurá-lo adequadamente.

## Executando o Projeto com Docker Compose

1.  **Navegue até o diretório raiz** do projeto (onde o arquivo `docker-compose.yml` está localizado).
2.  **Construa as imagens e inicie os containers:**
    ```bash
    # A flag --build garante que a imagem da aplicação Java será (re)construída
    # A flag -d executa os containers em background (detached mode)
    docker-compose up --build -d
    ```
3.  **Aguarde** alguns instantes para que todos os containers iniciem completamente (especialmente a aplicação Java e o banco de dados).
4.  **Verifique o status:**
    ```bash
    docker-compose ps
    ```
    Todos os serviços devem estar com o status `Up` ou `Running`.
5.  **Verifique os logs** (se necessário):
    ```bash
    # Ver logs de um serviço específico (ex: aplicação Java)
    docker-compose logs -f java_app_v17

    # Ver logs do Traefik
    docker-compose logs -f traefik

    # Ver logs do MinIO
    docker-compose logs -f minio

    # Ver logs do Postgres
    docker-compose logs -f postgres
    ```
    Pressione `Ctrl+C` para sair do modo de acompanhamento de logs (`-f`).

## Acessando os Serviços

Após iniciar os containers e configurar o arquivo `hosts` (ou usando o IP do host Docker):

*   **API Java Principal:** `http://java17.marcosperes.com.br/` (roteado pelo Traefik para a porta 8083 do container `java_app_v17`)
*   **Documentação Swagger UI:** `http://java17.marcosperes.com.br/swagger-ui/index.html`
*   **Definição OpenAPI:** `http://java17.marcosperes.com.br/v3/api-docs`
*   **MinIO S3 API:** `http://s3.marcosperes.com.br/` (roteado pelo Traefik para a porta 9000 do container `minio`)
*   **MinIO Console:** `http://console.marcosperes.com.br/` (roteado pelo Traefik para a porta 9001 do container `minio`)
    *   **Login:** `marcos.luciano.peres`
    *   **Senha:** `mlp020572` (Definidos no `docker-compose.yml`)
*   **PgAdmin (Interface Web do Postgres):** `http://pgadmin.marcosperes.com.br/` (roteado pelo Traefik para a porta 80 do container `pgadmin`)
    *   **Email Login:** `admin@example.com`
    *   **Senha:** `admin` (Definidos no `docker-compose.yml`)
    *   Ao acessar pela primeira vez, adicione um novo servidor para conectar ao container Postgres:
        *   **Host name/address:** `postgres` (nome do serviço docker)
        *   **Port:** `5432`
        *   **Maintenance database:** `postgres`
        *   **Username:** `postgres`
        *   **Password:** `postgres`
*   **Traefik Dashboard:** `http://<IP_DO_HOST_DOCKER>:8080/dashboard/` (Acesso direto à porta 8080 exposta pelo Traefik para a API/Dashboard)

## Documentação da API (Swagger UI)

Acesse `http://java17.marcosperes.com.br/swagger-ui/index.html` para visualizar e interagir com os endpoints da API.

## Endpoints da API

Todos os endpoints sob `/api/v1/` (exceto os de autenticação) requerem um token válido no cabeçalho `Authorization`.

**Autenticação (`/api/v1/auth`)**

*   `POST /login`: Gera um novo token de autenticação (sem necessidade de credenciais nesta implementação de exemplo).
    *   **Resposta (200 OK):** `{ "token": "uuid-gerado" }`
*   `POST /token/renew`: Renova a validade de um token existente.
    *   **Header Obrigatório:** `Authorization: <token_atual_valido>`
    *   **Resposta (200 OK):** `{ "token": "mesmo-uuid", "message": "Token renovado..." }`
    *   **Resposta (401 Unauthorized):** Se o token no header for inválido ou expirado.

**Upload de Arquivos (`/api/v1/files`)**

*   `POST /upload`: Envia uma ou mais fotos para o MinIO e registra metadados no banco.
    *   **Tipo de Corpo:** `multipart/form-data`
    *   **Parâmetros (form-data):**
        *   `files`: (Tipo: File) Um ou mais arquivos de imagem.
        *   `pes_id`: (Tipo: Text) O ID numérico da Pessoa associada às fotos.
    *   **Header Obrigatório:** `Authorization: <token_valido>`
    *   **Resposta (201 Created / 207 Multi-Status):** Lista de resultados para cada arquivo:
        ```json
        [
          {
            "originalFilename": "foto1.jpg",
            "pesId": "123",
            "objectName": "uuid-gerado-1.jpg",
            "temporaryUrl": "http://s3.marcosperes.com.br/bucket01/uuid-gerado-1.jpg?...",
            "status": "success"
          },
          {
            "originalFilename": "doc.txt",
            "pesId": "123",
            "status": "failed",
            "error": "Falha no upload/DB: Excecao..."
          }
        ]
        ```

**Operações CRUD Paginadas (Exemplo: `/api/v1/cidades`)**

*   `GET /api/v1/{entidade}`: Lista recursos da entidade de forma paginada.
    *   **Entidades Cobertas:** `cidades`, `enderecos`, `pessoas`, `foto-pessoas`, `unidades`, `pessoa-enderecos`, `servidores-efetivos`, `servidores-temporarios`. (Verifique os paths exatos nos controllers).
    *   **Header Obrigatório:** `Authorization: <token_valido>`
    *   **Parâmetros Query Opcionais:**
        *   `page`: Número da página (base 0, padrão geralmente 0).
        *   `size`: Tamanho da página (padrão geralmente 10 ou 20).
        *   `sort`: Campo para ordenação, opcionalmente com direção (`campo,asc` ou `campo,desc`). O campo deve existir na entidade ou ser um caminho válido (ex: `pessoa.nome`).
        *   *Filtros específicos:* (ex: `GET /api/v1/cidades?nome=Belo%20Horizonte&estado=MG`)
    *   **Resposta (200 OK):** Objeto `Page` em JSON contendo `content` (a lista de itens da página) e metadados de paginação (`totalPages`, `totalElements`, etc.).

**Operações CRUD Específicas (Exemplo: `/api/v1/cidades/{id}`)**

*   `GET /api/v1/{entidade}/{id}`: Busca um recurso pelo ID.
*   `POST /api/v1/{entidade}`: Cria um novo recurso. (Espera JSON no corpo).
*   `PUT /api/v1/{entidade}/{id}`: Atualiza um recurso existente. (Espera JSON no corpo).
*   `DELETE /api/v1/{entidade}/{id}`: Deleta um recurso.
    *   **Header Obrigatório:** `Authorization: <token_valido>` para todos.

**Consultas Complexas**

*   `GET /api/v1/unidades/{unidadeId}/servidores-efetivos`: Lista servidores efetivos de uma unidade (paginado).
    *   **Header Obrigatório:** `Authorization: <token_valido>`
    *   **Parâmetros Path:** `{unidadeId}`
    *   **Parâmetros Query:** `page`, `size`, `sort` (baseado em `pessoa.nome`, etc.)
    *   **Resposta (200 OK):** `Page<ServidorLotacaoDTO>`
*   `GET /api/v1/enderecos/por-nome-servidor`: Busca endereços de unidade por nome parcial de servidor (paginado).
    *   **Header Obrigatório:** `Authorization: <token_valido>`
    *   **Parâmetros Query Obrigatório:** `nomeServidor`
    *   **Parâmetros Query Opcionais:** `page`, `size`, `sort` (baseado nos campos de `Endereco`)
    *   **Resposta (200 OK):** `Page<Endereco>`

## Configuração

*   **`docker-compose.yml`**: Define os serviços, redes, volumes, portas e variáveis de ambiente (como credenciais de banco e MinIO).
*   **`src/main/resources/application.properties`**: Contém configurações do Spring Boot, incluindo:
    *   Conexão com banco de dados (para ambiente Docker).
    *   Conexão com MinIO (`minio.url`, `minio.public.url`, credenciais).
    *   Tempo de expiração do token de autenticação (`authentication.token.expiration-time`).
    *   Origens permitidas para CORS (`cors.allowed.origins`).
    *   Porta da aplicação (`server.port`).
    *   Configurações de Log e JPA/Hibernate.

## Parando os Serviços

Para parar os containers:

```bash
docker-compose down