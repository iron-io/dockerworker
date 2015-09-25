import sys
sys.path.append("packages")
import iron_mq
from iron_worker import *

print 'Hello %s' %  IronWorker.payload()['name']
print
print 'Here is the payload: %s' %  IronWorker.payload()
print 'Here is the task_id: %s' %  IronWorker.task_id()
