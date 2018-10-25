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

package org.linkki.core.binding.aspect;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Repeatable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.linkki.core.binding.aspect.definition.LinkkiAspectDefinition;
import org.linkki.core.ui.section.SectionCreationContext;

/**
 * Annotation to add a {@link LinkkiAspectDefinition}. This is a meta-annotation that means it is
 * applied on another annotation which should be used in client code. This might be a UI field
 * annotation but could also be a new annotation that simply defines one aspect binding.
 * <p>
 * For example a tool tip aspect might be declared like this:
 * 
 * <pre>
 * &#64;Retention(RetentionPolicy.RUNTIME)
 * &#64;Target(value = { ElementType.FIELD, ElementType.METHOD })
 * &#64;LinkkiAspect(BindTooltipAspectDefinition.class)
 * public @interface BindTooltip {
 * 
 *     String text() default StringUtils.EMPTY;
 * 
 *     BindTooltipType tooltipType() default BindTooltipType.STATIC;
 * }
 * </pre>
 * 
 * Such annotations could be used within every PMO that is scanned by
 * {@link SectionCreationContext}. The {@link SectionCreationContext} will initialize the binding
 * for this aspect.
 * <p>
 * This annotation can be used multiple times to add different {@link LinkkiAspectDefinition
 * LinkkiAspectDefinitions} to the same annotation.
 * <p>
 * For further information see {@link Aspect} and {@link LinkkiAspectDefinition}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(LinkkiAspects.class)
@Target(ElementType.ANNOTATION_TYPE)
public @interface LinkkiAspect {

    Class<? extends LinkkiAspectDefinition> value();
}
