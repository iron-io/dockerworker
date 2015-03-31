import sys
sys.path.append("packages")
import iron_mq
import iron_worker
from helper import *

print 'Hello %s' % getPayload()['name']
print
print 'Here is the payload: %s' % getPayload()
print 'Here is the task_id: %s' % getTaskId()