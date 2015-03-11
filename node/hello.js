
var iron_worker = require('iron_worker');

// Get task runtime data
console.log("task_id:", iron_worker.taskId());
console.log("params:", iron_worker.params());
console.log("config:", iron_worker.config());

// IronMQ usage example
var iron_mq = require('iron_mq');

var imq = new iron_mq.Client({token: "MY_TOKEN", project_id: "MY_PROJECT_ID", queue_name: "MY_QUEUE"});

console.log("IronMQ Client: ", imq);
