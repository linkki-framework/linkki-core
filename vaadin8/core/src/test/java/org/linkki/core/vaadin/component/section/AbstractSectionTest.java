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
package org.linkki.core.vaadin.component.section;

import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.linkki.core.vaadin.component.ComponentFactory;

import com.vaadin.icons.VaadinIcons;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;

public class AbstractSectionTest {

    static class TestSection extends AbstractSection {

        public TestSection(String caption, boolean closeable, Optional<Button> button) {
            super(caption, closeable, button);
        }

        private static final long serialVersionUID = 1L;


        @SuppressFBWarnings("NP_NONNULL_RETURN_VIOLATION")
        @Override
        public Component getSectionContent() {
            return null;
        }

    }

    @Test
    public void testAddHeaderButton_HeaderIsCreated() {
        AbstractSection section = new TestSection("", true, Optional.empty());

        assertThat(section.getComponentCount(), is(0));

        Button headerButton1 = ComponentFactory.newButton();
        section.addHeaderButton(headerButton1);

        HorizontalLayout sectionHeader = (HorizontalLayout)section.getComponent(0);

        assertThat(sectionHeader, is(notNullValue()));
        assertThat(sectionHeader.getComponentCount(), is(3)); // button, close button, line
        assertThat(sectionHeader.getComponent(1), is(instanceOf(Button.class)));
        Button closeButton = (Button)sectionHeader.getComponent(1);
        assertThat(closeButton.getIcon(), is(VaadinIcons.ANGLE_DOWN));
        assertThat(sectionHeader.getComponentCount(), is(3));
        assertThat(sectionHeader.getComponent(1), is(closeButton));
        Button button = (Button)sectionHeader.getComponent(0);
        assertThat(button, is(headerButton1));
    }

    @Test
    public void testAddHeaderButton_HeaderButtonIsAddedBeforeCloseButton() {
        AbstractSection section = new TestSection("caption", true, Optional.empty());
        HorizontalLayout sectionHeader = (HorizontalLayout)section.getComponent(0);

        assertThat(sectionHeader, is(notNullValue()));
        assertThat(sectionHeader.getComponentCount(), is(3)); // label, close button, line
        assertThat(sectionHeader.getComponent(1), is(instanceOf(Button.class)));
        Button closeButton = (Button)sectionHeader.getComponent(1);
        assertThat(closeButton.getIcon(), is(VaadinIcons.ANGLE_DOWN));

        Button headerButton1 = ComponentFactory.newButton();
        section.addHeaderButton(headerButton1);
        assertThat(sectionHeader.getComponentCount(), is(4));
        assertThat(sectionHeader.getComponent(2), is(closeButton));
        Button button1 = (Button)sectionHeader.getComponent(1);
        assertThat(button1, is(headerButton1));

        Button headerButton2 = ComponentFactory.newButton();
        section.addHeaderButton(headerButton2);
        assertThat(sectionHeader.getComponentCount(), is(5));
        assertThat(sectionHeader.getComponent(3), is(closeButton));
        Button button2 = (Button)sectionHeader.getComponent(2);
        assertThat(button2, is(headerButton2));
    }

    @Test
    public void testAddHeaderButton_HeaderButtonIsAddedAtEndIfCloseButtonIsMissing() {
        AbstractSection section = new TestSection("caption", false, Optional.empty());
        HorizontalLayout sectionHeader = (HorizontalLayout)section.getComponent(0);

        assertThat(sectionHeader, is(notNullValue()));
        assertThat(sectionHeader.getComponentCount(), is(2)); // label, line

        Button headerButton1 = ComponentFactory.newButton();
        section.addHeaderButton(headerButton1);
        assertThat(sectionHeader.getComponentCount(), is(3));
        Button button1 = (Button)sectionHeader.getComponent(1);
        assertThat(button1, is(headerButton1));

        Button headerButton2 = ComponentFactory.newButton();
        section.addHeaderButton(headerButton2);
        assertThat(sectionHeader.getComponentCount(), is(4));
        assertThat(sectionHeader.getComponent(1), is(button1));
        Button button2 = (Button)sectionHeader.getComponent(2);
        assertThat(button2, is(headerButton2));
    }

    @Test
    public void testSetCaption_HeaderIsCreated() {
        AbstractSection section = new TestSection("", false, Optional.empty());

        assertThat(section.getComponentCount(), is(0));

        Label content = new Label("Content");
        section.addComponent(content);

        assertThat(section.getComponentCount(), is(1));

        section.setCaption("CAP");

        assertThat(section.getComponentCount(), is(2));

        HorizontalLayout sectionHeader = (HorizontalLayout)section.getComponent(0);

        assertThat(sectionHeader, is(notNullValue()));
        assertThat(sectionHeader.getComponentCount(), is(2)); // caption, line
        Label captionLabel = (Label)sectionHeader.getComponent(0);
        assertThat(captionLabel.getValue(), is("CAP"));
        assertThat(section.getComponent(1), is(content));
    }

    @Test
    public void testSetCaption_Null_HeaderIsRemoved() {
        AbstractSection section = new TestSection("CAP", false, Optional.empty());

        assertThat(section.getComponentCount(), is(1));

        HorizontalLayout sectionHeader = (HorizontalLayout)section.getComponent(0);

        assertThat(sectionHeader, is(notNullValue()));
        assertThat(sectionHeader.getComponentCount(), is(2)); // caption, line
        Label captionLabel = (Label)sectionHeader.getComponent(0);
        assertThat(captionLabel.getValue(), is("CAP"));

        Label content = new Label("Content");
        section.addComponent(content);

        assertThat(section.getComponentCount(), is(2));

        section.setCaption(null);

        assertThat(section.getComponentCount(), is(1));

        assertThat(section.getComponent(0), is(content));
    }

    @Test
    public void testSetCaption_Empty_HeaderIsRemoved() {
        AbstractSection section = new TestSection("CAP", false, Optional.empty());

        assertThat(section.getComponentCount(), is(1));

        HorizontalLayout sectionHeader = (HorizontalLayout)section.getComponent(0);

        assertThat(sectionHeader, is(notNullValue()));
        assertThat(sectionHeader.getComponentCount(), is(2)); // caption, line
        Label captionLabel = (Label)sectionHeader.getComponent(0);
        assertThat(captionLabel.getValue(), is("CAP"));

        Label content = new Label("Content");
        section.addComponent(content);

        assertThat(section.getComponentCount(), is(2));

        section.setCaption("");

        assertThat(section.getComponentCount(), is(1));

        assertThat(section.getComponent(0), is(content));
    }

}
