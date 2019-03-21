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

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;
import org.linkki.core.defaults.uielement.aspects.annotations.BindTooltip;
import org.linkki.core.defaults.uielement.aspects.types.EnabledType;
import org.linkki.core.defaults.uielement.aspects.types.RequiredType;
import org.linkki.core.defaults.uielement.aspects.types.TooltipType;
import org.linkki.core.defaults.uielement.aspects.types.VisibleType;
import org.linkki.core.ui.section.annotations.UITextFieldIntegrationTest.TextFieldTestPmo;

import com.vaadin.server.Sizeable.Unit;
import com.vaadin.ui.TextField;

import edu.umd.cs.findbugs.annotations.CheckForNull;

public class UITextFieldIntegrationTest extends FieldAnnotationIntegrationTest<TextField, TextFieldTestPmo> {

    private static final String WIDTH = "42em";
    private static final int MAX_LENGTH = 8;

    public UITextFieldIntegrationTest() {
        super(TestModelObjectWithString::new, TextFieldTestPmo::new);
    }

    @Test
    public void testTextVieldValue() {
        TestModelObjectWithString modelObject = new TestModelObjectWithString();
        TextField textField = createFirstComponent(modelObject);

        assertThat(textField.getMaxLength(), is(MAX_LENGTH));
        assertThat(textField.getWidth(), is(42.0f));
        assertThat(textField.getWidthUnits(), is(Unit.EM));
        assertThat(textField.getValue(), is(""));

        TestUiUtil.setUserOriginatedValue(textField, "asdf");
        assertThat(modelObject.getValue(), is("asdf"));

        modelObject.setValue("fdsa");
        getBindingContext().modelChanged();
        assertThat(textField.getValue(), is("fdsa"));

        TestUiUtil.setUserOriginatedValue(textField, null);
        assertThat(modelObject.getValue(), is(nullValue()));
    }

    @Test
    @Override
    public void testNullInputIfRequired() {
        TextField textField = getDynamicComponent();
        getDefaultPmo().setRequired(true);
        modelChanged();
        assertThat(textField.isRequiredIndicatorVisible(), is(true));

        TestUiUtil.setUserOriginatedValue(textField, "something");
        assertThat(getDefaultModelObject().getValue(), is("something"));

        TestUiUtil.setUserOriginatedValue(textField, null);
        assertThat(getDefaultModelObject().getValue(), is(nullValue()));
    }

    @Override
    protected TestModelObjectWithString getDefaultModelObject() {
        return (TestModelObjectWithString)super.getDefaultModelObject();
    }

    @UISection
    protected static class TextFieldTestPmo extends AnnotationTestPmo {

        public TextFieldTestPmo(Object modelObject) {
            super(modelObject);
        }

        @Override
        @BindTooltip(tooltipType = TooltipType.DYNAMIC)
        @UITextField(position = 1, label = "", enabled = EnabledType.DYNAMIC, required = RequiredType.DYNAMIC, visible = VisibleType.DYNAMIC, width = WIDTH, maxLength = MAX_LENGTH)
        public void value() {
            // model binding
        }

        @Override
        @BindTooltip(TEST_TOOLTIP)
        @UITextField(position = 2, label = TEST_LABEL, enabled = EnabledType.DISABLED, required = RequiredType.REQUIRED, visible = VisibleType.INVISIBLE)
        public void staticValue() {
            // model binding
        }

    }

    protected static class TestModelObjectWithString extends TestModelObject<String> {

        @CheckForNull
        private String value = null;

        @CheckForNull
        @Override
        public String getValue() {
            return value;
        }

        @Override
        public void setValue(@CheckForNull String value) {
            this.value = value;
        }
    }
}
