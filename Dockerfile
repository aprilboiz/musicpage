FROM openjdk:21
LABEL authors="Tuan Anh Phan"
EXPOSE 8080
RUN microdnf install findutils wget unzip

# Install Gradle
RUN wget -q https://services.gradle.org/distributions/gradle-8.8-bin.zip \
    && unzip gradle-8.8-bin.zip -d /opt \
    && rm gradle-8.8-bin.zip

ENV GRADLE_HOME /opt/gradle-8.8
ENV PATH $PATH:/opt/gradle-8.8/bin


WORKDIR /app
COPY src /app/src
COPY build.gradle /app
COPY gradlew /app
COPY settings.gradle /app


# RUN gradle --no-daemon build
CMD [ "gradle", "--no-daemon", "bootRun" ]