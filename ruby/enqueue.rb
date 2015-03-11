# Here is how to queue tasks from Ruby.

require 'iron_worker'

client = IronWorker::Client.new()
client.tasks.create(
    'hello', {name: "Snoop Dogg"}
)
