/*******************************************************************************
 * Copyright (c) 2014 Faktor Zehn AG.
 *
 * Alle Rechte vorbehalten.
 *******************************************************************************/

package org.linkki.core.ui.components;

import java.text.NumberFormat;

class DoubleFieldConverter extends AbstractNumberFieldConverter<Double> {

    private static final long serialVersionUID = 6756969882235490962L;

    DoubleFieldConverter(NumberFormat format) {
        super(format);
    }

    @Override
    public Class<Double> getModelType() {
        return Double.class;
    }

    @Override
    protected Double convertToModel(Number value) {
        return value.doubleValue();
    }

}