# Build stage
FROM eclipse-temurin:21-jdk-jammy as builder

WORKDIR /workspace/app

# Copy build files
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts .
COPY settings.gradle.kts .
COPY src src

# Build the application
RUN ./gradlew bootJar --no-daemon

# Extract layers (for optimized Docker image)
RUN java -Djarmode=layertools -jar build/libs/*.jar extract --destination build/extracted

# Runtime stage
FROM eclipse-temurin:21-jre-jammy
WORKDIR /app

# Copy layers from build stage
COPY --from=builder /workspace/app/build/extracted/dependencies/ ./
COPY --from=builder /workspace/app/build/extracted/spring-boot-loader/ ./
COPY --from=builder /workspace/app/build/extracted/snapshot-dependencies/ ./
COPY --from=builder /workspace/app/build/extracted/application/ ./

# Run as non-root user
RUN adduser --system --group spring
USER spring:spring

ENTRYPOINT ["java", "org.springframework.boot.loader.JarLauncher"]

EXPOSE 8080