FROM sapmachine:17 as builder
WORKDIR /project
ADD . /project

RUN ./gradlew bootJar

COPY build/libs/*.jar app.jar
CMD ["java", "-jar", "app.jar"]