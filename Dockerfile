# BUILD STAGE (Compilación con Maven y Java 17)
FROM eclipse-temurin:17-jdk-focal as build
WORKDIR /app

# 1. Copiar el script de Maven y el POM
COPY .mvn .mvn
COPY mvnw pom.xml ./

# 2. Conceder permiso de ejecución al script mvnw (SOLUCIÓN AL ERROR 126)
RUN chmod +x mvnw

# 3. Ejecutar el go-offline para descargar dependencias
RUN ./mvnw dependency:go-offline

# 4. Compilar la aplicación
COPY src ./src
RUN ./mvnw clean install -DskipTests

# RUN STAGE (Ejecución con JRE 17, más ligero)
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]