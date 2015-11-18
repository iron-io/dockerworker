## Quick Example for a PHP Worker (4 minutes)

This example will show you how to include dependencies with your worker so it will work right when you run it
remotely on IronWorker.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker).

Install the dependencies:

```sh
docker run --rm -v "$PWD":/worker -w /worker iron/php:dev composer install
```

Now run it:

```sh
docker run --rm -e "PAYLOAD_FILE=hello.payload.json" -v "$PWD":/worker -w /worker iron/php php hello.php
```

It works! And now that it works, we know it will work on IronWorker.

Let's package it up:

```sh
zip -r hello.zip .
```

Then upload it:

```sh
iron worker upload --name hello-php --zip hello.zip iron/php php hello.php
```

And queue up a job for it!

```sh
iron worker queue --payload-file hello.payload.json --wait hello-php
```

The `--wait` parameter waits for the job to finish, then prints the output.
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.
