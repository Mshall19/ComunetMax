# ETAPA 1: Construcción (Maven vive aquí dentro temporalmente)
FROM maven:3.8.4-openjdk-17 AS build
WORKDIR /app
# Copiamos tu código fuente al contenedor
COPY . .
# Docker ejecuta la compilación por ti
RUN mvn clean package -DskipTests

# ETAPA 2: Ejecución (Imagen ligera final)
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
# Tomamos el resultado de la ETAPA 1
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]