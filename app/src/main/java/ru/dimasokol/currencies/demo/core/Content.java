package ru.dimasokol.currencies.demo.core;

/**
 * <p>Загруженный контент любого типа</p>
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
