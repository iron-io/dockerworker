FROM iron/scala

WORKDIR /app
ADD . /app

ENTRYPOINT ["scala", "-cp", "gson-2.2.4.jar:json-java.jar:ironworker.jar:.", "Hello"]
