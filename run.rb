# This is an example of some kind of tool we could use
#
# Use like this:
# ruby run.rb --payload hello.payload.json --config hello.config.yml --stack ruby-2.1 ruby hello.rb

require 'optparse'
require 'open3'


options = {}
OptionParser.new do |opts|
  opts.banner = "Usage: example.rb [options]"

  opts.on("-p", "--payload PAYLOAD", "Payload file") do |v|
    options[:payload] = v
  end
  opts.on("-c", "--config [CONFIG]", "Config file") do |v|
    options[:config] = v
  end
  opts.on("-s", "--stack STACK", "Worker stack: http://dev.iron.io/worker/reference/environment/#default_language_versions") do |v|
    options[:stack] = v
  end

  opts.on_tail("-h", "--help", "Show this message") do
    puts opts
    exit
  end

end.parse!

d = "/usr/src/myapp"

p options
p ARGV
cmdsplit = ARGV.dup
cmdsplit += ["-payload", options[:payload]]
cmdsplit += ["-config", options[:config]]
cmdsplit += ["-d", d]
cmd = cmdsplit.join(" ")

Open3.popen2e(*cmdsplit) {|i,oe,t|
  oe.each {|line|
    puts line
  }
  #exit_status = wait_thr.value # Process::Status object returned.
}

puts "Trying docker now..."

dcmd = "docker run --rm -v \"#{Dir.getwd}\":#{d} -w #{d} iron/images:#{options[:stack]} sh -c '#{cmd}'"
Open3.popen2e(dcmd) {|i,oe,t|

  oe.each {|line|
    puts line
  }
  #exit_status = wait_thr.value # Process::Status object returned.
}