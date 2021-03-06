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

import org.linkki.framework.ui.component.Headline;

import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

public class ProductsSampleOverviewComponent extends VerticalLayout {

    private static final long serialVersionUID = 1L;

    public ProductsSampleOverviewComponent() {

        setPadding(false); // headline border should reach sidebar
        setSpacing(false); // content should scroll underneath headline border
        setHeightFull();

        add(new Headline("Overview"));

        VerticalLayout content = new VerticalLayout();
        content.setSizeFull();
        content.setPadding(true);
        content.getStyle().set("overflow", "auto");
        add(content);

        content.add(ProductsSampleUtils.createSampleSectionVertical());
        content.add(ProductsSampleUtils.createSampleSectionHorizontal());

        content.add(new HorizontalLayout(
                ProductsSampleUtils.createSampleSectionVertical(), //
                ProductsSampleUtils.createSampleSectionVertical()));


        content.add(ProductsSampleUtils.createSampleTableSection(100));
    }

}
