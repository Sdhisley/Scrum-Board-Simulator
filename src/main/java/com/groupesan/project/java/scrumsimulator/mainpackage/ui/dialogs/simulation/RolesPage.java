package com.groupesan.project.java.scrumsimulator.mainpackage.ui.dialogs.simulation;

import com.groupesan.project.java.scrumsimulator.mainpackage.core.ScrumRole;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils.DataModel;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils.GridBagConstraintsBuilder;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.CreateEditMode;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.DataList;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.ResuableHeader;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.Wizard;
import javax.swing.*;
import java.awt.*;
import java.util.List;

public class RolesPage extends Wizard.WizardPage {
    private final DataModel<List<ScrumRole>> roles;
    private final JFrame parentFrame;

    // Constructor for SimulationWizard with only the roles DataModel
    public RolesPage(DataModel<List<ScrumRole>> roles) {
        this.roles = roles;
        this.parentFrame = null; // No parent frame in this context
    }

    // Constructor with JFrame for cases where a frame is needed
    public RolesPage(DataModel<List<ScrumRole>> roles, JFrame parentFrame) {
        this.roles = roles;
        this.parentFrame = parentFrame;
    }

    @Override
    protected String getId() {
        return "Roles";
    }

    @Override
    public JPanel render() {
        JPanel container = new JPanel(new BorderLayout());

        ResuableHeader resuableHeader = new ResuableHeader("Roles", "Roles to assign to participants");

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEADING, 0, 0));
        JButton create = new JButton("Create");
        JButton edit = new JButton("Edit");
        JButton delete = new JButton("Delete");

        DataList<ScrumRole> list = new DataList<>(this.roles);

        // Initialize the list with some sample roles
        for (int i = 0; i < 10; i++) {
            list.addItem(new ScrumRole("Role " + i));
        }

        // Enable/disable buttons based on selection
        list.addListSelectionListener(
                l -> {
                    if (l.getValueIsAdjusting()) {
                        return;
                    }
                    if (list.getSelectedIndex() == -1) {
                        edit.setEnabled(false);
                        delete.setEnabled(false);
                    } else {
                        edit.setEnabled(true);
                        delete.setEnabled(true);
                    }
                });

        edit.setEnabled(list.itemCount() > 0);
        delete.setEnabled(list.itemCount() > 0);

        JScrollPane rolesPane = new JScrollPane(list);

        // Action listeners for create, edit, and delete buttons
        create.addActionListener(
                l -> new RoleCreateEditDialog(
                        CreateEditMode.CREATE,
                        "Role",
                        null,
                        data -> {
                            ScrumRole scrumRole = new ScrumRole(data);
                            list.addItem(scrumRole);
                        }).setVisible(true)
        );

        edit.addActionListener(
                l -> {
                    int selectedIndex = list.getSelectedIndex();
                    ScrumRole selectedScrumRole = list.getModel().getElementAt(selectedIndex);
                    new RoleCreateEditDialog(
                            CreateEditMode.EDIT,
                            "Role",
                            selectedScrumRole.getName(),
                            data -> {
                                selectedScrumRole.setName(data);
                                list.editItem(selectedIndex, selectedScrumRole);
                            }).setVisible(true);
                }
        );

        delete.addActionListener(l -> list.removeSelectedItem());

        // Adding the buttons to the controls panel
        controls.add(create);
        controls.add(Box.createHorizontalStrut(5));
        controls.add(edit);
        controls.add(Box.createHorizontalStrut(5));
        controls.add(delete);

        // Layout components
        JPanel inputs = new JPanel(new GridBagLayout());
        inputs.add(
                resuableHeader,
                new GridBagConstraintsBuilder()
                        .setGridX(0)
                        .setGridY(0)
                        .setWeightX(1)
                        .setFill(GridBagConstraints.HORIZONTAL)
                        .setInsets(new Insets(0, 0, 5, 0))
        );
        inputs.add(
                controls,
                new GridBagConstraintsBuilder()
                        .setGridX(0)
                        .setGridY(1)
                        .setFill(GridBagConstraints.HORIZONTAL)
                        .setInsets(new Insets(0, 0, 5, 0))
        );
        inputs.add(
                rolesPane,
                new GridBagConstraintsBuilder()
                        .setGridX(0)
                        .setGridY(2)
                        .setFill(GridBagConstraints.BOTH)
                        .setWeightY(1.0)
                        .setWeightX(1.0)
        );

        container.add(inputs, BorderLayout.CENTER);

        // Footer with Cancel and Finish buttons (no Previous button)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton cancelButton = new JButton("Cancel");
        JButton finishButton = new JButton("Finish");

        // Cancel button closes the parent frame if it exists
        cancelButton.addActionListener(e -> {
            if (parentFrame != null) {
                parentFrame.dispose();
            }
        });

        finishButton.addActionListener(e -> {
            System.out.println("Finish action triggered");
            if (parentFrame != null) {
                parentFrame.dispose();
            }
        });

        buttonPanel.add(cancelButton);
        buttonPanel.add(finishButton);

        container.add(buttonPanel, BorderLayout.SOUTH);

        return container;
    }
}
