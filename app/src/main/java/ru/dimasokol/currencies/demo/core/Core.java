package ru.dimasokol.currencies.demo.core;

import android.content.Context;
import android.net.Uri;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * <p></p>
 * <p>Добавлено: 20.06.16</p>
 *
 * @author sokol
 */
public final class Core {

    private ExecutorService mNetworkExecutor;
    private Map<Uri, Task> mTasksByUri = new ConcurrentHashMap<>();
    private Context mContext;

    public Core(Context context) {
        mContext = context.getApplicationContext();
    }

    public void init() {
        mNetworkExecutor = Executors.newSingleThreadExecutor();
    }

    public synchronized OperationResult runTask(Uri uri, TaskRunner runner) {
        if (mNetworkExecutor == null) {
            OperationResult sessionOver = new OperationResultSessionOver();
            return sessionOver;
        }

        Task runningTask = mTasksByUri.get(uri);

        if (runningTask != null) {
            OperationResult result = runningTask.getOperationResult();

            if (result.isDirty() || !runningTask.getTaskRunner().equals(runner)) {
                return restartTask(uri, runner);
            }

            return result;
        }

        return restartTask(uri, runner);
    }

    /**
     * Завершает работу ядра, зачищая все возможные ресурсы. После вызова этого метода попытки
     * поставить задачу в очередь приведут к немедленному возврату результата завершённой сессии.
     */
    public void shutdown() {
        if (mNetworkExecutor != null)
            mNetworkExecutor.shutdown();

        mNetworkExecutor = null;
        mTasksByUri.clear();
    }

    void recallDirtyTask(Uri uri) {
        Task task = mTasksByUri.get(uri);

        if (task != null) {
            runTask(uri, task.getTaskRunner());
        }
    }

    Context getContext() {
        return mContext;
    }

    private OperationResult restartTask(Uri uri, TaskRunner runner) {
        Task task = new Task(this, uri, runner);
        mTasksByUri.put(uri, task);
        mNetworkExecutor.submit(task);
        return task.getOperationResult();
    }
}
