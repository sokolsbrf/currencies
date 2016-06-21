package ru.dimasokol.currencies.demo.modules.history.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * <p></p>
 * <p>Добавлено: 14.06.16</p>
 *
 * @author sokol
 */
@Root(name = "Record")
public class HistoryRecord {

    @Attribute(name = "Id")
    private String id;

    @Attribute(name = "Date")
    private String date;

    @Element(name = "Nominal")
    private Double nominal;

    @Element(name = "Value")
    private Double value;


    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public Double getNominal() {
        return nominal;
    }

    public Double getValue() {
        return value;
    }
}
