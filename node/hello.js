
var iw = require('node_helper');
var iron_mq = require('iron_mq');

console.log("task_id:", iw.task_id);
console.log("params:", iw.params);
console.log("config:", iw.config);

var imq = new iron_mq.Client({token: "MY_TOKEN", project_id: "MY_PROJECT_ID", queue_name: "MY_QUEUE"});

console.log("IronMQ Client: ", imq);