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

package org.linkki.core.ui.table.column.aspect;

import org.linkki.core.ui.section.annotations.UITableColumn;
import org.linkki.core.ui.section.annotations.UITableColumn.CollapseMode;
import org.linkki.core.ui.table.column.TableColumnWrapper;

public class ColumnCollapseAspectDefinition extends StaticColumnAspectDefinition<CollapseMode> {

    public static final String NAME = "collapsible";

    public ColumnCollapseAspectDefinition() {
        super(NAME, CollapseMode.NOT_COLLAPSIBLE, UITableColumn::collapsible, TableColumnWrapper::setCollapseMode);
    }
}
