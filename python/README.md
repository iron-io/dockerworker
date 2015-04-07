## Quick Example for a Python Worker (4 minutes)

This example will show you how to include dependencies with your worker so it will work right when you run it
remotely on IronWorker.

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker). 

**Note**: You'll need Python installed on your machine to use this example (And [pip](https://pypi.python.org/pypi/pip) for dependencies).

Install the dependencies to your system.
```sh
pip install -r requirements.txt
```
Now run the example worker in this repo called `hello.py`, outside of the Iron.io container.

```sh
python hello.py -payload hello.payload.json
```

Now try running it in an Iron.io Docker container, [stack](http://dev.iron.io/worker/reference/environment/#default_language_versions), (if this is your first time running this, it will take a bit to download
the Docker container so be patient, it will only do it the first time):

```sh
docker run --rm -v "$(pwd)":/worker -w /worker iron/images:python-2.7 sh -c 'python hello.py -payload hello.payload.json'
```

Doh! Doesn't work! You should see an error with this in it:`ImportError: No module named iron_mq`,
which means it can't find the iron_mq module inside the container. We need to ensure we have all our dependencies
available inside the container and we do that by vendoring them into the same directory as your worker.
So let's install our modules into this folder using `pip install -t packages -r requirements.txt` and we're doing it inside Docker in case 
there are some native extensions.

```sh
docker run --rm -v "$(pwd)":/worker -w /worker iron/images:python-2.7 sh -c 'pip install -t packages -r requirements.txt'
```

Now we need to make a slight modification to hello.py to use the vendored modules. Open hello.py and 
add 
```python
import sys
sys.path.append("packages")
``` 
at the top of the file. Now run it again inside Docker.

```sh
docker run --rm -v "$(pwd)":/worker -w /worker iron/images:python-2.7 sh -c 'python hello.py -payload hello.payload.json'
```

It works! And now that it works, we know it will work on IronWorker.

Let's package it up:

```sh
zip -r hello.zip .
```

Then upload it:

```sh
iron worker upload --stack python-2.7 hello.zip python hello.py
```

Notice the --stack parameter is the same as the Docker container we used above.

And finally queue up a job for it!

```sh
iron worker queue --payload-file hello.payload.json --wait hello
```

The `--wait` parameter waits for the job to finish, then prints the output.
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.



