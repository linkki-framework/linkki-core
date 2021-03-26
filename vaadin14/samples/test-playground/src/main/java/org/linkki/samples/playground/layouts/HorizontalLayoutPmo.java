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

package org.linkki.samples.playground.layouts;

import org.linkki.core.ui.element.annotation.UIButton;
import org.linkki.core.ui.element.annotation.UICheckBox;
import org.linkki.core.ui.element.annotation.UIIntegerField;
import org.linkki.core.ui.element.annotation.UILabel;
import org.linkki.core.ui.element.annotation.UITextField;
import org.linkki.core.ui.layout.annotation.UIHorizontalLayout;

import com.vaadin.flow.component.notification.Notification;

@UIHorizontalLayout
public class HorizontalLayoutPmo {

    private String text;
    private int amount;

    @UILabel(position = 10)
    public String getLabel() {
        return "@UILabel content";
    }

    @UITextField(label = "Text", position = 20)
    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @UIIntegerField(label = "Amount", position = 21)
    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @UIButton(label = "Button", position = 30, caption = "Button")
    public void button() {
        Notification.show("Button clicked");
    }

    @UICheckBox(label = "Checkbox", caption = "Checkbox", position = 40)
    public boolean getCheckbox() {
        return true;
    }
}
