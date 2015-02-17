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

Now you're ready to try the examples, choose your weapon:

* [Ruby](https://github.com/iron-io/dockerworker/tree/master/ruby)
* [Go](https://github.com/iron-io/dockerworker/tree/master/go)

