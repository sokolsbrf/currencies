package ru.dimasokol.currencies.demo.core;

/**
 * <p></p>
 * <p>Добавлено: 20.06.16</p>
 *
 * @author sokol
 */
public abstract class Content<ContentType> {

    private ContentType mContent;

    public ContentType get() {
        return mContent;
    }

    public void set(ContentType content) {
        mContent = content;
    }
}
