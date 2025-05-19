# Etapa 1: Construcción del proyecto
FROM gradle:8.5-jdk21 AS builder
WORKDIR /app

# Copiar solo los archivos necesarios para cachear dependencias
COPY PAL/build.gradle PAL/settings.gradle /app/PAL/

# Copiar el resto del proyecto
COPY PAL /app/PAL

WORKDIR /app/PAL

# Compilar sin ejecutar los tests
RUN gradle build -x test

# Etapa 2: Imagen final
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Copiar el JAR generado
COPY --from=builder /app/PAL/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]
