## Quick Example for a Python Worker (4 minutes)

This example will show you how to include dependencies with your worker so it will work right when you run it
remotely on IronWorker.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker). 

Let's install our modules into this folder using `pip install -t packages -r requirements.txt` and we're doing it 
inside Docker in case there are some native extensions.

```sh
docker run --rm -v "$(pwd)":/worker -w /worker iron/images:python-2.7 sh -c 'pip install -t packages -r requirements.txt'
```

Now run our script inside Docker to test it:

```sh
docker run --rm -v "$(pwd)":/worker -w /worker iron/images:python-2.7 sh -c 'python hello.py -payload hello.payload.json'
```

And now that it works, we know it will work on IronWorker.

Let's package it up:

```sh
zip -r hello.zip .
```

Then upload it:

```sh
iron worker upload --name hello --zip hello.zip iron/images:python-2.7 python hello.py
```

And finally queue up a job for it!

```sh
iron worker queue --payload-file hello.payload.json --wait hello
```

The `--wait` parameter waits for the job to finish, then prints the output.
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.



