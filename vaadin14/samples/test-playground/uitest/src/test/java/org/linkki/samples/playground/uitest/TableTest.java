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
import static org.junit.Assert.assertThat;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.linkki.samples.playground.table.PlaygroundTablePmo;
import org.linkki.samples.playground.ui.PlaygroundApplicationView;

import com.vaadin.flow.component.grid.testbench.GridElement;

public class TableTest extends AbstractUiTest {

    @BeforeEach
    public void openTablesTab() {
        openTab(PlaygroundApplicationView.TABLES_TAB_ID);
    }

    @Test
    public void testLabelAsTableHeader() {
        GridElement table = $(GridElement.class).id(PlaygroundTablePmo.class.getSimpleName() + "_table");

        assertThat(table.getHeaderCellContent(0, 0).getText(), is("Editable"));
    }

    @Test
    public void testColumnWidthAndFlexGrowWithoutUIColumn() {
        GridElement table = $(GridElement.class).id(PlaygroundTablePmo.class.getSimpleName() + "_table");
        assertThat(table.getCell(0, 0).getCssValue("flexGrow"), is("0"));
    }

    @Test
    public void testFlexGrow() {
        GridElement table = $(GridElement.class).id(PlaygroundTablePmo.class.getSimpleName() + "_table");
        assertThat(table.getCell("Simple label to select the row").getCssValue("flexGrow"), is("10"));
    }

    @Test
    public void testFixedColumnWidth() {
        GridElement table = $(GridElement.class).id(PlaygroundTablePmo.class.getSimpleName() + "_table");
        assertThat(table.getCell(0, 5).getCssValue("width"), is("160px"));
    }

    @Test
    public void testWidthAndFlexGrow() {
        GridElement table = $(GridElement.class).id(PlaygroundTablePmo.class.getSimpleName() + "_table");

        String cssWidth = table.getCell(0, 7).getCssValue("width");
        double intWidth = Double.valueOf(cssWidth.substring(0, cssWidth.length() - 2));

        assertThat(intWidth, is(Matchers.greaterThanOrEqualTo(300.0)));
    }
}
