/*******************************************************************************
 * Copyright (c) 2014 Faktor Zehn AG.
 *
 * Alle Rechte vorbehalten.
 *******************************************************************************/

package org.linkki.core.ui.section.annotations;

import org.apache.commons.lang3.StringUtils;
import org.linkki.core.binding.BindingContext;
import org.linkki.core.binding.ElementBinding;

import com.vaadin.ui.AbstractComponent;
import com.vaadin.ui.Component;
import com.vaadin.ui.Field;
import com.vaadin.ui.Label;

/**
 * Holds all information about a field, which are the property name as well as the settings for
 * visibility, enabled-state etc. The given property name is only used as fallback if there is
 * {@link UIFieldDefinition#modelAttribute()} is not set.
 *
 * @author widmaier
 */
public class FieldDescriptor implements ElementDescriptor {

    private final UIFieldDefinition fieldDefinition;
    private String fallbackPropertyName;

    /**
     * Constructs a new field description with the following parameters.
     *
     * @param fieldDef The field definition that holds every given annotated property
     * @param fallbackPropertyName a fallback property name that is used if
     *            {@link UIFieldDefinition#modelAttribute()} is not specified.
     */
    public FieldDescriptor(UIFieldDefinition fieldDef, String fallbackPropertyName) {
        this.fieldDefinition = fieldDef;
        this.fallbackPropertyName = fallbackPropertyName;
    }

    /**
     * Property derived from the "modelAttribute" property defined by the annotation. If no
     * "modelAttribute" exists, derives the property name from the name of the annotated method.
     */
    @Override
    public String getPropertyName() {
        if (StringUtils.isEmpty(fieldDefinition.modelAttribute())) {
            return fallbackPropertyName;
        }
        return fieldDefinition.modelAttribute();
    }

    @Override
    public EnabledType enabled() {
        return fieldDefinition.enabled();
    }

    @Override
    public VisibleType visible() {
        return fieldDefinition.visible();
    }

    public RequiredType required() {
        return fieldDefinition.required();
    }

    public AvailableValuesType availableValues() {
        return fieldDefinition.availableValues();
    }

    @Override
    public int getPosition() {
        return fieldDefinition.position();
    }

    /**
     * Derives the label from the label defined in the annotation. If no label is defined, derives
     * the label from the property name. Appends the suffix ":" if necessary.
     */
    @Override
    public String getLabelText() {
        if (!fieldDefinition.showLabel()) {
            return "";
        }

        String label = fieldDefinition.label();
        if (StringUtils.isEmpty(label)) {
            label = StringUtils.capitalize(getPropertyName());
        }
        return label;
    }

    @Override
    public Component newComponent() {
        AbstractComponent component = (AbstractComponent)fieldDefinition.newComponent();
        component.setImmediate(true);
        return component;
    }

    @Override
    public ElementBinding createBinding(BindingContext bindingContext,
            Object pmo,
            Label label,
            Component component) {
        return bindingContext.bind(pmo, this, label, (Field<?>)component);
    }

    @Override
    public String toString() {
        return "FieldDescriptor [fieldDefinition=" + fieldDefinition + ", fallbackPropertyName=" + fallbackPropertyName
                + "]";
    }

}
