## Quick Example for a Java Worker (3 minutes)

This example will show you how to build your Java IronWorker, test it locally, then upload it
to IronWorker for production.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker).

First, let's build it on the right architecture using the actual Docker image it will be running on. The
dependencies are passed into javac.

```sh
docker run --rm -v "$PWD":/worker -w /worker iron/java:dev javac -cp "json-java.jar:gson-2.2.4.jar:ironworker-1.0.10.jar" Worker101.java PayloadData.java
```

Now run it to test it out:

```sh
docker run --rm  -e "PAYLOAD_FILE=hello.payload.json" -v "$PWD":/worker -w /worker iron/java java -cp gson-2.2.4.jar:json-java.jar:ironworker-1.0.10.jar:. Worker101
```

Now that we have it working, let's package it up:

```sh
zip -r hello-java.zip .
```

And upload it:

```sh
iron worker upload --name hello-java --zip hello-java.zip iron/java java -cp gson-2.2.4.jar:json-java.jar:ironworker.jar:. Worker101
```

Now queue up a task (or 1 million):

```sh
iron worker queue -payload-file hello.payload.json --wait hello-java
```
