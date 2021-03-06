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
package org.linkki.samples.playground.ui;

import org.faktorips.runtime.ValidationContext;
import org.linkki.core.binding.BindingContext;
import org.linkki.core.binding.dispatcher.behavior.PropertyBehaviorProvider;
import org.linkki.core.binding.manager.BindingManager;
import org.linkki.core.binding.manager.DefaultBindingManager;
import org.linkki.core.binding.validation.ValidationService;
import org.linkki.core.ui.creation.VaadinUiCreator;
import org.linkki.core.uiframework.UiFramework;
import org.linkki.core.vaadin.component.tablayout.LinkkiTabLayout;
import org.linkki.core.vaadin.component.tablayout.LinkkiTabSheet;
import org.linkki.ips.binding.dispatcher.IpsPropertyDispatcherFactory;
import org.linkki.ips.messages.MessageConverter;
import org.linkki.samples.playground.TestScenario;
import org.linkki.samples.playground.dynamicannotations.DynamicAnnotationsLayout;
import org.linkki.samples.playground.ips.model.IpsModelObject;
import org.linkki.samples.playground.messages.MessagesComponent;
import org.linkki.samples.playground.nestedcomponent.NestedComponentPage;
import org.linkki.samples.playground.tablayout.TabLayoutPage;
import org.linkki.samples.playground.table.TablePage;
import org.linkki.samples.playground.treetable.SampleTreeTableComponent;
import org.linkki.samples.playground.ts.alignment.HorizontalAlignmentTestComponent;
import org.linkki.samples.playground.ts.alignment.VerticalAlignmentTestComponent;
import org.linkki.samples.playground.ts.aspects.BindStyleNamesPmo;
import org.linkki.samples.playground.ts.components.ButtonPmo;
import org.linkki.samples.playground.ts.components.ComboBoxPmo;
import org.linkki.samples.playground.ts.components.CustomFieldPmo;
import org.linkki.samples.playground.ts.components.DateFieldPmo;
import org.linkki.samples.playground.ts.components.DoubleFieldPmo;
import org.linkki.samples.playground.ts.components.DynamicComponentPage;
import org.linkki.samples.playground.ts.components.IntegerFieldPmo;
import org.linkki.samples.playground.ts.components.LabelPmo;
import org.linkki.samples.playground.ts.components.LinkPmo;
import org.linkki.samples.playground.ts.components.RadioButtonsPmo;
import org.linkki.samples.playground.ts.components.TextAreaPmo;
import org.linkki.samples.playground.ts.components.TextFieldPmo;
import org.linkki.samples.playground.ts.ips.DecimalFieldPmo;
import org.linkki.samples.playground.ts.ips.EnabledSectionPmo;
import org.linkki.samples.playground.ts.ips.IpsPmo;
import org.linkki.samples.playground.ts.ips.RequiredSectionPmo;
import org.linkki.samples.playground.ts.ips.VisibleSectionPmo;
import org.linkki.samples.playground.ts.layouts.BasicElementsLayoutBehaviorCssLayoutPmo;
import org.linkki.samples.playground.ts.layouts.BasicElementsLayoutBehaviorFormLayoutPmo;
import org.linkki.samples.playground.ts.layouts.BasicElementsLayoutBehaviorFormSectionPmo;
import org.linkki.samples.playground.ts.layouts.BasicElementsLayoutBehaviorHorizontalLayoutPmo;
import org.linkki.samples.playground.ts.layouts.BasicElementsLayoutBehaviorUiSectionPmo;
import org.linkki.samples.playground.ts.layouts.BasicElementsLayoutBehaviorVerticalLayoutPmo;
import org.linkki.samples.playground.ts.linkkitext.LinkkiTextComponent;
import org.linkki.samples.playground.ts.localization.I18NElementsLocalizationPmo;
import org.linkki.samples.playground.ts.sectionheader.SectionHeaderBehaviorPmo;

import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.router.BeforeEvent;
import com.vaadin.flow.router.HasUrlParameter;
import com.vaadin.flow.router.OptionalParameter;
import com.vaadin.flow.router.PageTitle;
import com.vaadin.flow.router.Route;

@Route(value = "", layout = PlaygroundAppLayout.class)
@PageTitle("linkki Sample :: Playground")
public class PlaygroundApplicationView extends Div implements HasUrlParameter<String> {

    private static final long serialVersionUID = 1L;

    public static final String DYNAMIC_ASPECT_TAB_ID = "dynamic";
    public static final String TABLES_TAB_ID = "tables";
    public static final String NESTED_COMPONENT_PAGE_TAB_ID = "nestedComponentPage";
    public static final String TAB_LAYOUT_TAB_ID = "tab-layout";
    public static final String MESSAGES_TAB_ID = "messages";
    public static final String TREETABLE_TAB_ID = "tree-table";

    public PlaygroundApplicationView() {
        setSizeFull();
    }

    @Override
    public void setParameter(BeforeEvent event, @OptionalParameter String parameter) {
        removeAll();
        LinkkiTabLayout tabLayout = LinkkiTabLayout.newSidebarLayout();
        tabLayout.setId("test-scenario-selector");
        tabLayout.addTabSheets(

                               // new test scenarios
                               TestScenario.id("TS001")
                                       .testCase("TC001", new BasicElementsLayoutBehaviorUiSectionPmo())
                                       .testCase("TC002", new BasicElementsLayoutBehaviorFormSectionPmo())
                                       .testCase("TC003", new BasicElementsLayoutBehaviorHorizontalLayoutPmo())
                                       .testCase("TC004", new BasicElementsLayoutBehaviorVerticalLayoutPmo())
                                       .testCase("TC005", new BasicElementsLayoutBehaviorFormLayoutPmo())
                                       .testCase("TC006", new BasicElementsLayoutBehaviorCssLayoutPmo())
                                       .createTabSheet(),
                               TestScenario.id("TS002")
                                       .testCase("TC001", new SectionHeaderBehaviorPmo())
                                       .createTabSheet(),
                               TestScenario.id("TS003")
                                       .testCase("TC001", new I18NElementsLocalizationPmo())
                                       .createTabSheet(),
                               addIpsTabSheet(),
                               TestScenario.id("TS005")
                                       .testCase("TC001", new LabelPmo())
                                       .testCase("TC002", new IntegerFieldPmo())
                                       .testCase("TC003", new DoubleFieldPmo())
                                       .testCase("TC004", new TextAreaPmo())
                                       .testCase("TC005", new TextFieldPmo())
                                       .testCase("TC006", new DateFieldPmo())
                                       .testCase("TC007", new ComboBoxPmo())
                                       .testCase("TC008", new RadioButtonsPmo())
                                       .testCase("TC009", new LinkPmo())
                                       .testCase("TC010", new ButtonPmo())
                                       .testCase("TC011", new CustomFieldPmo())
                                       .testCase("TC012", new DynamicComponentPage())
                                       .createTabSheet(),
                               TestScenario.id("TS006")
                                       .testCase("TC001", new LinkkiTextComponent())
                                       .createTabSheet(),
                               TestScenario.id("TS007")
                                       .testCase("TC001", new VerticalAlignmentTestComponent())
                                       .testCase("TC002", new HorizontalAlignmentTestComponent())
                                       .createTabSheet(),
                               TestScenario.id("TS008")
                                       .testCase("TC001", new BindStyleNamesPmo())
                                       .createTabSheet(),

                               // old tab sheets
                               LinkkiTabSheet.builder(DYNAMIC_ASPECT_TAB_ID)
                                       .caption(VaadinIcon.FLIGHT_TAKEOFF.create())
                                       .description("Dynamic Aspects")
                                       .content(new DynamicAnnotationsLayout()).build(),
                               LinkkiTabSheet.builder(TABLES_TAB_ID)
                                       .caption(VaadinIcon.TABLE.create())
                                       .description("Tables")
                                       .content(new TablePage()).build(),
                               LinkkiTabSheet.builder(NESTED_COMPONENT_PAGE_TAB_ID)
                                       .caption(VaadinIcon.ROAD_BRANCHES.create())
                                       .description("Nested Components")
                                       .content(new NestedComponentPage()).build(),
                               LinkkiTabSheet.builder(TAB_LAYOUT_TAB_ID)
                                       .caption(VaadinIcon.TABS.create())
                                       .description("Tab Layout")
                                       .content(new TabLayoutPage()).build(),
                               LinkkiTabSheet.builder(MESSAGES_TAB_ID)
                                       .caption(VaadinIcon.COMMENT_ELLIPSIS_O.create())
                                       .description("Messages Component")
                                       .content(new MessagesComponent()).build(),
                               LinkkiTabSheet.builder(TREETABLE_TAB_ID)
                                       .caption(VaadinIcon.FILE_TREE.create())
                                       .description("Tree Table")
                                       .content(new SampleTreeTableComponent()).build()

        );

        add(tabLayout);
    }

    private LinkkiTabSheet addIpsTabSheet() {
        IpsModelObject ipsModelObject = new IpsModelObject();

        ValidationService validationService = () -> MessageConverter
                .convert(ipsModelObject.validate(new ValidationContext(UiFramework.getLocale())));

        BindingManager bindingManager = new DefaultBindingManager(validationService,
                PropertyBehaviorProvider.NO_BEHAVIOR_PROVIDER, new IpsPropertyDispatcherFactory());

        BindingContext bc = bindingManager.getContext("IpsBindingContext");

        return TestScenario.id("TS004")
                .testCase("TC001", VaadinUiCreator.createComponent(new IpsPmo(ipsModelObject), bc))
                .testCase("TC002", VaadinUiCreator.createComponent(new DecimalFieldPmo(), bc))
                .testCase("TC003", VaadinUiCreator.createComponent(new RequiredSectionPmo(), bc))
                .testCase("TC004", VaadinUiCreator.createComponent(new VisibleSectionPmo(), bc))
                .testCase("TC005", VaadinUiCreator.createComponent(new EnabledSectionPmo(), bc))
                .createTabSheet();
    }

}
