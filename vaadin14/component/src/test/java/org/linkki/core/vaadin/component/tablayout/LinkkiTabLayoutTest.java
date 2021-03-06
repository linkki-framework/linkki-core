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
package org.linkki.core.vaadin.component.tablayout;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.instanceOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.linkki.test.matcher.Matchers.absent;
import static org.linkki.test.matcher.Matchers.hasValue;
import static org.linkki.test.matcher.Matchers.present;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.clearInvocations;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

import java.util.Arrays;
import java.util.NoSuchElementException;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.linkki.util.handler.Handler;
import org.mockito.Mockito;

import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.ComponentEventListener;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.tabs.Tabs.Orientation;
import com.vaadin.flow.component.tabs.Tabs.SelectedChangeEvent;

public class LinkkiTabLayoutTest {

    @Test
    public void testLinkkiTabLayout_VerticalOrientation() {
        LinkkiTabLayout tabLayout = new LinkkiTabLayout(Orientation.VERTICAL);

        Component layout = tabLayout.getContent();

        assertThat(layout, is(instanceOf(HorizontalLayout.class)));
    }

    @Test
    public void testLinkkiTabLayout_HorizontalOrientation() {
        LinkkiTabLayout tabLayout = new LinkkiTabLayout(Orientation.HORIZONTAL);

        Component layout = tabLayout.getContent();

        assertThat(layout, is(instanceOf(VerticalLayout.class)));
    }

    @Test
    public void testAddTabSheet_WithoutIndex() {
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2")).build();

        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        tabLayout.addTabSheet(tabSheet1);
        tabLayout.addTabSheet(tabSheet2);

        assertThat(tabLayout.getTabsComponent().getComponentCount(), is(2));
        assertThat(tabLayout.getTabsComponent().getChildren().collect(Collectors.toList()),
                   Matchers.contains(tabSheet1.getTab(), tabSheet2.getTab()));
    }

    @Test
    public void testAddTabSheet_WithIndex() {
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2")).build();

        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        tabLayout.addTabSheet(tabSheet1);
        tabLayout.addTabSheet(tabSheet2, 0);

        assertThat(tabLayout.getTabsComponent().getComponentCount(), is(2));
        assertThat(tabLayout.getTabsComponent().getChildren().collect(Collectors.toList()),
                   Matchers.contains(tabSheet2.getTab(), tabSheet1.getTab()));
    }

    @Test
    public void testAddTabSheet_FirstSheetIsSelected() {
        Handler onSelectionHandler1 = mock(Handler.class);
        Handler onSelectionHandler2 = mock(Handler.class);
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1"))
                .onSelectionHandler(onSelectionHandler1).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2"))
                .onSelectionHandler(onSelectionHandler2).build();

        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        tabLayout.addTabSheet(tabSheet1);
        tabLayout.addTabSheet(tabSheet2);

        verify(onSelectionHandler1).apply();
        verify(onSelectionHandler2, never()).apply();
        assertThat(tabLayout.getTabsComponent().getSelectedTab(), is(tabSheet1.getTab()));
        assertThat(tabSheet1.getContent().getParent(), is(present()));
        assertThat(tabSheet1.getContent().isVisible(), is(true));
        assertThat(tabSheet2.getContent().getParent(), is(absent()));
    }

    @Test
    public void testGetTabSheets() {
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2")).build();
        tabLayout.addTabSheet(tabSheet1);
        tabLayout.addTabSheet(tabSheet2, 0);

        assertThat(tabLayout.getTabSheets(), contains(tabSheet2, tabSheet1));
    }

    @Test
    public void testAddTabSheetsVararg() {
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2")).build();
        tabLayout.addTabSheets(tabSheet2, tabSheet1);

        assertThat(tabLayout.getTabSheets(), contains(tabSheet2, tabSheet1));
    }

    @Test
    public void testAddTabSheetsStream() {
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2")).build();
        tabLayout.addTabSheets(Stream.of(tabSheet2, tabSheet1));

        assertThat(tabLayout.getTabSheets(), contains(tabSheet2, tabSheet1));
    }

    @Test
    public void testAddTabSheetsIterable() {
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2")).build();
        tabLayout.addTabSheets(Arrays.asList(tabSheet2, tabSheet1));

        assertThat(tabLayout.getTabSheets(), contains(tabSheet2, tabSheet1));
    }

    @Test
    public void testSetSelectedTabSheet() {
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2")).build();

        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        tabLayout.addTabSheets(tabSheet1, tabSheet2);

        tabLayout.setSelectedTabSheet(tabSheet2.getId());

        assertThat(tabLayout.getTabsComponent().getSelectedTab(), is(tabSheet2.getTab()));
        assertThat(tabSheet2.getContent().isVisible(), is(true));
        assertThat(tabSheet1.getContent().isVisible(), is(false));
    }

    @Test
    public void testSetSelectedTabSheet_CallSelectionHandler() {
        Handler onSelectionHandler1 = mock(Handler.class);
        Handler onSelectionHandler2 = mock(Handler.class);

        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        tabLayout.addTabSheet(LinkkiTabSheet.builder("id1").content(new Span("content1"))
                .onSelectionHandler(onSelectionHandler1).build());
        tabLayout.addTabSheet(LinkkiTabSheet.builder("id2").content(new Span("content2"))
                .onSelectionHandler(onSelectionHandler2).build());
        clearInvocations(onSelectionHandler1);

        tabLayout.setSelectedTabSheet("id2");

        verify(onSelectionHandler2).apply();

        tabLayout.setSelectedTabSheet("id1");

        verify(onSelectionHandler1).apply();
    }

    @Test
    public void testCallSelectionHandler_SelectionOnTabsComponent() {
        Handler onSelectionHandler1 = mock(Handler.class);
        Handler onSelectionHandler2 = mock(Handler.class);

        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        tabLayout.addTabSheet(LinkkiTabSheet.builder("id1").content(new Span("content1"))
                .onSelectionHandler(onSelectionHandler1).build());
        tabLayout.addTabSheet(LinkkiTabSheet.builder("id2").content(new Span("content2"))
                .onSelectionHandler(onSelectionHandler2).build());
        clearInvocations(onSelectionHandler1);

        tabLayout.getTabsComponent().setSelectedIndex(1);

        verify(onSelectionHandler2).apply();

        tabLayout.getTabsComponent().setSelectedIndex(0);

        verify(onSelectionHandler1).apply();
    }

    @Test
    public void testSetSelectedTabSheet_LazyInstantiation() {
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        tabLayout.addTabSheet(tabSheet1);
        assertThat(tabLayout.getContent().getChildren()//
                .filter(c -> VerticalLayout.class.isInstance(c))//
                .map(VerticalLayout.class::cast)//
                .flatMap(VerticalLayout::getChildren)//
                .collect(Collectors.toList()), contains(tabSheet1.getContent()));

        Supplier<Component> content2Supplier = spy(new Supplier<Component>() {

            @Override
            public Component get() {
                return new Span();
            }

        });
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(content2Supplier).build();

        tabLayout.addTabSheet(tabSheet2);
        assertThat(tabLayout.getContent().getChildren()//
                .filter(c -> VerticalLayout.class.isInstance(c))//
                .map(VerticalLayout.class::cast)//
                .flatMap(VerticalLayout::getChildren)//
                .collect(Collectors.toList()), contains(tabSheet1.getContent()));
        Mockito.verifyNoInteractions(content2Supplier);

        tabLayout.setSelectedTabSheet("id2");

        assertThat(tabLayout.getContent().getChildren()//
                .filter(c -> VerticalLayout.class.isInstance(c))//
                .map(VerticalLayout.class::cast)//
                .flatMap(VerticalLayout::getChildren)//
                .collect(Collectors.toList()), contains(tabSheet1.getContent(), tabSheet2.getContent()));
    }

    @Test
    public void testSetSelectedTabSheet_NotAddedTabSheet() {
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        tabLayout.addTabSheet(LinkkiTabSheet.builder("id1").content(new Span("content1")).build());

        assertThrows(IllegalArgumentException.class, () -> tabLayout.setSelectedTabSheet("id2"));
    }

    @Test
    public void testGetSelectedTabSheet() {
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2")).build();
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        tabLayout.addTabSheets(tabSheet1, tabSheet2);
        tabLayout.setSelectedTabSheet(tabSheet2.getId());

        LinkkiTabSheet selectedTabSheet = tabLayout.getSelectedTabSheet();

        assertThat(selectedTabSheet, is(tabSheet2));
    }

    @Test
    public void testGetSelectedTabSheet_NoneSelected() {
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();

        assertThrows(NoSuchElementException.class, () -> tabLayout.getSelectedTabSheet());

        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        tabLayout.addTabSheets(tabSheet1);
        tabLayout.getTabsComponent().setSelectedTab(null);

        assertThrows(NoSuchElementException.class, () -> tabLayout.getSelectedTabSheet());
    }

    @Test
    public void testSetSelectedIndex() {
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2")).build();
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        tabLayout.addTabSheets(tabSheet1, tabSheet2);

        tabLayout.setSelectedIndex(1);

        assertThat(tabLayout.getTabsComponent().getSelectedIndex(), is(1));
        assertThat(tabSheet2.getContent().isVisible(), is(true));
        assertThat(tabSheet1.getContent().isVisible(), is(false));
    }

    @Test
    public void testSetSelectedIndex_CallSelectionHandler() {
        Handler onSelectionHandler1 = mock(Handler.class);
        Handler onSelectionHandler2 = mock(Handler.class);
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1"))
                .onSelectionHandler(onSelectionHandler1).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2"))
                .onSelectionHandler(onSelectionHandler2).build();

        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        tabLayout.addTabSheets(tabSheet1, tabSheet2);
        clearInvocations(onSelectionHandler1);

        tabLayout.setSelectedIndex(1);

        verify(onSelectionHandler2).apply();

        tabLayout.setSelectedIndex(0);

        verify(onSelectionHandler1).apply();
    }

    @Test
    public void testSetSelectedIndex_InvalidIndex() {
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        tabLayout.addTabSheet(LinkkiTabSheet.builder("id1").content(new Span("content1")).build());

        assertThrows(IllegalArgumentException.class, () -> tabLayout.setSelectedIndex(1));
    }

    @Test
    public void testSetSelectedIndex_DeselectAll() {
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2")).build();
        tabLayout.addTabSheets(tabSheet1, tabSheet2);
        // Make sure content of tab sheet 2 is ever instantiated
        tabLayout.setSelectedTabSheet(tabSheet2.getId());

        tabLayout.setSelectedIndex(-1);

        assertThat(tabLayout.getTabsComponent().getSelectedIndex(), is(-1));
        assertThat(tabLayout.getTabsComponent().getSelectedTab(), is(nullValue()));
        assertThat(tabSheet1.getContent().isVisible(), is(false));
        assertThat(tabSheet2.getContent().isVisible(), is(false));
    }

    @Test
    public void testGetSelectedIndex() {
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2")).build();
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        tabLayout.addTabSheets(tabSheet1, tabSheet2);

        tabLayout.setSelectedIndex(1);

        assertThat(tabLayout.getSelectedIndex(), is(1));
    }

    @Test
    public void testRemoveTab() {
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2")).build();
        LinkkiTabSheet tabSheet3 = LinkkiTabSheet.builder("id3").content(new Span("content3")).build();
        tabLayout.addTabSheets(tabSheet1, tabSheet2, tabSheet3);

        tabLayout.removeTabSheet(tabSheet2);

        assertThat(tabSheet2.getTab().getParent(), is(absent()));
        assertThat(tabSheet1.getTab().getParent(), is(present()));
        assertThat(tabSheet3.getTab().getParent(), is(present()));
    }

    @Test
    public void testRemoveTabSheet_SelectedTab() {
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2")).build();
        tabLayout.addTabSheets(tabSheet1, tabSheet2);
        tabLayout.setSelectedTabSheet(tabSheet2.getId());

        tabLayout.removeTabSheet(tabSheet2);

        assertThat(tabSheet2.getTab().getParent(), is(absent()));
        assertThat(tabSheet1.getTab().getParent(), is(present()));
        assertThat(tabLayout.getSelectedTabSheet(), is(tabSheet1));
    }

    @Test
    public void testRemoveAllTabSheets() {
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2")).build();
        tabLayout.addTabSheets(tabSheet1, tabSheet2);
        // Make sure content of tab sheet 2 is ever instantiated
        tabLayout.setSelectedTabSheet(tabSheet2.getId());

        tabLayout.removeAllTabSheets();

        assertThat(tabLayout.getTabsComponent().getChildren().collect(Collectors.toList()), is(empty()));
        assertThat(tabSheet1.getContent().getParent().isPresent(), is(false));
        assertThat(tabSheet1.getTab().getParent().isPresent(), is(false));
        assertThat(tabSheet2.getContent().getParent().isPresent(), is(false));
        assertThat(tabSheet2.getTab().getParent().isPresent(), is(false));
    }

    @Test
    public void testGetTabSheet() {
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2")).build();
        tabLayout.addTabSheets(tabSheet1, tabSheet2);

        assertThat(tabLayout.getTabSheet("id1"), is(hasValue(tabSheet1)));
        assertThat(tabLayout.getTabSheet("id2"), is(hasValue(tabSheet2)));
        assertThat(tabLayout.getTabSheet("id3"), is(absent()));
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testAddSelectedTabChangeListener() {
        LinkkiTabLayout tabLayout = new LinkkiTabLayout();
        LinkkiTabSheet tabSheet1 = LinkkiTabSheet.builder("id1").content(new Span("content1")).build();
        LinkkiTabSheet tabSheet2 = LinkkiTabSheet.builder("id2").content(new Span("content2")).build();
        tabLayout.addTabSheets(tabSheet1, tabSheet2);
        ComponentEventListener<SelectedChangeEvent> listener = mock(ComponentEventListener.class);

        tabLayout.addSelectedChangeListener(listener);
        tabLayout.setSelectedTabSheet(tabSheet2.getId());

        verify(listener).onComponentEvent(any());
    }

    @Test
    public void testNewSidebarLayout() {
        LinkkiTabLayout sidebarLayout = LinkkiTabLayout.newSidebarLayout();

        assertThat(sidebarLayout.getContent(), is(instanceOf(HorizontalLayout.class)));
        assertThat(sidebarLayout.getContent().getElement().getThemeList(),
                   contains(LinkkiTabLayout.THEME_VARIANT_SOLID));
    }
}