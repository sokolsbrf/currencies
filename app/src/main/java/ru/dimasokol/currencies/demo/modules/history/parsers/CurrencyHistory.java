package ru.dimasokol.currencies.demo.modules.history.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.List;

/**
 * <p></p>
 * <p>Добавлено: 10.06.16</p>
 *
 * @author sokol
 */
@Root(name = "ValCurs")
public class CurrencyHistory {

    @Attribute(name = "ID")
    private String id;

    @Attribute(name = "DateRange1")
    private String fromDate;

    @Attribute(name = "DateRange2")
    private String toDate;

    @Attribute(name = "name")
    private String name;

    @ElementList(entry = "Record", inline = true)
    private List<HistoryRecord> mHistory;

    public String getId() {
        return id;
    }

    public String getFromDate() {
        return fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public String getName() {
        return name;
    }

    public List<HistoryRecord> getHistory() {
        return mHistory;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("CurrencyHistory ");
        builder.append(System.identityHashCode(this)).append(" {");
        builder.append("id = ").append(id);
        builder.append(", from = ").append(fromDate.toString());
        builder.append(", to = ").append(toDate.toString());
        builder.append(", name = ").append(name);
        builder.append(", history size = ").append(mHistory.size()).append("}");

        return builder.toString();
    }
}
