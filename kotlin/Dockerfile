FROM iron/java:1.8

WORKDIR /app
ADD . /app

ENTRYPOINT ["java", "-cp", "gson-2.2.4.jar:json-java.jar:kotlin-runtime-1.0.5.jar:ironworker-1.0.10.jar:.", "Hello"]
