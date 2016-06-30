package ru.dimasokol.currencies.demo.core;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

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

    /**
     * Выполняет новый {@link Task}, или возвращает результат уже выполняющегося/выполненного. Метод
     * построен таким образом, что {@code null} не будет возвращён никогда.
     *
     * <p>Важно помнить, что таск после загрузки данных остаётся висеть в роли хранилища контента;
     * итого, возможно, некоторые данные придётся очищать вручную через {@link #clearTask(Uri)}.</p>
     * @param uri URI таска
     * @param runner раннер таска, т.е. реально выполняющийся код
     * @return {@link OperationResult} этого таска
     */
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
     * Ищет выполняющийся {@link Task таск} и возвращает его {@link OperationResult}, опционально позволяет
     * задать результат по умолчанию (если таск не найден).
     *
     * <p><strong>Важно!</strong> От использования этого метода лучше отказаться в пользу
     * {@link #runTask(Uri, TaskRunner) runTask()} при любой возможности. Метод {@code findTask()}
     * предназначен только для случаев, когда <strong>абсолютно</strong> НЕ нужно запускать
     * таск в случае его отсутствия. Вариантов подобной необходимости не так много и большинство
     * из них сводится к вещам вроде проверки авторизации в каких-либо сервисах.</p>
     *
     * <p>Не стоит также воспринимать этот метод как способ получить данные из кэша: результатом
     * является {@link OperationResult}, который может находиться в любом состоянии: например,
     * выполняться прямо сейчас. А если данные уже кэшированы и валидны, то
     * {@link #runTask(Uri, TaskRunner) runTask()} тоже вернёт их сразу же. Главное отличие именно
     * и только в возможности не запускать таск при отсутствии данных.</p>
     * @param uri URI таска
     * @param resultIfNotFound результат если таск не найден; может быть {@code null}, в таком случае
     *                         возвращается пустой результат.
     * @return {@link OperationResult} найденного таска, или {@code resultIfNotFound} если таск не
     * найден, или {@link OperationResultEmpty} если {@code resultIfNotFound == null}
     */
    @NonNull
    public synchronized OperationResult findTask(Uri uri, @Nullable OperationResult resultIfNotFound) {
        Task runningTask = mTasksByUri.get(uri);

        if (runningTask == null) {
            if (resultIfNotFound != null)
                return resultIfNotFound;

            return OperationResultEmpty.INSTANCE;
        }

        return runningTask.getOperationResult();
    }

    /**
     * Очищает данные <strong>выполненного</strong> таска. Ничего не делает в случае отсутствия таска
     * с указанным URI, равно как и в случае когда таск сейчас выполняется.
     * @param uri URI таска
     * @return {@code true} если таск был удалён; {@code false} если не произошло никаких изменений
     */
    public synchronized boolean clearTask(Uri uri) {
        Task runningTask = mTasksByUri.get(uri);

        if (runningTask == null || runningTask.getOperationResult().isLoading())
            return false;

        mTasksByUri.remove(uri);
        return true;
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
