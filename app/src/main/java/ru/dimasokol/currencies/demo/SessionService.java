package ru.dimasokol.currencies.demo;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

import java.util.Date;

import ru.dimasokol.currencies.demo.core.Core;
import ru.dimasokol.currencies.demo.core.CoreHolder;
import ru.dimasokol.currencies.demo.core.OperationResult;
import ru.dimasokol.currencies.demo.currencies.CurrenciesFacade;

public class SessionService extends Service {

    private Core mCore;

    public SessionService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mCore = ((CoreHolder) getApplication()).getCore();
        mCore.init();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mCore.shutdown();
    }
}
