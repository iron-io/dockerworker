# Here is how to queue tasks from Python.
from iron_worker import *

worker = IronWorker(project_id=your_project_id, token=your_project_token)
task = worker.queue(code_name="hello", payload={"fruits": ["apples", "oranges", "bananas"], "best_song_ever": "Call Me Maybe"})

print task.id