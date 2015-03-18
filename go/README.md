## Quick Example for a Go Worker (3 minutes)

This example will show you how to compile your code with the same architecture we have on IronWorker so it will
run properly.

Note: This will be more interesting if you have a Mac or Windows or something non Linux AMD64.

Let's build hello.go and run it.

```sh
go build -o hello && ./hello -payload hello.payload.json -config hello.config.yml -id 123
```

All good. Let's run it in the Iron.io Docker container:

```sh
docker run --rm -v "$(pwd)":/usr/src/myapp -w /usr/src/myapp iron/images:go-1.4 sh -c './hello -payload hello.payload.json -config hello.config.yml -id 123'
```

Doh!  Doesn't work!?  Why? Because you're building it on a different architecture than IronWorker.

So let's build it on the right architecture using the actual Docker image it will be running on. We need to have the
dependencies available in the docker container while building so we'll use godep to do that. 

```sh
godep save
docker run --rm -v "$(pwd)":/usr/src/myapp -w /usr/src/myapp iron/images:go-1.4 sh -c 'GOPATH=$GOPATH:"/usr/src/myapp/Godeps/_workspace" go build -o hello'
```

And run it again:

```sh
docker run --rm -v "$(pwd)":/usr/src/myapp -w /usr/src/myapp iron/images:go-1.4 sh -c './hello -payload hello.payload.json -config hello.config.yml -id 123'
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