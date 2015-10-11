## Quick Example for a Python Worker (4 minutes)

This example will show you how to include dependencies with your worker so it will work right when you run it
remotely on IronWorker.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker).

If you want to use Python 3, change the image name in the commands below to iron/python:3
and use the `python3` interpreter.

Let's install our modules into this folder using `pip install -t packages -r requirements.txt` and we're doing it
inside Docker in case there are some native extensions.

```sh
docker run --rm -v "$PWD":/worker -w /worker iron/python:2-dev pip install -t packages -r requirements.txt
```

Now run our script inside Docker to test it:

```sh
docker run --rm -e "PAYLOAD_FILE=hello.payload.json" -v "$PWD":/worker -w /worker iron/python:2 python hello.py
```

And now that it works, we know it will work on IronWorker.

Let's package it up:

```sh
zip -r hello.zip .
```

Then upload it:

```sh
iron worker upload --name hellopy --zip hello.zip iron/python:2 python hello.py
```

And finally queue up a job for it!

```sh
iron worker queue --payload-file hello.payload.json --wait hellopy
```

The `--wait` parameter waits for the job to finish, then prints the output.
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.
