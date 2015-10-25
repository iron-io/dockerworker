## Quick Example for a Ruby Worker (5 minutes)

This example will show you how to test and deploy Ruby code to IronWorker.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker).

Vendor dependencies (if you update your Gemfile, rerun this):

```sh
docker run --rm -v "$PWD":/worker -w /worker iron/ruby:dev bundle install --standalone --clean
```

Notice in `hello.rb`, we add the following so it uses the vendored gems:

```ruby
require_relative 'bundle/bundler/setup'
```

Now test it locally:

```sh
docker run --rm -it -e "PAYLOAD_FILE=hello.payload.json" -v "$PWD":/worker -w /worker iron/ruby ruby hello.rb
```

Boom, it works! And now that it works, we know it will work on IronWorker.

Let's package it up inside a Docker image and upload it to a Docker Registry. Copy the [Dockerfile](https://github.com/iron-io/dockerworker/blob/master/ruby/Dockerfile) from this repository
and modify the ENTRYPOINT line to run your script, but for this example, it's all ready to go:

```sh
docker build -t username/hello.rb:0.0.1 .
```

That's just a standard `docker build` command. The 0.0.1 is the version which you can update
whenever you make changes to your code.

Test your image:

```sh
docker run --rm -it -e "PAYLOAD_FILE=hello.payload.json" username/hello.rb:0.0.1
```

Push it to Docker Hub:

```sh
docker push username/hello.rb
```

Ok, we're ready to run this on Iron now! But first we have to let Iron know about our
image.

```sh
iron worker register username/hello.rb:0.0.1
```

Now queue up a job (or 1 million jobs)!

```sh
iron worker queue --payload-file hello.payload.json --wait username/hello.rb
```

The `--wait` parameter waits for the job to finish, then prints the output.
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.

## Private images

If you want to keep your code private and use a [private Docker repository](https://docs.docker.com/docker-hub/repos/#private-repositories), you just need
to let Iron know it's private.

```sh
iron docker login -e YOUR_DOCKER_HUB_EMAIL -p YOUR_DOCKER_HUB_PASSWORD
```

Then just do everything the same as above.

## If you don't want to package your code using Docker

You can package and send your code to Iron directly with the instructions below.

Let's package it up:

```sh
zip -r hello.zip .
```

Then upload it:

```sh
iron worker upload --name hello --zip hello.zip iron/ruby ruby hello.rb
```

And finally queue up a job for it!

```sh
iron worker queue --payload-file hello.payload.json --wait hello
```

The `--wait` parameter waits for the job to finish, then prints the output.
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.
