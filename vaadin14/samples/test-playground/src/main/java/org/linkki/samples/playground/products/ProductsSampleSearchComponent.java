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

package org.linkki.samples.playground.products;

import org.linkki.core.binding.Binder;
import org.linkki.core.binding.BindingContext;
import org.linkki.core.vaadin.component.section.AbstractSection;
import org.linkki.framework.ui.component.Headline;

import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ProductsSampleSearchComponent extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public ProductsSampleSearchComponent() {

        setPadding(false);
        setSpacing(false);
        setHeightFull();

        Headline headline = new Headline();
        new Binder(headline, new ProductsSampleHeadlinePmo()).setupBindings(new BindingContext());
        add(headline);

        AbstractSection tableSection = ProductsSampleUtils.createSampleTableSection(50);
        tableSection.setPadding(true);
        addAndExpand(tableSection);
    }

}
