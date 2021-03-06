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

package org.linkki.framework.ui.component;

import java.util.function.Supplier;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;
import org.linkki.core.binding.BindingContext;
import org.linkki.core.binding.validation.message.Message;
import org.linkki.core.binding.validation.message.MessageList;
import org.linkki.core.binding.validation.message.ObjectProperty;
import org.linkki.core.binding.validation.message.Severity;
import org.linkki.core.defaults.style.LinkkiTheme;
import org.linkki.core.ui.creation.table.GridComponentCreator;
import org.linkki.framework.ui.LinkkiApplicationTheme;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.dependency.CssImport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.grid.Grid.SelectionMode;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;

/**
 * Utility class for the creation of different components to display {@link Message Messages} and
 * {@link MessageList MessageLists}.
 */
@CssImport("./styles/linkki-messages.css")
public final class MessageUiComponents {

    private MessageUiComponents() {
        // prevent instantiation
    }

    /**
     * Returns the CSS style name for the message's {@link Severity}.
     * 
     * @implNote the style names consist of the {@link LinkkiApplicationTheme#MESSAGE_PREFIX} and the
     *           {@link Severity#name() Severity's name}, for example {@code linkki-message-error}.
     */
    public static String getStyle(Severity severity) {
        return LinkkiApplicationTheme.MESSAGE_PREFIX + severity.name().toLowerCase();
    }

    /**
     * Returns an icon for the message's {@link Severity}.
     */
    public static VaadinIcon getIcon(Severity severity) {
        switch (severity) {
            case ERROR:
                return VaadinIcon.EXCLAMATION_CIRCLE;
            case WARNING:
                return VaadinIcon.WARNING;
            default:
                return VaadinIcon.INFO_CIRCLE;
        }
    }

    /**
     * Concatenates the {@link Message#getInvalidObjectProperties() message's invalid object
     * properties}.
     * 
     * @deprecated The invalid object property string was a concatenation of simple class name and
     *             property name. That was a very technical view of an invalid object property and
     *             should not be used for describing a property for the end user. If you need this
     *             representation consider to write your own utility method for this conversion.
     */
    @Deprecated
    public static String getInvalidObjectPropertiesAsString(Message message) {
        String text = message.getInvalidObjectProperties().stream()
                .map(MessageUiComponents::toString)
                .collect(Collectors.joining(", "));
        return text;
    }

    private static String toString(ObjectProperty op) {
        String simpleName = op.getObject().getClass().getSimpleName();
        if (StringUtils.isEmpty(op.getProperty())) {
            return simpleName;
        } else {
            return simpleName + ": " + op.getProperty();
        }
    }

    /**
     * Creates a form layout with a {@link Div} that contains the message's text and an icon
     * representing its {@link Severity}. The label can be styled with
     * {@link LinkkiApplicationTheme#MESSAGE_LABEL} and a {@link #getStyle(Severity) style derived from
     * the severity}.
     */
    public static Component createMessageComponent(Message message) {
        Span messageLabel = new Span();
        messageLabel.setWidthFull();
        Severity severity = message.getSeverity();
        messageLabel.add(getIcon(severity).create());
        messageLabel.add(message.getText());
        messageLabel.addClassNames(getStyle(severity), LinkkiApplicationTheme.MESSAGE_LABEL, LinkkiTheme.HAS_ICON);
        return messageLabel;
    }

    /**
     * Creates a table displaying all messages with the given {@link BindingContext}.
     */
    public static Component createMessageTable(Supplier<MessageList> messages, BindingContext bindingContext) {
        Grid<MessageRowPmo> messagesGrid = GridComponentCreator
                .createGrid(new MessageTablePmo(messages), bindingContext);
        messagesGrid.addClassName(LinkkiApplicationTheme.MESSAGE_TABLE);
        messagesGrid.setWidthFull();
        messagesGrid.setSelectionMode(SelectionMode.NONE);
        return messagesGrid;
    }

    /**
     * Creates a table displaying all messages under the given tile with the given
     * {@link BindingContext}.
     */
    public static Component createMessageTable(String title,
            Supplier<MessageList> messages,
            BindingContext bindingContext) {
        Component messageTable = createMessageTable(messages, bindingContext);
        VerticalLayout content = new VerticalLayout(new Label(title), messageTable);
        content.setPadding(false);
        return content;
    }
}
