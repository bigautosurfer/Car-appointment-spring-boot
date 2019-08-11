FROM maven:3.5-jdk-8

COPY ./ ./

RUN mvn package -DskipTests=true

EXPOSE 8080

CMD ["java", "-jar", "./target/appointment-0.0.1-SNAPSHOT.jar"]