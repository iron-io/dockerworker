hello_worker
============

## TODO

- Make an iron cli in Go, just for uploading (see below)
- Change `file_name` in [upload](http://dev.iron.io/worker/reference/api/#upload_or_update_a_code_package) to `command` or something, so they can run whatever, such as `ruby hello.rb`
- Maybe good time to change cli params from the single dash word like `-payload` to `--payload` or just `-p`

## What is this?

Use our docker stacks to run exactly as it would when run remote. All dependencies must be locally in the current directory to
work, no iron_worker cli magic allowed. Then user can zip themselves and upload (like Lambda). 

## The dev/test process for a user will be much cleaner

1) Create script
2) Create input/payload example file (they should check this into source control)
3) Run/test script with example file (see docker command below)
4) Once it works, upload it and it should work no problem (assuming the input is the same format as the example).


## Trying this out

To run hello.rb example, first:

```
bundle install --standalone
```

I've already add this line to use the bundled gems: `require_relative 'vendor/bundle/bundler/setup'`

Then run it: 

```
docker run --rm -v "$(pwd)":/usr/src/myapp -w /usr/src/myapp iron/images:ruby-2.1 sh -c 'ruby hello.rb -payload hello.payload.json -config hello.config.yml -id 123'
```

Then if it works, they can run the following to package:

```
zip -r hello.zip .
```

Get the new Go based ironcli at https://github.com/iron-io/ironcli

Then upload this worker to IronWorker:

```
ironcli upload hello.zip ruby hello.rb
```

And queue up jobs for it!

```
ironcli queue --payload-file hello.payload.json hello
```

