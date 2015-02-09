Docker Worker
============

## What is this?

Use the Iron.io Docker stacks to locally dev and test your IronWorker workers in the exact same environment it will
have when running remotely on the IronWorker cloud.

## The New Dev/Test Workflow

The new dev/test workflow is much simpler and quicker. The general workflow is the following:

1. Create your worker. All dependencies must in the current directory or in sub-directories.
2. Create an input/payload example file (check this into source control as an example)
3. Run your worker locally inside an Iron.io Stack container.
4. Debug/test until you get it working properly. 
4. Once it works like you want it to, upload it to IronWorker. You should only have to do this once until you want to make changes.

## Quick Example

Note: You'll need Ruby and Docker installed on your machine to use this example.

First, install the new [Iron cli](https://github.com/iron-io/ironcli) tool:

```sh
curl -sSL http://get.iron.io/cli | sh
```

Or if you'd prefer to download it yourself, you can grab the latest release from here: https://github.com/iron-io/ironcli

To check that it was installed properly, run:

```sh
iron --version
```

Now on to this example. Install the dependencies to your system.

```sh
bundle install
```

Now run the example worker in this repo called hello.rb, outside of the Iron.io Stack container.

```sh
ruby hello.rb --payload hello.payload.json --config hello.config.yml --id 123
```

Now try running it in the docker container:

```sh
docker run --rm -v "$(pwd)":/usr/src/myapp -w /usr/src/myapp iron/images:ruby-2.1 sh -c 'ruby hello.rb --payload hello.payload.json --config hello.config.yml --id 123'
```

Doh! Doesn't work! You should see an error with this in it: ``require': cannot load such file -- iron_mq (LoadError)`, 
which means it can't find the iron_mq gem inside the container. We need to ensure we have all our dependencies
available inside the container and we do that by vendoring them into the same directory as your worker. 
So let's install our gems into this folder using bundler. 

```sh
bundle install --standalone
```

Now we need to make a slight modification to hello.rb to use the vendored gems. Open hello.rb and 
replace `require 'iron_mq'` with `require_relative 'bundle/bundler/setup'`.  Now run it again
inside Docker.

```sh
docker run --rm -v "$(pwd)":/usr/src/myapp -w /usr/src/myapp iron/images:ruby-2.1 sh -c 'ruby hello.rb --payload hello.payload.json --config hello.config.yml --id 123'
```

Boom, it works! And now that it works, we know it will work on IronWorker.

Let's package it up:

```sh
zip -r hello.zip .
```

Then upload it:

```sh
iron worker upload --stack ruby-2.1 hello.zip ruby hello.rb
```

Notice the --stack parameter is the same as the Docker container we used above.

And finally queue up a job for it!

```sh
iron worker queue --payload-file hello.payload.json --wait hello
```

The `--wait` parameter waits for the job to finish, then prints the output. 
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.
