<?php

require 'vendor/autoload.php';

echo "Hello World!\n\n";

echo "Payload: ";
print_r(IronWorker\Runtime::getPayload(true));

echo "\nConfig: ";
print_r(IronWorker\Runtime::getConfig(true));

// Add iron.json and uncomment this block to actually use it
/*
$ironmq = new \IronMQ\IronMQ();
$ironmq->postMessage('Some Queue', "Hello world");
$msg = $ironmq->getMessage('Some Queue');
var_dump($msg)
*/

