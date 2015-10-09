## Quick Example for a C# Worker using Mono (3 minutes)

This example will show you how to build your C# IronWorker, test it locally, then upload it
to IronWorker for production.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker).

First, let's build it on the right architecture using the actual Docker image it will be running on. The
dependencies are passed into javac.

```sh
docker run --rm -v "$PWD":/worker -w /worker iron/mono mcs -r:System.Web.Extensions.dll worker101.cs
```

Now run it to test it out:

```sh
docker run -e PAYLOAD_FILE=hello.payload.json --rm -v "$PWD":/worker -w /worker iron/mono: mono worker101.exe
```

Now that we have it working, let's package it up:

```sh
zip -r hello-mono.zip .
```

And upload it:

```sh
iron worker upload --name hello-mono --zip hello-mono.zip iron/mono mono worker101.exe
```

Now queue up a task (or 1 million):

```sh
iron worker queue -payload-file hello.payload.json --wait hello-mono
```
