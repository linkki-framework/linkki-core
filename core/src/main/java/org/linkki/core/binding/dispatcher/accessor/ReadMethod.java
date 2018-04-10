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
package org.linkki.core.binding.dispatcher.accessor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.linkki.core.binding.LinkkiBindingException;

/**
 * Wrapper for a {@link Method}. {@link #canRead()} can safely be accessed even if no read method
 * exists. {@link #readValue(Object)} will access the getter via reflection.
 */
public class ReadMethod extends AbstractMethod {

    public ReadMethod(PropertyAccessDescriptor descriptor) {
        super(descriptor, descriptor.getReflectionReadMethod());
    }

    public boolean canRead() {
        return hasMethod();
    }

    /**
     * Reads and returns the value by accessing the respective read method.
     *
     * @throws IllegalStateException if no read method exists
     * @throws RuntimeException if an error occurs while accessing the read method
     */
    public Object readValue(Object boundObject) {
        if (canRead()) {
            return readValueWithExceptionHandling(boundObject);
        } else {
            throw new IllegalStateException(
                    "Cannot find getter method for " + boundObject.getClass().getName() + "#" + getPropertyName());
        }
    }

    private Object readValueWithExceptionHandling(Object boundObject) {
        try {
            return invokeMethod(boundObject);
        } catch (IllegalAccessException e) {
            throw new LinkkiBindingException(
                    "Cannot access method " + getMethodWithExceptionHandling(), e);
        } catch (IllegalArgumentException | InvocationTargetException e) {
            throw new LinkkiBindingException(
                    "Cannot invoke read method " + getMethodWithExceptionHandling(), e);
        }
    }

    private Object invokeMethod(Object boundObject) throws IllegalAccessException, InvocationTargetException {
        return getMethodWithExceptionHandling().invoke(boundObject);
    }

    public Class<?> getReturnType() {
        return getMethodWithExceptionHandling().getReturnType();
    }

}
