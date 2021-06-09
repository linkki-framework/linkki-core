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

package org.linkki.samples.playground.uitestnew.ts003;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.linkki.samples.playground.pageobjects.LinkkiSectionElement;
import org.linkki.samples.playground.pageobjects.TestCaseComponentElement;
import org.linkki.samples.playground.uitest.extensions.DriverExtension.Configuration;
import org.linkki.samples.playground.uitestnew.BaseUITest;

import com.vaadin.flow.component.button.testbench.ButtonElement;
import com.vaadin.flow.component.html.testbench.LabelElement;
import com.vaadin.flow.component.textfield.testbench.TextFieldElement;
import com.vaadin.testbench.TestBenchElement;

class TC001LocalizationTest {

    @Nested
    @Configuration(locale = "de")
    class TC001LocalizationTestDe extends BaseTC001LocalizationTest {
        TC001LocalizationTestDe() {
            super("Deutsch", "12.345,67");
        }
    }

    @Nested
    @Configuration(locale = "en")
    class TC001LocalizationTestEn extends BaseTC001LocalizationTest {
        TC001LocalizationTestEn() {
            super("English", "12,345.67");
        }
    }

    private abstract class BaseTC001LocalizationTest extends BaseUITest {

        protected TestCaseComponentElement testCaseSection;
        protected LinkkiSectionElement section;
        protected List<TestBenchElement> allFormItems;

        private final String localizedPrefix;
        private final String localizedNumber;

        BaseTC001LocalizationTest(String localizedPrefix, String localizedNumber) {
            this.localizedPrefix = localizedPrefix;
            this.localizedNumber = localizedNumber;
        }

        @BeforeAll
        void setup() {
            testCaseSection = goToTestCase("TS003", "TC001");

            section = testCaseSection.getContentWrapper().$(LinkkiSectionElement.class).first();
            allFormItems = section.$("vaadin-form-item").all();
        }

        @Test
        void testLocalizedLabelsOfAllElements() {
            assertThat(allFormItems.stream()//
                    .map(e -> e.$(LabelElement.class).first().getText()))
                            .allSatisfy(e -> StringUtils.contains(e, localizedPrefix));
        }

        @Test
        void testLocalizedSectionTitle() {
            assertThat(section.getCaption().getTitle().getText())
                    .contains(localizedPrefix);
        }

        @Test
        void testLocalizedButtonCaption() {
            assertThat(section.$(ButtonElement.class).id("localizedButtonCaption").getText())
                    .contains(localizedPrefix);
        }

        @Test
        void testLocalizedFormatOfDoubleField() {
            assertThat(section.$(TextFieldElement.class).id("localizedDoubleField").getValue())
                    .isEqualTo(localizedNumber);
        }

        @Test
        void testLocalizedFormatOfDecimalField() {
            assertThat(section.$(TextFieldElement.class).id("localizedDecimalField").getValue())
                    .isEqualTo(localizedNumber);
        }

    }

}
