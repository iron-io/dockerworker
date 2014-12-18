# This could be a gem to help worker users with common functions like getting config and payload.
require 'json'

module IronWorker
  @@loaded = false
  @@args = {}
  @@payload = {}
  @@config = {}

  def self.loaded
    return @@loaded
  end

  def self.load
    return if @@loaded
    0.upto($*.length - 2) do |i|
      @@args[:root] = $*[i + 1] if $*[i] == '-d'
      @@args[:payload_file] = $*[i + 1] if $*[i] == '-payload'
      @@args[:config_file] = $*[i + 1] if $*[i] == '-config'
      @@args[:task_id] = $*[i + 1] if $*[i] == '-id'
    end

    puts "args: #{@@args.inspect}"

    if args[:payload_file]
      @@payload = File.read(@@args[:payload_file])

      begin
        @@payload = JSON.parse(@@payload)
      rescue => ex
        puts "Couldn't parse IronWorker payload into json, leaving as string. #{ex}"
      end
    end

    if args[:config_file]
      if args[:config_file]
        @@config = File.read(args[:config_file])
        begin
          @@config = JSON.parse(@@config)
        rescue
          # try yaml
          begin
            @@config = YAML.load(@@config)
          rescue => ex

          end
        end
      end
    end
  end

  def self.payload
    load
    @@payload
  end

  def self.config
    load
    @@config
  end

  def self.task_id
    @@args[:task_id]
  end

  def self.args
    return @@args
  end


end