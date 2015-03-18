## Quick Example for a Java Worker (3 minutes)

This example will show you how to build your Java IronWorker, test it locally, then upload it
to IronWorker for production.

First, let's build it on the right architecture using the actual Docker image it will be running on. The
dependencies are passed into javac. 

```sh
docker run --rm -v "$(pwd)":/worker -w /worker iron/images:java-1.8 sh -c 'javac -cp "json-java.jar:gson-1.7.jar" Worker101.java'
```

Now run it to test it out:

```sh
docker run --rm -v "$(pwd)":/worker -w /worker iron/images:java-1.8 sh -c 'java -cp gson-1.7.jar:json-java.jar:. Worker101 -payload hello.payload.json -config hello.config.yml -id 123'
```

Now that we have it working, let's package it up:

```sh
zip -r hello-java.zip .
```

And upload it:

```sh
iron worker upload --stack java-1.8 hello-java.zip java -cp gson-1.7.jar:json-java.jar:. Worker101
```

Now queue up a task (or 1 million):

```sh
iron worker queue -payload-file hello.payload.json --wait hello-java
```
