/*******************************************************************************
 * Copyright (c) 2014 Faktor Zehn AG.
 *
 * Alle Rechte vorbehalten.
 *******************************************************************************/

package org.linkki.core.ui.section.annotations;

import static java.util.Objects.requireNonNull;

import org.apache.commons.lang3.StringUtils;
import org.linkki.core.binding.FieldBinding;
import org.linkki.core.binding.dispatcher.PropertyDispatcher;
import org.linkki.util.handler.Handler;

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
    private final String pmoPropertyName;

    /**
     * Constructs a new field description with the following parameters.
     *
     * @param fieldDef The field definition that holds every given annotated property
     * @param pmoPropertyName the property name that is used to find the methods in the PMO.
     */
    public FieldDescriptor(UIFieldDefinition fieldDef, String pmoPropertyName) {
        this.fieldDefinition = fieldDef;
        this.pmoPropertyName = pmoPropertyName;
    }

    /**
     * Property derived from the "modelAttribute" property defined by the annotation. If no
     * "modelAttribute" exists, derives the property name from the name of the annotated method.
     */
    @Override
    public String getModelPropertyName() {
        if (StringUtils.isEmpty(fieldDefinition.modelAttribute())) {
            return getPmoPropertyName();
        }
        return fieldDefinition.modelAttribute();
    }

    @Override
    public String getModelObjectName() {
        return fieldDefinition.modelObject();
    }

    @Override
    public EnabledType enabled() {
        return fieldDefinition.enabled();
    }

    @Override
    public VisibleType visible() {
        return fieldDefinition.visible();
    }

    @Override
    public RequiredType required() {
        return fieldDefinition.required();
    }

    @Override
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
            label = StringUtils.capitalize(getModelPropertyName());
        }
        return label;
    }

    @Override
    public Component newComponent() {
        return fieldDefinition.newComponent();
    }

    @Override
    public FieldBinding<?> createBinding(PropertyDispatcher propertyDispatcher,
            Handler updateUi,
            Component component,
            Label label) {
        requireNonNull(propertyDispatcher, "PropertyDispatcher must not be null");
        requireNonNull(updateUi, "UpdateUI-Handler must not be null");
        requireNonNull(component, "Component must not be null");
        return new FieldBinding<>(label, (Field<?>)component, propertyDispatcher, updateUi);
    }

    @Override
    public String toString() {
        return "FieldDescriptor [fieldDefinition=" + fieldDefinition + ", fallbackPropertyName=" + getPmoPropertyName()
                + "]";
    }

    @Override
    public String getPmoPropertyName() {
        return pmoPropertyName;
    }
}
