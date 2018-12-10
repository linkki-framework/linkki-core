/*
 * Copyright Faktor Zehn GmbH.
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
package org.linkki.doc;

import java.lang.annotation.Annotation;
import java.util.function.Consumer;

import org.linkki.core.binding.aspect.Aspect;
import org.linkki.core.binding.aspect.definition.LinkkiAspectDefinition;
import org.linkki.core.binding.dispatcher.PropertyDispatcher;
import org.linkki.core.ui.components.ComponentWrapper;
import org.linkki.doc.BindTooltip.TooltipType;
import org.linkki.util.handler.Handler;

//tag::BindTooltipAspectDefinition[]
public class BindTooltipAspectDefinition implements LinkkiAspectDefinition {

    public static final String NAME = "Tooltip";

    private BindTooltip annotation;

    @Override
    public void initialize(Annotation tooltipAnnotation) {
        this.annotation = (BindTooltip)tooltipAnnotation;
    }

    @Override
    public Handler createUiUpdater(PropertyDispatcher propertyDispatcher, ComponentWrapper componentWrapper) {
        Consumer<String> setter = componentWrapper::setTooltip;
        Aspect<String> aspect = createAspect();
        return () -> setter.accept(propertyDispatcher.pull(aspect));
    }

    public Aspect<String> createAspect() {
        if (annotation.tooltipType() == TooltipType.STATIC) {
            return Aspect.of(NAME, annotation.value());
        } else {
            return Aspect.of(NAME);
        }
    }
}
// end::BindTooltipAspectDefinition[]