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
package org.linkki.samples.gettingstarted;

import org.linkki.core.binding.BindingContext;
import org.linkki.core.binding.dispatcher.PropertyBehaviorProvider;
import org.linkki.core.ui.section.AbstractSection;
import org.linkki.core.ui.section.PmoBasedSectionFactory;
import org.linkki.samples.gettingstarted.model.Report;
import org.linkki.samples.gettingstarted.pmo.ReportSectionPmo;
import org.linkki.util.handler.Handler;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.ui.UI;

@Theme(value = "valo")
public class GettingStartedUI extends UI {

    private static final long serialVersionUID = 1L;

    @Override
    protected void init(VaadinRequest request) {

        Page.getCurrent().setTitle("linkki :: Getting Started");

        PmoBasedSectionFactory sectionFactory = new PmoBasedSectionFactory();
        AbstractSection section = sectionFactory.createSection(new ReportSectionPmo(new Report()),
                                                               new BindingContext("report-context",
                                                                       PropertyBehaviorProvider.NO_BEHAVIOR_PROVIDER,
                                                                       Handler.NOP_HANDLER));

        setContent(section);
    }
}