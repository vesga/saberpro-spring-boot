# BUILD STAGE (Compilación con Maven y Java 17)
# Usamos una imagen de Eclipse Temurin que ya incluye el JDK 17
FROM eclipse-temurin:17-jdk-focal as build
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
RUN ./mvnw clean install -DskipTests

# RUN STAGE (Ejecución con JRE 17, más ligero)
# Esto ejecuta el JAR compilado
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]