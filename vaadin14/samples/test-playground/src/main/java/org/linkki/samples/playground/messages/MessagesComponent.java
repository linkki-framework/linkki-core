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
package org.linkki.samples.playground.messages;

import org.linkki.core.binding.manager.BindingManager;
import org.linkki.core.binding.validation.ValidationDisplayState;
import org.linkki.core.ui.converters.LinkkiConverterRegistry;
import org.linkki.core.ui.creation.section.PmoBasedSectionFactory;
import org.linkki.core.vaadin.component.section.AbstractSection;
import org.linkki.framework.ui.component.MessageUiComponents;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.UI;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.server.VaadinSession;

public class MessagesComponent extends Div {

    private static final long serialVersionUID = 7735020388489427827L;
    private BindingManager bindingManager;
    private RegistrationValidationService validationService;

    public MessagesComponent() {
        UI.getCurrent().getPage().setTitle("linkki Samples :: Validation Messages");

        VaadinSession.getCurrent().setAttribute(LinkkiConverterRegistry.class, new LinkkiConverterRegistry());

        MessagesPanel messagesPanel = new MessagesPanel();

        User user = new User();
        RegistrationSectionPmo registrationPmo = new RegistrationSectionPmo(user, pmo -> handleRegistration(pmo));

        validationService = new RegistrationValidationService(registrationPmo);
        bindingManager = new RegistrationBindingManager(validationService, ml -> messagesPanel.updateMessages(ml));

        PmoBasedSectionFactory sectionFactory = new PmoBasedSectionFactory();
        AbstractSection section = sectionFactory.createSection(registrationPmo, bindingManager.getContext(getClass()));

        VerticalLayout layout = new VerticalLayout();
        layout.add(section);
        layout.setMargin(false);

        Component messageTable = MessageUiComponents.createMessageTable(validationService::getValidationMessages,
                                                                        bindingManager.getContext(getClass()));
        layout.add(messageTable);

        add(layout);
    }

    private void handleRegistration(RegistrationSectionPmo userPmo) {
        // validate required fields as well
        validationService.setValidationDisplayState(ValidationDisplayState.SHOW_ALL);

        // trigger validation and display errors in the MessagesPanel
        bindingManager.afterUpdateUi();

        if (!validationService.getValidationMessages().containsErrorMsg()) {
            Notification.show("Registration successful! Thank you for joining us!");
            userPmo.reset();
            validationService.setValidationDisplayState(ValidationDisplayState.HIDE_MANDATORY_FIELD_VALIDATIONS);
        }
    }
}
