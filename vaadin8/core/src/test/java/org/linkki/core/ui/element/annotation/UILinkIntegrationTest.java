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
package org.linkki.core.ui.element.annotation;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

import org.junit.jupiter.api.Test;
import org.linkki.core.defaults.ui.aspects.types.CaptionType;
import org.linkki.core.defaults.ui.aspects.types.VisibleType;
import org.linkki.core.ui.element.annotation.UILink.LinkTarget;
import org.linkki.core.ui.element.annotation.UILinkIntegrationTest.LinkTestPmo;
import org.linkki.core.ui.layout.annotation.UISection;

import com.vaadin.server.ExternalResource;
import com.vaadin.ui.Link;

import edu.umd.cs.findbugs.annotations.CheckForNull;

public class UILinkIntegrationTest extends ComponentAnnotationIntegrationTest<Link, LinkTestPmo> {

    public UILinkIntegrationTest() {
        super(mo -> new LinkTestPmo());
    }

    @Test
    public void testValue_Dynamic() {
        Link link = getDynamicComponent();

        assertThat(getDefaultPmo().getDynamic(), is(nullValue()));
        assertThat(link.getResource(), is(nullValue()));

        getDefaultPmo().setDynamic("https://faktorzehn.org");
        modelChanged();

        assertThat(((ExternalResource)link.getResource()).getURL(), is("https://faktorzehn.org"));

        getDefaultPmo().setDynamic("");
        modelChanged();

        assertThat(((ExternalResource)link.getResource()).getURL(), is(""));
    }

    @Test
    public void testLinkCaption() {
        Link link = getDynamicComponent();

        assertThat(link.getCaption(), is(LinkTestPmo.INITIAL_CAPTION));

        getDefaultPmo().setDynamicCaption("caption");
        modelChanged();

        assertThat(link.getCaption(), is("caption"));
    }

    @Test
    public void testLinkCaption_Static() {
        Link link = getStaticComponent();

        assertThat(link.getCaption(), is(LinkTestPmo.STATIC_CAPTION));
    }

    @Test
    public void testLinkCaption_Default() {
        Link link = getComponentById("defaultsLink");

        assertThat(link.getCaption(), is(""));
    }

    @Test
    public void testTarget_Dynamic() {
        Link link = getDynamicComponent();

        assertThat(link.getTargetName(), is("_top"));

        getDefaultPmo().setDynamicTarget("_parent");
        modelChanged();

        assertThat(link.getTargetName(), is("_parent"));

        getDefaultPmo().setDynamicTarget("");
        modelChanged();

        assertThat(link.getTargetName(), is(""));
    }

    @Test
    public void testTarget_Static() {
        Link link = getStaticComponent();

        assertThat(link.getTargetName(), is("_blank"));
    }

    @Test
    public void testTarget_Default() {
        Link link = getComponentById("defaultsLink");

        assertThat(link.getTargetName(), is("_self"));
    }

    @Override
    public void testEnabled() {
        assertThat(getStaticComponent().isEnabled(), is(true));
        assertThat(getDynamicComponent().isEnabled(), is(true));
    }

    @UISection
    protected static class LinkTestPmo extends AnnotationTestPmo {

        public static final String INITIAL_CAPTION = "https://linkki-framework.org";
        public static final String READONLY_LINK = "link";
        public static final String STATIC_CAPTION = "caption";

        @CheckForNull
        private String value;
        private String valueCaption;
        private String valueTarget;

        public LinkTestPmo() {
            super(null);
            this.valueCaption = INITIAL_CAPTION;
            this.valueTarget = "_top";
        }

        @CheckForNull
        @UILink(position = 1, visible = VisibleType.DYNAMIC, captionType = CaptionType.DYNAMIC, target = LinkTarget.DYNAMIC, label = "")
        public String getDynamic() {
            return value;
        }

        public void setDynamic(String value) {
            this.value = value;
        }

        public String getDynamicCaption() {
            return valueCaption;
        }

        public void setDynamicCaption(String valueCaption) {
            this.valueCaption = valueCaption;
        }

        public String getDynamicTarget() {
            return valueTarget;
        }

        public void setDynamicTarget(String valueTarget) {
            this.valueTarget = valueTarget;
        }

        @UILink(position = 2, label = TEST_LABEL, visible = VisibleType.INVISIBLE, caption = STATIC_CAPTION, target = "_blank")
        public String getStaticValue() {
            return READONLY_LINK;
        }

        @UILink(position = 3)
        public String getDefaultsLink() {
            return READONLY_LINK;
        }

        public String getDefaultsLinkCaption() {
            return "";
        }

        @Override
        public void staticValue() {
            // not needed as UI Link does not support model binding
        }

        @Override
        public void dynamic() {
            // not needed as UI Link does not support model binding
        }
    }
}
