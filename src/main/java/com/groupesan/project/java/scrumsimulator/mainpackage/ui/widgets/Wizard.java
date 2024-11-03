package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

/** Reusable component that handles the core functionality of a wizard. */
public abstract class Wizard<T> extends JFrame implements BaseComponent {
    private final List<WizardPage> pages;
    private int pageNum;
    private final WizardHandler<T> handler;

    public Wizard(WizardHandler<T> handler) {
        this.pageNum = 0;
        this.handler = handler;
        this.initDataModels();
        this.pages = new ArrayList<>(this.build());
        this.init();
    }

    @Override
    public void init() {
        setSize(800, 600);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());
        setLocationRelativeTo(null);

        CardLayout bodyLayout = new CardLayout();
        JPanel body = new JPanel(bodyLayout);
        body.setBorder(new EmptyBorder(10, 10, 10, 10));

        // Render each page and add it to the body container with a unique ID
        for (WizardPage page : pages) {
            body.add(page.render(), page.getId());
        }

        JPanel footer = buildFooter(bodyLayout, body);

        add(body, BorderLayout.CENTER);
        add(footer, BorderLayout.SOUTH);

        bodyLayout.show(body, this.pages.get(this.pageNum).getId());
    }

    private JPanel buildFooter(CardLayout bodyLayout, JPanel bodyContainer) {
        JPanel footer = new JPanel(new BorderLayout());
        footer.setBorder(new EmptyBorder(5, 10, 5, 5));
        JLabel stepsLabel = new JLabel();
        this.updateStepsLabel(stepsLabel);
        
        JPanel navigation = new JPanel();

        JButton cancel = new JButton("Cancel");
        JButton previous = new JButton("Previous");
        previous.setEnabled(this.pageNum > 0);

        JButton next = new JButton("Next");
        JButton finish = new JButton("Finish");
        finish.setVisible(false);

        // Update visibility based on the page number
        updateNavigationButtons(next, finish);

        // Cancel button action
        cancel.addActionListener(e -> {
            onCancel();
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });

        // Previous button action
        previous.addActionListener(e -> {
            if (pageNum > 0) {
                pageNum--;
                updateNavigationButtons(next, finish);
                bodyLayout.show(bodyContainer, getCurrentPage().getId());
                updateStepsLabel(stepsLabel);
            }
            previous.setEnabled(pageNum != 0);
        });

        // Next button action
        next.addActionListener(e -> {
            if (pageNum < pages.size() - 1) {
                pageNum++;
                updateNavigationButtons(next, finish);
                bodyLayout.show(bodyContainer, getCurrentPage().getId());
                updateStepsLabel(stepsLabel);
            }
            previous.setEnabled(pageNum > 0);
        });

        // Finish button action
        finish.addActionListener(e -> {
            onSubmit();
            dispatchEvent(new WindowEvent(this, WindowEvent.WINDOW_CLOSING));
        });

        navigation.add(cancel);
        navigation.add(previous);
        navigation.add(next);
        navigation.add(finish);

        footer.add(stepsLabel, BorderLayout.WEST);
        footer.add(navigation, BorderLayout.EAST);
        return footer;
    }

    private void updateNavigationButtons(JButton next, JButton finish) {
        boolean isLastPage = this.pageNum == pages.size() - 1;
        next.setVisible(!isLastPage);
        finish.setVisible(isLastPage);
    }

    private WizardPage getCurrentPage() {
        return this.pages.get(this.pageNum);
    }

    private void updateStepsLabel(JLabel label) {
        label.setText("Step " + (this.pageNum + 1) + "/" + pages.size());
    }

    /**
     * Called when the user completes the wizard. Invoked after the wizard is closed.
     */
    protected void onSubmit() {
        handler.onSubmit(process());
    }

    protected abstract T process();

    /** Called when the user cancels the wizard. Invoked after the wizard is closed. */
    protected void onCancel() {}

    /** Method for defining data models that will be used in `build` */
    protected abstract void initDataModels();

    /**
     * Method for defining the list of pages that should be used in the wizard. If you're using
     * {@link com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils.DataModel}s, be sure to
     * instantiate them in `initDataModels`.
     *
     * @return A list of pages to be used in the wizard
     */
    protected abstract List<WizardPage> build();

    /** Abstract class definition for a wizard page. */
    public abstract static class WizardPage {
        /**
         * A unique identifier for this page. Must be different from other IDs for a given Wizard
         *
         * @return A unique string identifier
         */
        protected abstract String getId();

        /**
         * Method to render the content for the wizard page
         *
         * @return A JPanel containing the rendered contents of the page.
         */
        protected abstract JPanel render();
    }
}
