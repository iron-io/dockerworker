## Quick Example for a Go Worker (3 minutes)

This example will show you how to compile your code with the same architecture we have on IronWorker so it will
run properly.

**NOTE**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker). 

Let's build hello.go and run it.

```sh
go build -o hello && ./hello -payload hello.payload.json -config hello.config.yml -id 123
```

All good. Let's run it in the Iron.io Docker container:

```sh
docker run --rm -v "$(pwd)":/worker -w /worker iron/images:go-1.4 sh -c './hello -payload hello.payload.json -config hello.config.yml -id 123'
```

Doh!  Doesn't work!?  Why? Because you're building it on a different architecture than IronWorker. (If you're running on linux, this will probably work). 

So let's build it on the right architecture using the actual Docker image it will be running on. We need to have the
dependencies available in the docker container while building, so let's install the dependencies:

```sh
go get github.com/iron-io/iron_go/worker
```

And build it:

```sh
 docker run --rm -v "$GOPATH":/gopath -v "$(pwd)":/worker -w /worker google/golang sh -c 'go build -o hello'
```

We're using the google/golang container because the Iron one doesn't have the right tools setup to build properly. 
Also notice we mounted our local GOPATH into the container. 

And run it again:

```sh
docker run --rm -v "$(pwd)":/worker -w /worker iron/images:go-1.4 sh -c './hello -payload hello.payload.json -config hello.config.yml -id 123'
```

And now it works, so let's package it up:

```sh
zip -r hello-go.zip .
```

And upload it:

```sh
iron worker upload --stack go-1.4 hello-go.zip ./hello
```

Now queue up a task (or 1 million):

```sh
iron worker queue -payload-file hello.payload.json --wait hello-go
```
