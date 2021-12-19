# WorkManagerUpgrade
WorkManagrUpgrade

## Main Branch

The main branch defines SampleWorker that is scheduled every 15 min.
This worked updates shared preferences with time at which it get exeecuted.
In MainActiviy getWorkInfoByIdLiveData gets updates when periodic worker finishes.
The MainActivity then updates UI.

NOTE: For recurring tasks, you can't set outputdata in results, hence using shared preferences.

## feature/version2 branch
In this branch we remove the SampleWorker, now after 15 min workmanager attempts to
execute the task and logs ClassNotFoundException -- note the app itself doesn't crash, note
chronometer keeps running

## feature/version3 branch

In this branch we cancel the SampleWorker task explicitly when app is upgraded.
