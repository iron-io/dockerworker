hello_worker
============

## TODO

- Make an iron cli in Go, just for uploading (see below)
- Change file_name in upload to `command` or something, so they can run whatever, such as `ruby hello.rb`
- Maybe good time to change cli params from the single dash word like `-payload` to `--payload` or just `-p`


## Trying this out

To run hello.rb example:

```
bundle install --standalone
```

I've already add this line to use the bundled gems: `require_relative 'vendor/bundle/bundler/setup'`

Then run:

```
ruby run.rb --payload hello.payload.json --config hello.config.yml --stack ruby-2.1 ruby hello.rb
```

That will run hello.rb locally.

Do we even need run.rb, user could just run docker directly like:

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
