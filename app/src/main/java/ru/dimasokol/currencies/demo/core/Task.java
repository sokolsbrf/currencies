package ru.dimasokol.currencies.demo.core;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;

import java.lang.ref.WeakReference;

import ru.dimasokol.currencies.demo.core.exceptions.RequestException;

/**
 * <p>Связанная с URI задача, выполняющаяся или выполненная; в последнем случае превращается
 * фактически в хранилище результата запроса.</p>
 * <p>Для получения результата и статуса исполнения служит метод {@link #getOperationResult()}.</p>
 *
 * @author sokol
 */
public final class Task implements Runnable {

    private final Uri mContentUri;
    private final Context mContext;

    private volatile boolean mRunning = false;
    private final OperationResult mOperationResult;
    private TaskRunner mTaskRunner;

    private WeakReference<Core> mCoreWeakReference;

    /**
     * Создание новой задачи для указанного {@link Core ядра}.
     * @param core Ядро. Должно быть инициализировано и запущено, хотя этот факт не проверяется.
     * @param contentUri URI контента (<strong>не</strong> адрес для сетевого запроса, внутренний)
     * @param taskRunner Класс-исполнитель задачи; вся логика возложена на него.
     */
    public Task(@NonNull Core core, @NonNull Uri contentUri, @NonNull TaskRunner taskRunner) {
        mContentUri = contentUri;
        mContext = core.getContext();
        mOperationResult = new OperationResultImpl(this);
        mTaskRunner = taskRunner;

        mCoreWeakReference = new WeakReference<>(core);
    }

    /**
     * Возвращает результат операции для данного таска. Для отдельно взятого таска этот объект
     * не меняется, а потому не требует вызова этого метода каждый раз, зато требует осторожности
     * в части хранения ссылок на него (и, косвенно, на таск и данные).
     * @return {@link OperationResult} с данными или без них.
     */
    public final OperationResult getOperationResult() {
        synchronized (mOperationResult) {
            return mOperationResult;
        }
    }

    public TaskRunner getTaskRunner() {
        return mTaskRunner;
    }

    @Override
    public void run() {
        // Невероятный случай, но всё же
        if (mRunning)
            return;

        try {
            mRunning = true;
            mOperationResult.setLoading(true);

            Content content = mTaskRunner.execute();
            mOperationResult.setContent(content);

        } catch (RequestException e) {
            mOperationResult.putMessage(e.getUIMessage(mContext));
        } finally {
            mRunning = false;
            mOperationResult.setLoading(false);
            mContext.getContentResolver().notifyChange(mContentUri, null);
        }
    }

    /**
     * Помечает задачу как «грязную» и требующую перезапуска; метод необходим для вызова из
     * {@link OperationResult}.
     */
    void markAsDirty() {
        Core core = mCoreWeakReference.get();

        if (core != null) {
            core.recallDirtyTask(mContentUri);
        }
    }
}
