package ru.dimasokol.currencies.demo.core.exceptions;

import android.content.Context;
import android.support.annotation.StringRes;

/**
 * <p></p>
 * <p>Добавлено: 10.06.16</p>
 *
 * @author sokol
 */
public abstract class BaseException extends Exception {

    private int mErrorStringResource;
    private Object[] mFormatSpecifiers;

    public BaseException(@StringRes int errorStringResource) {
        mErrorStringResource = errorStringResource;
    }

    public BaseException(@StringRes int errorStringResource, Object[] formatSpecifiers) {
        mErrorStringResource = errorStringResource;
        mFormatSpecifiers = formatSpecifiers;
    }

    public BaseException(Throwable throwable, @StringRes int errorStringResource) {
        super(throwable);
        mErrorStringResource = errorStringResource;
    }

    public BaseException(Throwable throwable, @StringRes int errorStringResource, Object[] formatSpecifiers) {
        super(throwable);
        mErrorStringResource = errorStringResource;
        mFormatSpecifiers = formatSpecifiers;
    }

    public int getErrorStringResource() {
        return mErrorStringResource;
    }

    public Object[] getFormatSpecifiers() {
        return mFormatSpecifiers;
    }

    public String getString(Context context) {
        return context.getString(mErrorStringResource, mFormatSpecifiers);
    }
}
