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

package org.linkki.core.defaults.columnbased.pmo;

import org.linkki.core.binding.TestEnum;
import org.linkki.core.binding.TestPmo;
import org.linkki.core.defaults.section.annotations.TestUIField;

public class TestRowPmo extends TestPmo {

    @TestUIField(position = 10, label = "val")
    @Override
    public String getValue() {
        return super.getValue();
    }

    @TestUIField(position = 20, label = "enum")
    @Override
    public TestEnum getEnumValue() {
        return super.getEnumValue();
    }


}