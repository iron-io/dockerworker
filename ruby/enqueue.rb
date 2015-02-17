# Here is how to queue tasks from Ruby.

require 'iron_worker_ng'
client = IronWorkerNG::Client.new()
client.tasks.create(
    'hello', {ruby_foo: "bar"}
)
