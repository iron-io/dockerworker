FROM iron/kotlin

WORKDIR /app
ADD . /app

ENTRYPOINT ["kotlin", "-cp", "gson-2.2.4.jar:json-java.jar:ironworker-1.0.10.jar:.", "Hello"]
