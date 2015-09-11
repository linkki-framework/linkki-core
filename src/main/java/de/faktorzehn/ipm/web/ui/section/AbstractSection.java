package de.faktorzehn.ipm.web.ui.section;

import static com.google.gwt.thirdparty.guava.common.base.Preconditions.checkNotNull;

import java.util.Optional;

import javax.annotation.Nonnull;

import com.vaadin.server.FontAwesome;
import com.vaadin.server.Resource;
import com.vaadin.shared.ui.label.ContentMode;
import com.vaadin.ui.AbstractLayout;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Button;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.VerticalLayout;

import de.faktorzehn.ipm.web.ButtonPmo;
import de.faktorzehn.ipm.web.ui.application.ApplicationStyles;
import de.faktorzehn.ipm.web.ui.util.ComponentFactory;

/**
 * A section consists of a header displaying a caption and a body/content containing controls to
 * view and edit data. Optionally the section can be closed and opened. When the section is closed
 * only the header is shown.
 */
public abstract class AbstractSection extends VerticalLayout {

    /** PMO for the {@link #openCloseButton}. */
    private class OpenCloseButtonPmo implements ButtonPmo {

        public OpenCloseButtonPmo() {
            super();
        }

        @Override
        public void onClick() {
            switchOpenStatus();
        }

        @Override
        public Resource buttonIcon() {
            return FontAwesome.ANGLE_DOWN;
        }
    }

    private static final long serialVersionUID = 1L;

    private HorizontalLayout header;
    private Button openCloseButton = null;
    private boolean open = true;
    private Button editButton = null;

    /**
     * Creates a new, section with the given caption that cannot be closed.
     * 
     * @param caption the caption to display for this section
     */
    public AbstractSection(@Nonnull String caption) {
        this(caption, false, Optional.empty());
    }

    /**
     * Creates a new section with the given caption.
     * 
     * @param caption the caption to display for this section
     * @param closeable <code>true</code> if the section can be closed and opened.
     */
    public AbstractSection(@Nonnull String caption, boolean closeable) {
        this(caption, closeable, Optional.empty());
    }

    /**
     * Creates a new section with the given caption.
     * 
     * @param caption the caption to display for this section
     * @param closeable <code>true</code> if the section can be closed and opened.
     * @param editButtonPmo If present the section has an edit button in the header.
     */
    public AbstractSection(@Nonnull String caption, boolean closeable, Optional<ButtonPmo> editButtonPmo) {
        super();
        checkNotNull(caption);
        checkNotNull(editButtonPmo);
        createHeader(caption, closeable, editButtonPmo);
    }

    private void createHeader(@Nonnull String caption, boolean closeable, Optional<ButtonPmo> editButtonPmo) {
        header = new HorizontalLayout();
        header.setSpacing(true);
        addComponent(header);
        header.addStyleName(ApplicationStyles.SECTION_CAPTION);

        Label l = new Label(caption);
        l.addStyleName(ApplicationStyles.SECTION_CAPTION_TEXT);
        header.addComponent(l);
        header.setComponentAlignment(l, Alignment.MIDDLE_LEFT);

        if (editButtonPmo.isPresent()) {
            createEditButton(editButtonPmo.get());
        }

        if (closeable) {
            createOpenCloseButton();
        }

        Label line = new Label("<hr/>", ContentMode.HTML);
        line.addStyleName(ApplicationStyles.SECTION_CAPTION_LINE);
        header.addComponent(line);
        header.setComponentAlignment(line, Alignment.MIDDLE_LEFT);
    }

    private void createEditButton(ButtonPmo buttonPmo) {
        editButton = ComponentFactory.newButton(buttonPmo);
        header.addComponent(editButton);
    }

    public boolean isEditButtonAvailable() {
        return editButton != null;
    }

    private void createOpenCloseButton() {
        openCloseButton = ComponentFactory.newButton(new OpenCloseButtonPmo());
        header.addComponent(openCloseButton);
    }

    /**
     * Adds a new button to the header for the given button PMO. The new button is added before the
     * close button. If the section does not have a close button it is added at the end of the
     * header.
     */
    public void addHeaderButton(ButtonPmo buttonPmo) {
        Button headerButton = ComponentFactory.newButton(buttonPmo);
        addBeforeCloseButton(headerButton);
    }

    private void addBeforeCloseButton(Button headerButton) {
        if (openCloseButton != null) {
            header.addComponent(headerButton, header.getComponentIndex(openCloseButton));
        } else {
            header.addComponent(headerButton, header.getComponentCount() - 1);
        }

    }

    /**
     * Returns <code>true</code> if the section is open.
     */
    public boolean isOpen() {
        return open;
    }

    /**
     * Returns <code>true</code> if the section is closed.
     */
    public boolean isClosed() {
        return !open;
    }

    /**
     * Opens the sections.
     */
    public void open() {
        if (open) {
            return;
        }
        switchOpenStatus();
    }

    /**
     * Closed the section.
     */
    public void close() {
        if (isClosed()) {
            return;
        }
        switchOpenStatus();
    }

    protected void switchOpenStatus() {
        open = !open;
        if (openCloseButton != null) {
            openCloseButton.setIcon(open ? FontAwesome.ANGLE_DOWN : FontAwesome.ANGLE_RIGHT);
        }
        setAllChildComponentsVisibilityBasedOnOpenStatus(this);
    }

    protected void setAllChildComponentsVisibilityBasedOnOpenStatus(AbstractLayout layout) {
        for (Component c : layout) {
            if (c != header) {
                c.setVisible(open);
            }
        }
    }

}
