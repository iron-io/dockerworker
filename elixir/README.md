
## Quick Example for an Elixir Worker (5 minutes)

This example will show you how to test and deploy Elixir code to IronWorker.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker).

The structure of this repository is a [mix project](http://elixir-lang.org/getting-started/mix-otp/introduction-to-mix.html).

Vendor (if you have any dependencies defined in mix.exs):

```sh
docker run --rm -it -v "$PWD":/worker -w /worker iron/elixir mix deps.get
```

Test:

```sh
docker run --rm -it -e "PAYLOAD_FILE=hello.payload.json" -v "$PWD":/worker -w /worker iron/elixir mix run hello.exs
```

Boom, it works! And now that it works, we know it will work on IronWorker.

Let's package it up:

```sh
zip -r hello-elixir.zip .
```

Then upload it:

```sh
iron worker upload --name hello-elixir --zip hello-elixir.zip iron/elixir mix run hello.exs
```

And finally queue up a job for it!

```sh
iron worker queue --payload-file hello.payload.json --wait hello-elixir
```

The `--wait` parameter waits for the job to finish, then prints the output.
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.
