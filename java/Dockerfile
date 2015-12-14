FROM iron/java

WORKDIR /app
ADD . /app

ENTRYPOINT ["java", "-cp", "gson-2.2.4.jar:json-java.jar:ironworker-1.0.10.jar:.", "Hello"]
