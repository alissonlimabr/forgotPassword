# Usa a imagem oficial do OpenJDK para executar a aplicação Spring Boot
FROM openjdk:17-jdk-slim

# Variáveis de ambiente para a aplicação Spring Boot
ENV SPRING_OUTPUT_ANSI_ENABLED=ALWAYS \
    JAVA_OPTS=""

# Adiciona um usuário para executar a aplicação para que não precise rodar como root
RUN useradd -ms /bin/bash alissonlimabr

# Cria um diretório onde a aplicação será colocada
RUN mkdir -p /app

# Muda para o diretório /app
WORKDIR /app

# Copia o arquivo jar para o diretório /app
COPY ./target/*.jar ./app.jar


EXPOSE 80

# Muda para o usuário alissonlimabr
USER alissonlimabr

CMD ["java", "-jar", "./app.jar"]