/*
 * Copyright Faktor Zehn AG.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.linkki.core.ui.section.annotations;

import static java.util.Objects.requireNonNull;

import java.util.List;

import javax.annotation.Nullable;

import org.linkki.core.binding.ElementBinding;
import org.linkki.core.binding.LabelBinding;
import org.linkki.core.binding.aspect.LinkkiAspectDefinition;
import org.linkki.core.binding.dispatcher.PropertyDispatcher;
import org.linkki.util.handler.Handler;

import com.vaadin.ui.Component;
import com.vaadin.ui.Label;

public class LabelDescriptor extends AbstractFieldDescriptor {


    public LabelDescriptor(UIFieldDefinition labelDefinition, String pmoPropertyName, Class<?> pmoClass,
            List<LinkkiAspectDefinition> aspectDefs) {
        super(labelDefinition, pmoPropertyName, pmoClass, aspectDefs);
    }

    @Override
    public ElementBinding createBinding(PropertyDispatcher propertyDispatcher,
            Handler modelChanged,
            Component component,
            @Nullable Label label) {
        requireNonNull(propertyDispatcher, "propertyDispatcher must not be null");
        requireNonNull(modelChanged, "updateUi must not be null");
        requireNonNull(component, "component must not be null");
        return new LabelBinding(label, (Label)component, propertyDispatcher, getAspectDefinitions());
    }

}
