/*******************************************************************************
 * Copyright (c) 2014 Faktor Zehn AG.
 * 
 * Alle Rechte vorbehalten.
 *******************************************************************************/

package de.faktorzehn.ipm.web.ui.table;

import java.util.Optional;

import de.faktorzehn.ipm.web.ButtonPmo;
import de.faktorzehn.ipm.web.PresentationModelObject;
import de.faktorzehn.ipm.web.ui.section.AbstractSection;

public class TableSection<T extends PresentationModelObject> extends AbstractSection {

    private static final long serialVersionUID = 1L;

    private PmoBasedTable<? extends PresentationModelObject> table;

    public TableSection(String caption, Optional<ButtonPmo> addItemButtonPmo) {
        super(caption, false, addItemButtonPmo);
    }

    void setTable(PmoBasedTable<? extends PresentationModelObject> theTable) {
        if (this.table != null) {
            throw new IllegalStateException("Table already set.");
        }
        this.table = theTable;
        addComponent(table);
    }

    /**
     * Manually update the table in this section from its pmo.
     * 
     * Introduced for the AA project.
     */
    public void update() {
        table.updateFromPmo();
    }

    @Override
    public String toString() {
        return "TableSection based on " + table.getContainerDataSource();
    }
}
