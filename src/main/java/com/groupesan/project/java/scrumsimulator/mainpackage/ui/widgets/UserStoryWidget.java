package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets; // Import Insets for padding
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.EditUserStoryForm;

public class UserStoryWidget extends JPanel implements BaseComponent {

    private JLabel id;
    private JLabel points;
    private JLabel name;
    private JLabel desc;
    private JLabel businessValue;
    private JLabel isAssigned;
    private JLabel status;
    private JLabel solution;
    private JLabel blockerLabel;  // New label for blocker

    private transient UserStory userStory;
    private boolean isDialogOpen = false;

    public UserStoryWidget(UserStory userStory) {
        this.userStory = userStory;
        this.init();
    }

    public void init() {
        removeAll();

        // Initialize labels for user story properties
        id = createLabel(userStory.getId().toString());
        points = createLabel(Double.toString(userStory.getPointValue()));
        name = createLabel(userStory.getName());
        desc = createLabel(userStory.getDescription());
        businessValue = createLabel(Double.toString(userStory.getBusinessValue()));
        isAssigned = createLabel(userStory.getAssignStatus());
        status = createLabel(userStory.getStatus());
        solution = createLabel(userStory.getSolution());

        // Initialize blocker label
        blockerLabel = createLabel(getFirstBlocker());

        // Layout setup
        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Add padding

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.1;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        add(id, gbc);

        gbc.gridx = 1; add(points, gbc);
        gbc.gridx = 2; add(name, gbc);
        gbc.gridx = 3; gbc.weightx = 0.7; add(desc, gbc);
        gbc.gridx = 4; gbc.weightx = 0.1; add(businessValue, gbc);
        gbc.gridx = 5; add(isAssigned, gbc);
        gbc.gridx = 6; add(status, gbc);
        gbc.gridx = 7; add(solution, gbc);
        gbc.gridx = 8; add(blockerLabel, gbc);

        revalidate();
        repaint();
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                openEditDialog();
            }
        });
        return label;
    }

    private void openEditDialog() {
        if (isDialogOpen) return;
        isDialogOpen = true;
        EditUserStoryForm form = new EditUserStoryForm(userStory);
        form.setVisible(true);

        form.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                isDialogOpen = false;
                UserStoryWidget.this.updateUserStoryDetails();
            }
        });
    }

    private void updateUserStoryDetails() {
        this.init();
    }

    private String getFirstBlocker() {
        List<String> blockers = userStory.getBlockers();
        return blockers.isEmpty() ? "No Blocker" : blockers.get(0);
    }
}
