import sys, json


def getPayload():
    file = None
    for i in range(len(sys.argv)):
        if sys.argv[i] == '-payload' and (i + 1) < len(sys.argv):
            file = sys.argv[i + 1]
            break

    with open(file) as content:
        data = json.load(content)

    return data


def getTaskId():
    task_id = None
    for i in range(len(sys.argv)):
        if sys.argv[i] == '-id' and (i + 1) < len(sys.argv):
            task_id = sys.argv[i + 1]
            break

    return task_id