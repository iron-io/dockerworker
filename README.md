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

## Getting Started

1\. You'll need [Docker](http://docker.com) installed on your machine to try this out.

2\. You'll want to install the new [Iron cli](https://github.com/iron-io/ironcli) tool as well (not totally necessary, but makes things a lot easier):
                   
```sh
curl -sSL http://get.iron.io/cli | sh
```

Or if you'd prefer to download it yourself, you can grab the latest release from here: https://github.com/iron-io/ironcli/releases

3\. Check that the Iron cli tool was installed properly:
    
```sh
iron --version
```

4\. Clone this repo:
    
```sh
git clone https://github.com/iron-io/dockerworker.git
```

And cd into the directory:
    
```sh
cd dockerworker
```

Now you're ready to try the examples.

## Quick Example for a Ruby Worker (5 minutes)

This example will show you how to include dependencies with your worker so it will work right when you run it 
remotely on IronWorker.

Note: You'll need Ruby installed on your machine to use this example.

Install the dependencies to your system.

```sh
bundle install
```

Now run the example worker in this repo called hello.rb, outside of the Iron.io container.

```sh
ruby hello.rb --payload hello.payload.json --config hello.config.yml --id 123
```

Now try running it in an Iron.io Docker container, [stack](http://dev.iron.io/worker/reference/environment/#default_language_versions), (if this is your first time running this, it will take a bit to download
the Docker container so be patient, it will only do it the first time):

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

## Quick Example for a Go Worker (3 minutes)

This example will show you how to compile your code with the same architecture we have on IronWorker so it will
run properly. 

Note: This will only be interesting if you have a Mac or Windows or something non Linux AMD64.
Note: You'll need the Go sdk installed on your machine. 

TODO: Use a lib to read in payload or just do the full load file and parse to read payload. 

Let's build hello.go and run it. 

```sh
go build -o hello && ./hello
```

All good. Let's run it in the Iron.io Docker container:

```sh
docker run --rm -v "$(pwd)":/usr/src/myapp -w /usr/src/myapp iron/images:go-1.4 sh -c './hello --payload hello.payload.json --config hello.config.yml --id 123'
```

Doh!  Doesn't work!?  Why? Because you're building it on a different architecture that IronWorker.

So let's build it on the right architecture using the actual Docker image it will be running on. 

```sh
docker run --rm -v "$(pwd)":/usr/src/myapp -w /usr/src/myapp iron/images:go-1.4 sh -c 'go build -o hello'
```

And run it again:

```sh
docker run --rm -v "$(pwd)":/usr/src/myapp -w /usr/src/myapp iron/images:go-1.4 sh -c './hello --payload hello.payload.json --config hello.config.yml --id 123'
```

And now it works, so let's package it up:

```sh
zip -r hello-go.zip .
```

And upload it:

```sh
iron worker upload --stack go-1.4 hello-go.zip ./hello
```

Now queue up a task (or 1 million):

```sh
iron worker queue --payload-file hello.payload.json --wait hello-go
```
