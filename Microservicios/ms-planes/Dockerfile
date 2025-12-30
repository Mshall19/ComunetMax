# 1. Usamos una imagen base ligera de Java 17
FROM eclipse-temurin:17-jdk-alpine

# 2. Definimos una variable que apunta a donde Maven deja el ejecutable
ARG JAR_FILE=target/*.jar

# 3. Copiamos ese ejecutable dentro del contenedor con el nombre "app.jar"
COPY ${JAR_FILE} app.jar

# 4. Comando para iniciar la aplicación dentro del contenedor
ENTRYPOINT ["java", "-jar", "/app.jar"]