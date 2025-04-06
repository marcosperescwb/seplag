# Estágio 1: Build da aplicação com Maven (se estiver usando Maven)
# Use uma imagem base com JDK 17 e Maven
FROM maven:3.8.5-openjdk-17 AS build

# Define o diretório de trabalho
WORKDIR /app

# Copia os arquivos de definição do projeto (pom.xml)
COPY pom.xml .

# Baixa as dependências (cache layer)
RUN mvn dependency:go-offline

# Copia o código fonte
COPY src ./src

# Compila a aplicação e empacota em um JAR
# Use -DskipTests para acelerar o build no Docker se os testes rodam em outro lugar
RUN mvn package -DskipTests

# Estágio 2: Criação da imagem final
# Use uma imagem JRE slim para reduzir o tamanho
FROM openjdk:17-jdk-slim

WORKDIR /app

# Copia o JAR do estágio de build
COPY --from=build /app/target/*.jar app.jar

# Expõe a porta definida em application.properties (8083)
EXPOSE 8083

# Comando para executar a aplicação
ENTRYPOINT ["java", "-jar", "/app/app.jar"]

# Nota: Se estiver usando Gradle, ajuste os comandos RUN e COPY --from=build de acordo
# Exemplo Gradle:
# FROM gradle:7.6-jdk17 AS build
# WORKDIR /app
# COPY build.gradle settings.gradle ./
# COPY src ./src
# RUN gradle build --no-daemon -x test
# ...
# COPY --from=build /app/build/libs/*.jar app.jar