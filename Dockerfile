# Use uma imagem base do OpenJDK para Java 17
FROM openjdk:17-jdk-slim AS build

WORKDIR /app

# Copie o arquivo JAR do seu aplicativo para o contêiner
COPY target/ssh-0.0.1-SNAPSHOT.jar /app.jar

# Porta recomendada para o aplicativo Spring Boot
EXPOSE 8080

# Comando para iniciar o aplicativo quando o contêiner for iniciado
CMD ["java", "-jar", "/app.jar"]
