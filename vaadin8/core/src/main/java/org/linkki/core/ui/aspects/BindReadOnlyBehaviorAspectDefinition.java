/*
 * Copyright Faktor Zehn GmbH.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in
 * compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is
 * distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or
 * implied. See the License for the specific language governing permissions and limitations under the
 * License.
 */

package org.linkki.core.ui.aspects;

import org.linkki.core.binding.descriptor.aspect.Aspect;
import org.linkki.core.binding.descriptor.aspect.LinkkiAspectDefinition;
import org.linkki.core.binding.dispatcher.PropertyDispatcher;
import org.linkki.core.binding.wrapper.ComponentWrapper;
import org.linkki.core.ui.aspects.types.ReadOnlyBehaviorType;
import org.linkki.util.handler.Handler;

import com.vaadin.data.HasValue;
import com.vaadin.ui.Component;

/**
 * Aspect Definition for a {@link Component Component's} behaviour in read-only mode.
 */
public class BindReadOnlyBehaviorAspectDefinition implements LinkkiAspectDefinition {

    private final ReadOnlyBehaviorType value;

    public BindReadOnlyBehaviorAspectDefinition(ReadOnlyBehaviorType value) {
        this.value = value;
    }

    @Override
    public Handler createUiUpdater(PropertyDispatcher propertyDispatcher, ComponentWrapper componentWrapper) {
        return () -> {
            setComponentStatus((Component)componentWrapper.getComponent(),
                               propertyDispatcher.isPushable(Aspect.of(VALUE_ASPECT_NAME)));
        };
    }

    @SuppressWarnings("deprecation")
    private void setComponentStatus(Component component, boolean writable) {
        switch (value) {
            case DISABLED:
                component.setEnabled(component.isEnabled() && writable);
                break;
            case INVSIBLE:
            case INVISIBLE:
                component.setVisible(component.isVisible() && writable);
                break;
            case WRITABLE:
                if (component instanceof HasValue) {
                    HasValue<?> hasValueComponent = (HasValue<?>)component;
                    hasValueComponent.setReadOnly(false);
                }
        }
    }
}