hello_worker
============

## TODO

- Make an iron cli in Go, just for uploading (see below)
- Change `file_name` in [upload](http://dev.iron.io/worker/reference/api/#upload_or_update_a_code_package) to `command` or something, so they can run whatever, such as `ruby hello.rb`
- Maybe good time to change cli params from the single dash word like `-payload` to `--payload` or just `-p`

## What is this?

Use our docker stacks to run exactly as it would when run remote. All dependencies must be locally in the current directory to
work, no iron_worker cli magic allowed. Then user can zip themselves and upload (like Lambda). 


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

TODO: Then upload (maybe make a Go cli with upload as the first command. Make it one cli for all Iron products:

```
iron worker upload hello.zip ruby hello.rb
```
