require_relative 'iron_worker'
# require 'iron_mq'
require_relative 'bundle/bundler/setup'

puts "Hello Worker!"

puts "here is the config: #{IronWorker.config}"
puts "Here is the payload: #{IronWorker.payload}"

