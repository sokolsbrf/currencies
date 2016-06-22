package ru.dimasokol.currencies.demo.core.exceptions;

import android.content.Context;
import android.support.annotation.StringRes;

import ru.dimasokol.currencies.demo.core.ui.Error;
import ru.dimasokol.currencies.demo.core.ui.FatalError;
import ru.dimasokol.currencies.demo.core.ui.UIMessage;

/**
 * <p></p>
 * <p>Добавлено: 10.06.16</p>
 *
 * @author sokol
 */
public class RequestException extends BaseException {

    private boolean mFatal = false;

    public RequestException(@StringRes int errorStringResource) {
        super(errorStringResource);
    }

    public RequestException(@StringRes int errorStringResource, Object[] formatSpecifiers) {
        super(errorStringResource, formatSpecifiers);
    }

    public RequestException(Throwable throwable, @StringRes int errorStringResource) {
        super(throwable, errorStringResource);
    }

    public RequestException(Throwable throwable, @StringRes int errorStringResource, Object[] formatSpecifiers) {
        super(throwable, errorStringResource, formatSpecifiers);
    }

    public boolean isFatal() {
        return mFatal;
    }

    public void setFatal(boolean fatal) {
        mFatal = fatal;
    }

    public UIMessage getUIMessage(Context context) {
        if (isFatal())
            return new FatalError(getString(context));

        return new Error(getString(context));
    }
}
