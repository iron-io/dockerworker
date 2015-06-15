## The simplest worker you can make

This is a simple worker that will just print "Hello world!" Not a very useful example, but you'll be running code
on the IronWorker platform in minutes. 

**Note**: Be sure you've followed the base [getting started instructions on the top level README](https://github.com/iron-io/dockerworker). 

```sh
docker run --rm -v "$(pwd)":/worker -w /worker iron/images:ruby-2.1 sh -c 'ruby helloworld.rb'
```

The fact that it runs means it's all good to run on IronWorker, so lets upload it and queue up a task for it so it runs on
the IronWorker platform.

Let's package it up first:

```sh
zip -r helloworld.zip .
```

Then upload it:

```sh
iron worker upload --name helloworld --zip helloworld.zip iron/images:ruby-2.1 helloworld.zip ruby helloworld.rb
```

Notice the --stack parameter is the same as the Docker container we used above.

And finally queue up a job for it!

```sh
iron worker queue --wait helloworld
```

The `--wait` parameter waits for the job to finish, then prints the output.
You will also see a link to [HUD](http://hud.iron.io) where you can see all the rest of the task details along with the log output.

That's it, you've ran a worker on the IronWorker cloud platform!
