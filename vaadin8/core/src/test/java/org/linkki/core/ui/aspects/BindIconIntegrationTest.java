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

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.util.List;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.linkki.core.binding.BindingContext;
import org.linkki.core.binding.LinkkiBindingException;
import org.linkki.core.defaults.ui.aspects.types.IconType;
import org.linkki.core.ui.aspects.annotation.BindIcon;
import org.linkki.core.ui.element.annotation.UITextField;
import org.linkki.core.ui.wrapper.LabelComponentWrapper;
import org.linkki.core.uicreation.UiCreator;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Component;

public class BindIconIntegrationTest {

    @Test
    public void testCreateAspect_Static() {
        BindingContext bindingContext = new BindingContext();
        List<LabelComponentWrapper> uiElements = UiCreator
                .createUiElements(new TestPmoWithStaticIcon(),
                                  bindingContext, c -> new LabelComponentWrapper((Component)c))
                .collect(Collectors.toList());

        assertThat(uiElements.get(0).getComponent().getIcon(), is(VaadinIcons.ALARM));
    }

    @Test
    public void testCreateAspect_Auto() {
        BindingContext bindingContext = new BindingContext();
        TestPmoWithAutoIcon pmo = new TestPmoWithAutoIcon(VaadinIcons.ABACUS);
        List<LabelComponentWrapper> uiElements = UiCreator
                .createUiElements(pmo, bindingContext, c -> new LabelComponentWrapper((Component)c))
                .collect(Collectors.toList());
        assertThat(uiElements.get(0).getComponent().getIcon(), is(VaadinIcons.ABACUS));

        pmo.setIcon(null);
        bindingContext.modelChanged();
        assertThat(uiElements.get(0).getComponent().getIcon(), is(nullValue()));
    }

    @Test
    public void testCreateAspect_Dynamic() {
        BindingContext bindingContext = new BindingContext();
        TestPmoWithDynamicIcon pmo = new TestPmoWithDynamicIcon(VaadinIcons.ABACUS);
        List<LabelComponentWrapper> uiElements = UiCreator
                .createUiElements(pmo, bindingContext, c -> new LabelComponentWrapper((Component)c))
                .collect(Collectors.toList());
        assertThat(uiElements.get(0).getComponent().getIcon(), is(VaadinIcons.ABACUS));

        pmo.setIcon(null);
        bindingContext.modelChanged();
        assertThat(uiElements.get(0).getComponent().getIcon(), is(nullValue()));
    }

    @Test
    public void testCreateAspect_Dynamic_MethodMissing() {
        BindingContext bindingContext = new BindingContext();
        TestPmoMissingDynamicMethod pmo = new TestPmoMissingDynamicMethod();

        Assertions.assertThrows(LinkkiBindingException.class, () -> {
            UiCreator.createUiElements(pmo, bindingContext, c -> new LabelComponentWrapper((Component)c))
                    .collect(Collectors.toList());
        });

    }

    public static class TestPmoWithStaticIcon {

        @BindIcon(VaadinIcons.ALARM)
        @UITextField(label = "static icon", position = 0)
        public String getPropertyWithStaticIcon() {
            return "";
        }
    }

    public static class TestPmoWithAutoIcon {

        private VaadinIcons icon;

        public TestPmoWithAutoIcon(VaadinIcons icon) {
            this.icon = icon;
        }

        public void setIcon(VaadinIcons icon) {
            this.icon = icon;
        }

        @BindIcon
        @UITextField(label = "dynamic icon", position = 0)
        public String getProperty() {
            return "";
        }

        public VaadinIcons getPropertyIcon() {
            return icon;
        }
    }

    public static class TestPmoWithDynamicIcon {

        private VaadinIcons icon;

        public TestPmoWithDynamicIcon(VaadinIcons icon) {
            this.icon = icon;
        }

        public void setIcon(VaadinIcons icon) {
            this.icon = icon;
        }

        @BindIcon(iconType = IconType.DYNAMIC)
        @UITextField(label = "dynamic icon", position = 0)
        public String getProperty() {
            return "";
        }

        public VaadinIcons getPropertyIcon() {
            return icon;
        }
    }

    public static class TestPmoMissingDynamicMethod {

        @BindIcon(iconType = IconType.DYNAMIC)
        @UITextField(label = "missing dynamic method", position = 0)
        public String getProperty() {
            return "";
        }
    }
}
