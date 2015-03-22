#require_relative 'bundle/bundler/setup'
require 'iron_mq'
require 'iron_worker'

puts "Hello #{IronWorker.payload["name"]}!"
puts
puts "Here is the payload: #{IronWorker.payload}"
puts "here is the config: #{IronWorker.config}"

