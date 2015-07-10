## Quick Example for a Ruby Worker (5 minutes)

This example will show you how to include dependencies with your worker so it will work right when you run it
remotely on IronWorker.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker). 

Vendor dependencies:

```sh
docker run --rm -v "$(pwd)":/worker -w /worker iron/images:ruby-2.1 sh -c 'bundle install --standalone --clean'
```

Notice in `hello.rb`, we have the following: `require_relative 'bundle/bundler/setup'`. This makes it use the vendored gems. 

Now test it locally:

```sh
docker run --rm -v "$(pwd)":/worker -w /worker iron/images:ruby-2.1 sh -c 'ruby hello.rb -payload hello.payload.json -config hello.config.yml -id 123'
```

Boom, it works! And now that it works, we know it will work on IronWorker.

Let's package it up:

```sh
zip -r hello.zip .
```

Then upload it:

```sh
iron worker upload --name hello --zip hello.zip iron/images:ruby-2.1 ruby hello.rb
```

Notice the --stack parameter is the same as the Docker container we used above.

And finally queue up a job for it!

```sh
iron worker queue --payload-file hello.payload.json --wait hello
```

The `--wait` parameter waits for the job to finish, then prints the output.
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.

## Bundling the worker inside a Docker image

**NOTE**: This requires custom image docker feature on your IronWorker account. 

Build it:

```sh
docker build -t treeder/hello.rb .
```

Run it to test it:

```sh
docker run --rm -e "PAYLOAD_FILE=/wdata/hello.payload.json" -v "$(pwd)":/wdata treeder/hello.rb
```

Tag it with a version tag so you can be sure IronWorker has the latest version:

```sh
docker tag treeder/hello.rb treeder/hello.rb:v0.0.2
```

Push it to docker hub:

```sh
docker push treeder/hello.rb
```

Upload it to IronWorker:

```sh
iron worker upload --name helloimage treeder/hello.rb:v0.0.2
```

Then queue up a task for it:

```sh
iron worker queue --wait --payload-file hello.payload.json helloimage
```
