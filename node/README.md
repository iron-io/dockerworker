## Quick Example for a Node Worker (4 minutes)

This example will show you how to include dependencies with your worker so it will work right when you run it
remotely on IronWorker.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker). 

**Note**: You'll need Node.js installed on your machine to use this example.

Install the dependencies to your system.

```sh
npm install
```
Now run the example worker in this repo called `hello.js`, outside of the Iron.io container.

```sh
node hello.js -payload hello.payload.json
```

Now try running it in an Iron.io Docker container, [stack](http://dev.iron.io/worker/reference/environment/#default_language_versions), (if this is your first time running this, it will take a bit to download
the Docker container so be patient, it will only do it the first time):

```sh
docker run --rm -v "$(pwd)":/worker -w /worker iron/images:node-0.10 sh -c 'node hello.js -payload hello.payload.json'
```

It works! And now that it works, we know it will work on IronWorker.

Let's package it up:

```sh
zip -r hello.zip .
```

Then upload it:

```sh
iron worker upload --stack node-0.10 hello.zip node hello.js
```

Notice the --stack parameter is the same as the Docker container we used above.

And finally queue up a job for it!

```sh
iron worker queue --payload-file hello.payload.json --wait hello
```

The `--wait` parameter waits for the job to finish, then prints the output.
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.



