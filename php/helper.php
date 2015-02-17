<?php

// see https://github.com/iron-io/iron_worker_ruby_ng/blob/master/lib/iron_worker_ng/code/runtime/php.rb

function getArgs($assoc = true) {
  global $argv;

  $args = array('task_id' => null, 'dir' => null, 'payload' => array(), 'config' => null);

  foreach ($argv as $k => $v) {
    if (empty($argv[$k + 1])) continue;

    if ($v == '-id') $args['task_id'] = $argv[$k + 1];
    if ($v == '-d')  $args['dir']     = $argv[$k + 1];

    if ($v == '-payload' && file_exists($argv[$k + 1])) {
      $args['payload'] = file_get_contents($argv[$k + 1]);

      $parsed_payload = json_decode($args['payload'], $assoc);

      if ($parsed_payload != null) {
        $args['payload'] = $parsed_payload;
      }
    }

    if ($v == '-config' && file_exists($argv[$k + 1])) {
      $args['config'] = file_get_contents($argv[$k + 1]);

      $parsed_config = json_decode($args['config'], $assoc);

      if ($parsed_config != null) {
          $args['config'] = $parsed_config;
      }
    }
  }

  return $args;
}

function getPayload($assoc = false) {
  $args = getArgs($assoc);

  return $args['payload'];
}

function getConfig($assoc = true){
  $args = getArgs($assoc);

  return $args['config'];
}