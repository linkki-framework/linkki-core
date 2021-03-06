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

package org.linkki.core.vaadin.component;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import java.time.LocalDate;
import java.util.Locale;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.datepicker.DatePicker;
import com.vaadin.flow.component.textfield.TextArea;

public class ComponentFactoryTest {

    @BeforeEach
    public void setUp() {
        UI ui = new UI();
        ui.setLocale(Locale.ENGLISH);
        UI.setCurrent(ui);
    }

    @AfterEach
    public void tearDown() {
        UI.setCurrent(null);
    }

    @Test
    public void testNewDateField_BelowRange() {
        DatePicker dateField = ComponentFactory.newDateField();

        dateField.setValue(LocalDate.of(999, 1, 1));

        assertThat(dateField.isInvalid(), is(true));
    }

    @Test
    public void testNewDateField_InRangeLowerBound() {
        DatePicker dateField = ComponentFactory.newDateField();

        dateField.setValue(LocalDate.of(1000, 1, 1));

        assertThat(dateField.isInvalid(), is(false));
    }

    @Test
    public void testNewDateField_InRangeUpperBound() {
        DatePicker dateField = ComponentFactory.newDateField();

        dateField.setValue(LocalDate.of(9999, 12, 31));

        assertThat(dateField.isInvalid(), is(false));
    }

    @Test
    public void testNewDateField_AboveRange() {
        DatePicker dateField = ComponentFactory.newDateField();

        dateField.setValue(LocalDate.of(10000, 1, 1));

        assertThat(dateField.isInvalid(), is(true));
    }

    @Test
    public void testNewTextArea() {
        TextArea textArea = ComponentFactory.newTextArea(250, "500px", "100px");

        assertThat(textArea.getMaxLength(), is(250));
        assertThat(textArea.getWidth(), is("500px"));
        assertThat(textArea.getHeight(), is("100px"));
    }

    @Test
    public void testNewTextArea_EmptyHeight() {
        TextArea textArea = ComponentFactory.newTextArea(250, "500px", "");

        assertThat(textArea.getHeight(), is(nullValue()));
    }


}
