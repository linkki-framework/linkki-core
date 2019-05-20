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
package org.linkki.core.ui.section.annotations;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;
import org.linkki.core.binding.BindingContext;
import org.linkki.core.defaults.ui.aspects.types.AvailableValuesType;
import org.linkki.core.defaults.ui.aspects.types.EnabledType;
import org.linkki.core.defaults.ui.aspects.types.RequiredType;
import org.linkki.core.defaults.ui.aspects.types.VisibleType;
import org.linkki.core.pmo.ModelObject;

import com.vaadin.ui.GridLayout;
import com.vaadin.ui.OptionGroup;
import com.vaadin.ui.TextField;

import edu.umd.cs.findbugs.annotations.CheckForNull;

public class UiCustomFieldTest {

    private TestModelObject modelObject = new TestModelObject();

    private TestPmo pmo = new TestPmo(modelObject);

    private BindingContext bindingContext = new BindingContext();

    private OptionGroup createCustomField() {
        return (OptionGroup)TestUiUtil.createFirstComponentOf(pmo, bindingContext);
    }

    @Test
    public void testAvailableValues() {
        OptionGroup optionGroup = createCustomField();

        assertThat(optionGroup.getItemIds(), contains(pmo.availableValues.toArray()));
    }

    @Test
    public void testAvailableValues_NotApplicable() {
        GridLayout section = TestUiUtil.createSectionWith(pmo);
        TextField textField = TestUiUtil.getComponentAt(section, 1);

        // the real test is that this text field could be created, just check the value to check
        // anything
        assertThat(textField.getValue(), is("test"));
    }

    @Test
    public void testGetValue() {
        OptionGroup optionGroup = createCustomField();

        assertThat(optionGroup.getValue(), is(modelObject.getProperty()));

        TestValue newValue = new TestValue("newValue");
        modelObject.property = newValue;
        bindingContext.modelChanged();

        assertThat(optionGroup.getValue(), is(newValue));
    }

    @Test
    public void testSetValue() {
        OptionGroup optionGroup = createCustomField();

        TestValue newValue = new TestValue("b");
        optionGroup.setValue(newValue);

        assertThat(modelObject.getProperty(), is(newValue));
    }

    @Test
    public void testEnabled() {
        pmo.enabled = false;
        OptionGroup optionGroup = createCustomField();

        assertThat(optionGroup.isEnabled(), is(false));

        pmo.enabled = true;
        bindingContext.modelChanged();

        assertThat(optionGroup.isEnabled(), is(true));
    }

    @Test
    public void testVisible() {
        pmo.visible = false;
        OptionGroup optionGroup = createCustomField();

        assertThat(optionGroup.isVisible(), is(false));

        pmo.visible = true;
        bindingContext.modelChanged();

        assertThat(optionGroup.isVisible(), is(true));
    }

    @Test
    public void testRequired() {
        pmo.required = false;
        OptionGroup optionGroup = createCustomField();

        assertThat(optionGroup.isRequired(), is(false));

        pmo.required = true;
        bindingContext.modelChanged();

        assertThat(optionGroup.isRequired(), is(true));
    }

    public static class TestModelObject {

        private TestValue property = new TestValue("abc123");

        public TestValue getProperty() {
            return property;
        }

        public void setProperty(TestValue value) {
            this.property = value;
        }

    }

    @UISection
    public static class TestPmo {

        private final TestModelObject modelObject;

        private boolean enabled = false;

        private boolean visible = false;

        private boolean required = false;

        private List<TestValue> availableValues = Arrays.asList(new TestValue("a"), new TestValue("b"),
                                                                new TestValue("c"));

        public TestPmo(TestModelObject modelObject) {
            super();
            this.modelObject = modelObject;
        }

        @UICustomField(position = 7, label = "TheLabel", enabled = EnabledType.DYNAMIC, modelAttribute = "property", visible = VisibleType.DYNAMIC, required = RequiredType.DYNAMIC, content = AvailableValuesType.DYNAMIC, uiControl = OptionGroup.class)
        public void property() {
            // data binding
        }

        @UICustomField(position = 10, label = "", uiControl = TextField.class)
        public String getNoSelectField() {
            return "test";
        }

        @ModelObject
        public TestModelObject getModelObject() {
            return modelObject;
        }

        public boolean isPropertyEnabled() {
            return enabled;
        }

        public boolean isPropertyVisible() {
            return visible;
        }

        public boolean isPropertyRequired() {
            return required;
        }

        public List<TestValue> getPropertyAvailableValues() {
            return availableValues;
        }

    }

    public static class TestValue {

        @CheckForNull
        private String value;

        public TestValue(String value) {
            this.value = value;
        }

        @CheckForNull
        public String getId() {
            return value;
        }

        @CheckForNull
        public String getName() {
            return value;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + ((value != null) ? value.hashCode() : 0);
            return result;
        }

        @Override
        public boolean equals(@CheckForNull Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            TestValue other = (TestValue)obj;
            if (value != null) {
                if (!value.equals(other.value)) {
                    return false;
                }
            } else {
                if (other.value != null) {
                    return false;
                }
            }
            return true;
        }

    }
}