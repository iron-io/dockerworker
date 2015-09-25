<?php

require 'vendor/autoload.php';

echo "Hello World!\n\n";

echo "Payload: ";
print_r(IronWorker\IronWorker::getPayload(true));

echo "\nConfig: ";
print_r(IronWorker\IronWorker::getPayload(true));

// Add iron.json and uncomment this block to actually use it
/*
$ironmq = new \IronMQ\IronMQ();
$ironmq->postMessage('Some Queue', "Hello world");
$msg = $ironmq->getMessage('Some Queue');
var_dump($msg)
*/

echo "\n\nCLI Table:\n";
$headers = array('Id', 'Name', 'City');
$data = array(
  array(1,  'Elliott',    'San Francisco'),
  array(2,  'Washington', 'Bessemer'),
  array(3,  'Hopkins',    'Altoona'),
);
$table = new \cli\Table();
$table->setHeaders($headers);
$table->setRows($data);
$table->display();
