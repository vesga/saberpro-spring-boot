# BUILD STAGE (Compilación con Maven y Java 17)
# Usamos una imagen de Eclipse Temurin que ya incluye el JDK 17
FROM eclipse-temurin:17-jdk-focal as build
WORKDIR /app

# Paso CRÍTICO: Copiar el script de Maven y el POM antes de ejecutar cualquier mvnw
COPY .mvn .mvn
COPY mvnw pom.xml ./

# Ejecutar el go-offline para descargar dependencias
RUN ./mvnw dependency:go-offline

# Compilar la aplicación
COPY src ./src
RUN ./mvnw clean install -DskipTests

# RUN STAGE (Ejecución con JRE 17, más ligero)
# Esto ejecuta el JAR compilado
FROM eclipse-temurin:17-jre-focal
WORKDIR /app
# Copiar el JAR compilado de la etapa 'build'
COPY --from=build /app/target/*.jar app.jar
# Comando de inicio
ENTRYPOINT ["java", "-jar", "app.jar"]