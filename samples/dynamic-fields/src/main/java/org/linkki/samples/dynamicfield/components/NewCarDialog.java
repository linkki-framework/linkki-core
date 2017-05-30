package org.linkki.samples.dynamicfield.components;

import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import org.linkki.core.binding.BindingContext;
import org.linkki.core.binding.dispatcher.PropertyBehaviorProvider;
import org.linkki.core.ui.section.DefaultPmoBasedSectionFactory;
import org.linkki.core.ui.section.PmoBasedSectionFactory;
import org.linkki.samples.dynamicfield.model.Car;
import org.linkki.samples.dynamicfield.model.CarType;
import org.linkki.samples.dynamicfield.model.NewCar;
import org.linkki.samples.dynamicfield.pmo.CarTypePmo;
import org.linkki.samples.dynamicfield.pmo.NewCarPmo;
import org.linkki.util.handler.Handler;

import java.util.List;

public class NewCarDialog extends Window {

    private static final long serialVersionUID = -6187152700037744469L;


    private final List<Car> carStorage;

    private final PmoBasedSectionFactory sectionFactory;
    private final Handler afterSaveAction;


    public NewCarDialog(List<Car> carStorage, Handler afterSaveAction) {
        this.carStorage = carStorage;
        this.afterSaveAction = afterSaveAction;

        setWidth(600, Unit.PIXELS);
        setHeightUndefined();
        setResizable(false);

        VerticalLayout layout = new VerticalLayout();


        sectionFactory = new DefaultPmoBasedSectionFactory();

        NewCar car = new NewCar();

        layout.addComponent(sectionFactory.createSection(new CarTypePmo(car),
                                                         new BindingContext("car-type",
                                                                 PropertyBehaviorProvider.NO_BEHAVIOR_PROVIDER,
                                                                 () -> addNewCarSection(layout, car))));
        setContent(layout);

        center();
        UI.getCurrent().addWindow(this);
    }

    private void addNewCarSection(VerticalLayout layout, NewCar car) {

        if (layout.getComponentCount() > 1) {
            layout.removeComponent(layout.getComponent(1));
        }

        // reset retention if the carType == premium
        if (CarType.PREMIUM == car.getCarType()) {
            car.setRetention(null);
        }

        layout.addComponent(sectionFactory.createSection(new NewCarPmo(car, this.carStorage, this::close),
                                                         new BindingContext("new-car",
                                                                 PropertyBehaviorProvider.NO_BEHAVIOR_PROVIDER,
                                                                 afterSaveAction)));
        center();
    }


}
