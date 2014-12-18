require_relative 'iron_worker'
require_relative 'vendor/bundle/bundler/setup'
#require 'iron_mq'

puts "Hello Worker!"

puts "here is the config: #{IronWorker.config}"
puts "Here is the payload: #{IronWorker.payload}"

