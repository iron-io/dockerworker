## Quick Example for a Ruby Worker (5 minutes)

This example will show you how to test and deploy Ruby code to IronWorker.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker).

### 1. Vendor dependencies (if you update your Gemfile, rerun this):

```sh
docker run --rm -v "$PWD":/worker -w /worker iron/ruby:dev bundle install --standalone --clean
```

Notice in `hello.rb`, we add the following so it uses the vendored gems:

```ruby
require_relative 'bundle/bundler/setup'
```

### 2. Test locally

Now test it locally:

```sh
docker run --rm -it -e "PAYLOAD_FILE=hello.payload.json" -v "$PWD":/worker -w /worker iron/ruby ruby hello.rb
```

The PAYLOAD_FILE environment variable is passed in to your worker automatically and tells you
where the payload file is. Our [client libraries](http://dev.iron.io/worker/libraries/) help you load the special environment variables automatically.

Boom, it works! And now that it works, we know it will work on IronWorker.

### 3. Package your code

Let's package it up inside a Docker image and upload it to a Docker Registry. Copy the [Dockerfile](https://github.com/iron-io/dockerworker/blob/master/ruby/Dockerfile) from this repository
and modify the ENTRYPOINT line to run your script, but for this example, it's all ready to go:

```sh
docker build -t username/hello:0.0.1 .
```

That's just a standard `docker build` command. The 0.0.1 is the version which you can update
whenever you make changes to your code.

Test your image, just to be sure you created it correctly:

```sh
docker run --rm -it -e "PAYLOAD_FILE=hello.payload.json" username/hello:0.0.1
```

### 4. Push it to Docker Hub

Push it to Docker Hub:

```sh
docker push username/hello
```

### 4. Register your image with Iron

Ok, we're ready to run this on Iron now, but first we have to let Iron know about the
image you just pushed to Docker Hub. Also, you can optionally register environment variables here that will be passed into your container at runtime. 

```sh
iron worker register -e "NAME=JOHNNY" username/hello:0.0.1
```

### 5. Queue / Schedule jobs for your image

Now you can start queuing jobs or schedule recurring jobs for your image. Let's quickly
queue up a job to try it out.

```sh
iron worker queue --payload-file hello.payload.json --wait username/hello
```

The `--wait` parameter waits for the job to finish, then prints the output.
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.

Read the API docs to see how to queue jobs from your code or how to schedule them:
http://dev.iron.io/worker/reference/api/

## Private images

If you want to keep your code private and use a [private Docker repository](https://docs.docker.com/docker-hub/repos/#private-repositories), you just need
to let Iron know it's private.

```sh
iron docker login -e YOUR_DOCKER_HUB_EMAIL -p YOUR_DOCKER_HUB_PASSWORD
```

Then just do everything the same as above.

## If you don't want to package your code using Docker

You can package and send your code to Iron directly with the instructions below.
Start with steps 1 and 2 above, then continue at step 3 here.

### 3. Package your code

Let's package it up:

```sh
zip -r hello.zip .
```

### 4. Upload your code

Then upload it:

```sh
iron worker upload --name hello --zip hello.zip iron/ruby ruby hello.rb
```

### 5. Queue / Schedule jobs for your worker

Now you can start queuing jobs or schedule recurring jobs for your worker. Let's quickly
queue up a job to try it out.

```sh
iron worker queue --payload-file hello.payload.json --wait hello
```

The `--wait` parameter waits for the job to finish, then prints the output.
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.
