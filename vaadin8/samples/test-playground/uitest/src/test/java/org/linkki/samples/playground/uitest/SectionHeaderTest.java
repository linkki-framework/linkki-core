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

package org.linkki.samples.playground.uitest;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.lessThan;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;

import com.vaadin.testbench.elements.ButtonElement;
import com.vaadin.testbench.elements.LabelElement;
import com.vaadin.testbench.elements.VerticalLayoutElement;

public class SectionHeaderTest extends AbstractUiTest {

    @BeforeEach
    public void setResolution() {
        // there appear to be issues running the test with a small resolution
        driver.manage().window().setSize(new Dimension(1920, 1080));
    }

    @Test
    public void testButtons() {
        VerticalLayoutElement section = $(VerticalLayoutElement.class).id("SectionHeaderPmo");
        ButtonElement leftButton = section.$(ButtonElement.class).id("headerButtonLeft");
        ButtonElement rightButton = section.$(ButtonElement.class).id("headerButtonRight");
        LabelElement label = section.$(LabelElement.class).id("label");

        testButtonPosition(leftButton.getLocation(), rightButton.getLocation());
        leftButton.click();
        assertThat(label.getText(), is("left"));
        rightButton.click();
        assertThat(label.getText(), is("right"));
    }

    private void testButtonPosition(Point leftButton, Point rightButton) {
        assertThat("Buttons " + leftButton + " and " + rightButton + "should have the same height",
                   leftButton.getY() == rightButton.getY(), is(true));
        assertThat("Button " + leftButton + " should be to the left of " + rightButton,
                   leftButton.getX(), is(lessThan(rightButton.getX())));
    }

}
