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

package org.linkki.samples.playground.uitestnew.ts.aspects;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.linkki.samples.playground.ts.aspects.BindStyleNamesPmo;
import org.linkki.samples.playground.uitestnew.BaseUITest;

import com.vaadin.flow.component.orderedlayout.testbench.VerticalLayoutElement;

public class TC001BindStyleNamesTest extends BaseUITest {

    @BeforeEach
    void setUp() {
        goToTestCase("TS008", "TC001");
    }

    @Test
    void testLabel_StyleNames() {
        VerticalLayoutElement label = $(VerticalLayoutElement.class).id(BindStyleNamesPmo.class.getSimpleName());
        assertThat(label.getClassNames()).contains("style1", "style2", "style3");
    }

}
