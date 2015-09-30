## Quick Example for a Ruby Worker (5 minutes)

This example will show you how to test and deploy Ruby code to IronWorker.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker).

Vendor dependencies (if you update your Gemfile, rerun this):

```sh
docker run --rm -v "$(pwd)":/worker -w /worker iron/ruby-bundle sh -c 'bundle install --standalone --clean'
```

Notice in `hello.rb`, we add the following so it uses the vendored gems:

```ruby
require_relative 'bundle/bundler/setup'
```

Now test it locally:

```sh
docker run --rm -it -e "PAYLOAD_FILE=hello.payload.json" -v $PWD:/worker -w /worker iron/ruby ruby hello.rb
```

Boom, it works! And now that it works, we know it will work on IronWorker.

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

<!--
## Bundling the worker inside a Docker image

Follow the same steps above to run/test your worker. But instead of zipping it up, do the following:

Package it up inside an image and send it off to Docker HUB, see the `Dockerfile` for reference:

```sh
docker build -t treeder/hello.rb:latest .
```

Test your image:

```sh
docker run --rm -it -e "PAYLOAD_FILE=hello.payload.json" treeder/hello.rb
```

Now queue up a job for it!

```sh
iron worker queue --payload-file hello.payload.json --wait treeder/hello.rb
```

The `--wait` parameter waits for the job to finish, then prints the output.
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.
-->
