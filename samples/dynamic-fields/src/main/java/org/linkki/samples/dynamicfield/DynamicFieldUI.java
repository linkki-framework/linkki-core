package org.linkki.samples.dynamicfield;

import com.vaadin.annotations.Theme;
import com.vaadin.server.Page;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.VaadinSession;
import com.vaadin.server.WrappedSession;
import com.vaadin.ui.UI;
import com.vaadin.util.CurrentInstance;
import org.linkki.core.binding.BindingContext;
import org.linkki.core.ui.section.DefaultPmoBasedSectionFactory;
import org.linkki.core.ui.table.TableSection;
import org.linkki.samples.dynamicfield.components.NewCarDialog;
import org.linkki.samples.dynamicfield.model.Car;
import org.linkki.samples.dynamicfield.model.CarType;
import org.linkki.samples.dynamicfield.pmo.CarRowPmo;
import org.linkki.samples.dynamicfield.pmo.CarTablePmo;

import java.util.ArrayList;
import java.util.List;

@Theme(value = "valo")
public class DynamicFieldUI extends UI {

    private static final long serialVersionUID = -3028891029288587709L;

    private static final String CAR_STORAGE_ATTRIBUTE = "linkki-sample::car-storage";

    @Override
    protected void init(VaadinRequest request) {

        Page.getCurrent().setTitle("Linkki :: Dynamic Fields Sample");

        List<Car> carStorage = getCarStorage();

        BindingContext bindingContext = new BindingContext();

        TableSection<CarRowPmo> table = new DefaultPmoBasedSectionFactory()
                .createTableSection(new CarTablePmo(carStorage,
                        () -> new NewCarDialog(carStorage, bindingContext::updateUI)),
                                    bindingContext);
        setContent(table);
    }

    // some fake persistent storage
    // store the cars in the session so it is available after a browser
    // refresh as long as we are in the same session
    private List<Car> getCarStorage() {
        List<Car> carStorage;

        WrappedSession session = CurrentInstance.get(VaadinSession.class).getSession();
        @SuppressWarnings("unchecked")
        List<Car> storage = (List<Car>)session.getAttribute(CAR_STORAGE_ATTRIBUTE);
        if (storage != null) {
            carStorage = storage;
        } else {
            carStorage = new ArrayList<>();
            addCars(carStorage);
            session.setAttribute(CAR_STORAGE_ATTRIBUTE, carStorage);
        }

        return carStorage;
    }


    private void addCars(List<Car> carStorage) {
        carStorage.add(createCar(CarType.STANDARD, "Audi", "A4 Avant", 200.0));
        carStorage.add(createCar(CarType.PREMIUM, "Porsche", "911 Carrera 4 GTS", 5000.0));
        carStorage.add(createCar(CarType.STANDARD, "Mercedes", "GLA", 300.0));
        carStorage.add(createCar(CarType.PREMIUM, "Ferrari", "812 superfast", 10000.0));

    }

    private Car createCar(CarType carType, String make, String model, double retention) {
        Car car = new Car(carType);
        car.setMake(make);
        car.setModel(model);
        car.setRetention(retention);

        return car;
    }
}
