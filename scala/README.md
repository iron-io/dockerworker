## Quick Example for a Scala Worker (3 minutes)

This example will show you how to build your Java IronWorker, test it locally, then upload it
to IronWorker for production.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker).

First, let's build it on the right architecture using the actual Docker image it will be running on. The
dependencies are passed into scalac.

```sh
docker run --rm -v "$PWD":/worker -w /worker iron/scala scalac -deprecation -cp "json-java.jar:gson-2.2.4.jar:ironworker.jar" Worker101.scala PayloadData.scala
```

Now run it to test it out:

```sh
docker run -e PAYLOAD_FILE=hello.payload.json --rm -v "$PWD":/worker -w /worker iron/scala scala -cp gson-2.2.4.jar:json-java.jar:ironworker.jar:. Worker101 
```

Now that we have it working, let's package it up:

```sh
zip -r hello-scala.zip .
```

And upload it:

```sh
iron worker upload --name hello-scala --zip hello-scala.zip iron/scala:2.11 scala -cp gson-2.2.4.jar:json-java.jar:ironworker.jar:. Worker101
```

Now queue up a task (or 1 million):

```sh
iron worker queue -payload-file hello.payload.json --wait hello-scala
```
