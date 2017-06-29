/*******************************************************************************
 * Copyright (c) 2014 Faktor Zehn AG.
 * 
 * Alle Rechte vorbehalten.
 *******************************************************************************/

package org.linkki.core.ui.section.annotations;

import static org.linkki.core.ui.section.annotations.EnabledType.ENABLED;
import static org.linkki.core.ui.section.annotations.RequiredType.NOT_REQUIRED;
import static org.linkki.core.ui.section.annotations.VisibleType.VISIBLE;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.text.DecimalFormat;

import org.linkki.core.ui.section.annotations.adapters.DecimalFieldBindingDefinition;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@LinkkiBindingDefinition(DecimalFieldBindingDefinition.class)
public @interface UIDecimalField {

    int position();

    String label() default "";

    boolean noLabel() default false;

    EnabledType enabled() default ENABLED;

    RequiredType required() default NOT_REQUIRED;

    VisibleType visible() default VISIBLE;

    int maxLength() default 0;

    /**
     * Format for the UI representation of the value. See {@link DecimalFormat} for the
     * documentation of the pattern.
     */
    String format() default "#,##0.00##";

    String modelObject() default ModelObject.DEFAULT_NAME;

    String modelAttribute() default "";
}