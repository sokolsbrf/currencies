package ru.dimasokol.currencies.demo.modules.currencies.parsers;

import android.support.annotation.Nullable;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import ru.dimasokol.currencies.demo.core.utils.DateFormatUtils;


/**
 * <p></p>
 * <p>Добавлено: 10.06.16</p>
 *
 * @author sokol
 */
@Root(name = "ValCurs")
public class CurrenciesList implements Serializable {

    @Attribute(name = "Date")
    private String date;

    @Attribute
    private String name;

    @ElementList(entry = "Valute", inline = true)
    private List<Currency> currencies;

    @Nullable
    public Date getDate() {
        return DateFormatUtils.dateFromString(date);
    }

    public String getName() {
        return name;
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }
}
