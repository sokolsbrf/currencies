package ru.dimasokol.currencies.demo.currencies.parsers;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * <p></p>
 * <p>Добавлено: 10.06.16</p>
 *
 * @author sokol
 */
@Root(name = "Valute")
public class Currency {
    @Attribute(name = "ID")
    private String id;

    @Element(name = "Name")
    private String name;

    @Element(name = "NumCode")
    private int numCode;

    @Element(name = "CharCode")
    private String charCode;

    @Element(name = "Nominal")
    private Double nominal;

    @Element(name = "Value")
    private Double value;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getNumCode() {
        return numCode;
    }

    public String getCharCode() {
        return charCode;
    }

    public Double getNominal() {
        return nominal;
    }

    public Double getValue() {
        return value;
    }
}
