## Quick Example for a Node Worker (4 minutes)

This example will show you how to include dependencies with your worker so it will work right when you run it
remotely on IronWorker.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker).

**Note**: You'll need Node.js installed on your machine to use this example.

Install the dependencies to your system.

```sh
docker run --rm -v "$PWD":/worker -w /worker iron/node:dev npm install
```

Now run it:

```sh
docker run --rm -e "PAYLOAD_FILE=hello.payload.json" -v "$PWD":/worker -w /worker iron/node node hello.js
```

It works! And now that it works, we know it will work on IronWorker.

Let's package it up:

```sh
zip -r hello.zip .
```

Then upload it:

```sh
iron worker upload --name hellojs --zip hello.zip iron/node node hello.js
```

Notice the --stack parameter is the same as the Docker container we used above.

And finally queue up a job for it!

```sh
iron worker queue --payload-file hello.payload.json --wait hellojs
```

The `--wait` parameter waits for the job to finish, then prints the output.
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.
