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

package org.linkki.core.ui.section;

import java.util.Arrays;

import org.linkki.core.ButtonPmo;
import org.linkki.core.binding.BindingContext;
import org.linkki.core.binding.aspect.definition.TestComponentClickAspectDefinition;
import org.linkki.core.binding.property.BoundProperty;
import org.linkki.core.ui.TestComponentWrapper;
import org.linkki.core.ui.TestUiComponent;
import org.linkki.core.ui.TestUiLayoutComponent;
import org.linkki.core.ui.UiElementCreator;

public class TestSectionBuilder {

    private TestSectionBuilder() {
        // no instantiation
    }

    public static TestUiLayoutComponent createSection(Object pmo, BindingContext bindingContext) {
        TestUiLayoutComponent section = new TestUiLayoutComponent();
        section.setId(Sections.getSectionId(pmo));
        Sections.getEditButtonPmo(pmo).ifPresent(buttonPmo -> createButton(buttonPmo, section, bindingContext));
        UiElementCreator.createUiElements(pmo, bindingContext, TestComponentWrapper::new)
                .map(TestComponentWrapper::getComponent).forEach(section::addChild);
        return section;
    }

    private static void createButton(ButtonPmo buttonPmo,
            TestUiLayoutComponent section,
            BindingContext bindingContext) {
        TestUiComponent button = new TestUiComponent();
        section.addChild(button);
        button.setId("editButton");
        bindingContext.bind(buttonPmo, BoundProperty.of(""),
                            Arrays.asList(new TestComponentClickAspectDefinition()),
                            new TestComponentWrapper(button));
    }
}