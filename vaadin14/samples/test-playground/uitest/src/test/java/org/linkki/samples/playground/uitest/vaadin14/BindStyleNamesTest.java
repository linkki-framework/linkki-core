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

package org.linkki.samples.playground.uitest.vaadin14;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.is;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.linkki.samples.playground.dynamicannotations.BindStyleNamesComponentsPmo;
import org.linkki.samples.playground.ui.PlaygroundApplicationView;
import org.linkki.samples.playground.uitest.AbstractUiTest;

import com.vaadin.flow.component.textfield.testbench.TextFieldElement;

public class BindStyleNamesTest extends AbstractUiTest {

    @BeforeEach
    public void setup() {
        openTab(PlaygroundApplicationView.DYNAMIC_ASPECT_TAB_ID);
    }

    @Test
    public void testBindStyleNames_Dynamic_EmptyStyleName() {
        TextFieldElement textField = getSection(BindStyleNamesComponentsPmo.class).$(TextFieldElement.class)
                .first();
        textField.setValue(" ");
        textField.sendKeys("\t");

        assertThat(textField.getClassNames(), is(empty()));
    }

    @Test
    public void testBindStyleNames_Dynamic_SingleStyleName() {
        TextFieldElement textField = getSection(BindStyleNamesComponentsPmo.class).$(TextFieldElement.class)
                .first();
        textField.setValue("aCustomStyleName");
        textField.sendKeys("\t");

        assertThat(textField.getClassNames(), contains("aCustomStyleName"));
    }

    @Test
    public void testBindStyleNames_Dynamic_MultipleStyleNames() {
        TextFieldElement textField = getSection(BindStyleNamesComponentsPmo.class).$(TextFieldElement.class)
                .first();
        textField.setValue("aCustomStyleName anotherOne");
        textField.sendKeys("\t");

        assertThat(textField.getClassNames(), contains("aCustomStyleName", "anotherOne"));
    }

}