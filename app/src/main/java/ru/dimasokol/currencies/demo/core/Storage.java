package ru.dimasokol.currencies.demo.core;

import android.net.Uri;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * <p></p>
 * <p>Добавлено: 20.06.16</p>
 *
 * @author sokol
 */
public class Storage {

    private final Map<Uri, Content> mContentMap = new ConcurrentHashMap<>();

    public void store(Uri uri, Content content) {
        mContentMap.put(uri, content);
    }

    public Content retrieve(Uri uri) {
        return mContentMap.get(uri);
    }
}
