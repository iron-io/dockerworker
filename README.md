hello_worker
============

## What is this?

Use our docker stacks to dev and test locally with the exact same environment as it has when when it runs remote. 


## The dev/test process for a user will be much cleaner

1. Create a script. All dependencies must in the current directory or in sub-directories. This is the key thing. It may
work normally, but when you run it in the docker container it doesn't. If it works in the container, then you're all good.
2. Create input/payload example file (they should check this into source control)
3. Run/test script with example file (see docker command below)
4. Once it works, upload it and it should work no problem (assuming the input is the same format as the example).

## Trying this out

First, let's run the example as is:

```sh
ruby hello.rb --payload hello.payload.json --config hello.config.yml --id 123
```

Now let's run it in the docker container:

```
docker run --rm -v "$(pwd)":/usr/src/myapp -w /usr/src/myapp iron/images:ruby-2.1 sh -c 'ruby hello.rb --payload hello.payload.json --config hello.config.yml --id 123'
```

Doh! Doesn't work, it can't find the iron_mq gem inside the container. We need to ensure we have all our dependencies
available locally. So let's install our gems locally.

```
bundle install --standalone
```

Then open hello.rb and replace `require 'iron_mq'` with `require_relative 'bundle/bundler/setup'`.  Now let's run it again
inside Docker.

```
docker run --rm -v "$(pwd)":/usr/src/myapp -w /usr/src/myapp iron/images:ruby-2.1 sh -c 'ruby hello.rb --payload hello.payload.json --config hello.config.yml --id 123'
```

Boom, it works! So let's package it up to upload to IronWorker:

```
zip -r hello.zip .
```

Get the new Go based ironcli at https://github.com/iron-io/ironcli (see README for one liner installation).

Then upload it:

```
iron upload hello.zip ruby hello.rb
```

And finally queue up jobs for it! One or millions.

```
iron queue --payload-file hello.payload.json hello
```
