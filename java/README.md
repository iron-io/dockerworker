# Worker 101

This covers most of the core concepts of using IronWorker including loading third party
dependencies.

1. Be sure you've setup your Iron.io credentials, see main [README.md](https://github.com/iron-io/iron_worker_examples).
1. Compile java file 'javac -cp "json-java.jar:gson-1.7.jar" Worker101.java'
1. Make jar file 'jar cfm worker101.jar manifest.txt Worker101.class'
1. Run `iron_worker upload worker101` to upload the worker code package to IronWorker.
1. Queue up a task:
  1. From command line: `iron_worker queue JavaWorker101 --payload '{"query":"xbox"}' --priority 2 --timeout 60`
  1. With enqueue.java - get latest iron_worker_java lib (https://github.com/iron-io/iron_worker_java), change query, compile it and launch.
1. Look at [HUD](https://hud.iron.io) to view your tasks running, check logs, etc.
1. Schedule a task:
  1. From command line: `iron_worker schedule JavaWorker101 --payload '{"query":"heyaa"}' --delay 5 --timeout 60 --start-at "12:30" --run-times 5 --run-every 70`


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
