/*******************************************************************************
 * Copyright (c) 2014 Faktor Zehn AG.
 *
 * Alle Rechte vorbehalten.
 *******************************************************************************/

package org.linkki.core.binding.dispatcher;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

import javax.annotation.Nonnull;

import org.linkki.core.binding.annotations.Bind;
import org.linkki.core.ui.section.annotations.AvailableValuesType;
import org.linkki.core.ui.section.annotations.BindingDescriptor;
import org.linkki.core.ui.section.annotations.EnabledType;
import org.linkki.core.ui.section.annotations.RequiredType;
import org.linkki.core.ui.section.annotations.UIComboBox;
import org.linkki.core.ui.section.annotations.UITextField;
import org.linkki.core.ui.section.annotations.VisibleType;

/**
 * Provides default values for enabled state, required state, visibility and available values
 * defined by annotations like {@link UITextField} and {@link UIComboBox}. If no annotation exists
 * for the given property, or if the type is dynamic, the wrapped dispatcher is accessed for a
 * value.
 *
 * @author widmaier
 */
public class BindingAnnotationDispatcher extends AbstractPropertyDispatcherDecorator {

    private final BindingDescriptor bindingDescriptor;

    /**
     * Creating a new {@link BindingAnnotationDispatcher} for an {@link BindingDescriptor}, passing
     * the wrapped dispatcher that should be decorated by this {@link BindingAnnotationDispatcher}
     *
     * @param wrappedDispatcher The decorated dispatcher
     * @param bindingDescriptor The descriptor for an element annotated with one of the UI
     *            annotations like {@link UITextField} or {@link UIComboBox} or the {@link Bind}
     *            annotation
     */
    public BindingAnnotationDispatcher(@Nonnull PropertyDispatcher wrappedDispatcher,
            @Nonnull BindingDescriptor bindingDescriptor) {
        super(wrappedDispatcher);
        this.bindingDescriptor = requireNonNull(bindingDescriptor, "BindingDescriptor must not be null");
    }

    @Override
    public boolean isEnabled() {
        EnabledType enabledType = bindingDescriptor.enabled();
        if (enabledType == null || enabledType == EnabledType.DYNAMIC) {
            return super.isEnabled();
        } else {
            return enabledType != EnabledType.DISABLED;
        }
    }

    @Override
    public boolean isVisible() {
        VisibleType visibleType = bindingDescriptor.visible();
        if (visibleType == null || visibleType == VisibleType.DYNAMIC) {
            return super.isVisible();
        } else {
            return visibleType != VisibleType.INVISIBLE;
        }
    }

    @Override
    public boolean isRequired() {
        RequiredType requiredType = bindingDescriptor.required();
        if (requiredType == null || requiredType == RequiredType.DYNAMIC) {
            return super.isRequired();
        } else if (requiredType == RequiredType.NOT_REQUIRED) {
            return false;
        } else if (requiredType == RequiredType.REQUIRED_IF_ENABLED) {
            return isEnabled();
        } else {
            return true;
        }
    }

    @Override
    @Nonnull
    public Collection<?> getAvailableValues() {
        AvailableValuesType type = Objects
                .requireNonNull(bindingDescriptor.availableValues(),
                                "AvailableValuesType null is not allowed, use AvailableValuesType.NO_VALUES to indicate that available values cannot be obtained");
        if (type == AvailableValuesType.NO_VALUES) {
            return Collections.emptySet();
        } else if (type == AvailableValuesType.DYNAMIC) {
            return super.getAvailableValues();
        } else {
            return getAvailableValuesByValueClass(type == AvailableValuesType.ENUM_VALUES_INCL_NULL);
        }
    }

    private Collection<?> getAvailableValuesByValueClass(boolean inclNull) {
        Class<?> type = getValueClass();
        Object[] values = type.getEnumConstants();
        if (values == null) {
            throw new IllegalStateException(
                    "Can't get list of values for Field " + getProperty() + ", ValueClass " + type);
        } else {
            return getCollection(values, inclNull);
        }
    }

    private Collection<?> getCollection(Object[] values, boolean inclNull) {
        if (inclNull) {
            ArrayList<Object> result = new ArrayList<>();
            result.add(null);
            result.addAll(Arrays.asList(values));
            return Collections.unmodifiableCollection(result);
        } else {
            return Arrays.asList(values);
        }
    }

    @Override
    public String toString() {
        return "BindingAnnotationDispatcher [wrappedDispatcher=" + getWrappedDispatcher() + ", bindingDescriptor="
                + bindingDescriptor + "]";
    }

}