package ru.dimasokol.currencies.demo.core.utils;

import org.simpleframework.xml.transform.Transform;

/**
 * <p></p>
 * <p>Добавлено: 10.06.16</p>
 *
 * @author sokol
 */
public class DoubleTransformer implements Transform<Double> {
    @Override
    public Double read(String s) throws Exception {
        return Double.parseDouble(s.replace(",", "."));
    }

    @Override
    public String write(Double aDouble) throws Exception {
        return Double.toString(aDouble);
    }

}
