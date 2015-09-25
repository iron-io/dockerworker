## Quick Example for a Go Worker (3 minutes)

This example will show you how to test and deploy Go (Golang) code to IronWorker.

**NOTE**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker).

Vendor dependencies:

```sh
docker run --rm -it -v "$PWD":/go/src/x/y/z -w /go/src/x/y/z -e "GOPATH=/go/src/x/y/z/vendor:/go" iron/go go get
```

And build it:


```sh
docker run --rm -it -v "$PWD":/go/src/x/y/z -w /go/src/x/y/z -e "GOPATH=/go/src/x/y/z/vendor:/go" iron/go go build -o hello
```

Run it:

```sh
docker run --rm -it -e "PAYLOAD_FILE=hello.payload.json" -v "$PWD":/app -w /app  iron/base ./hello
```

And now it works, so let's package it up:

```sh
zip -r hello-go.zip .
```

And upload it:

```sh
iron worker upload --name hello-go --zip hello-go.zip iron/base ./hello
```

Now queue up a task (or 1 million):

```sh
iron worker queue -payload-file hello.payload.json --wait hello-go
```
