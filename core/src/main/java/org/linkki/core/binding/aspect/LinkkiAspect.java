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

/**
 * Annotation to add an {@link Aspect} to a linkki UI annotation using a
 * {@link LinkkiAspectDefinition}.
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Repeatable(LinkkiAspects.class)
@Target(ElementType.ANNOTATION_TYPE)
public @interface LinkkiAspect {

    Class<? extends LinkkiAspectDefinition> value();
}
