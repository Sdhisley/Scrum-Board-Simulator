package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
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
    private JLabel blockerLabel;
    private JLabel blockerStatusLabel;
    private transient UserStory userStory;
    private boolean isDialogOpen = false;

    public UserStoryWidget(UserStory userStory) {
        this.userStory = userStory;
        init();
    }

    @Override
    public void init() {
        removeAll();

        id = createLabel(userStory.getId().toString());
        points = createLabel(Double.toString(userStory.getPointValue()));
        name = createLabel(userStory.getName());
        desc = createLabel(userStory.getDescription());
        businessValue = createLabel(Double.toString(userStory.getBusinessValue()));
        isAssigned = createLabel(userStory.getAssignStatus());
        status = createLabel(userStory.getStatus());
        solution = createLabel(userStory.getSolution());
        blockerLabel = createLabel(getAllBlockers());
        blockerStatusLabel = createLabel(getBlockerStatusText());

        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0.2; add(id, gbc);
        gbc.gridx = 1; gbc.weightx = 0.1; add(points, gbc);
        gbc.gridx = 2; gbc.weightx = 0.2; add(name, gbc);
        gbc.gridx = 3; gbc.weightx = 0.3; add(desc, gbc);
        gbc.gridx = 4; gbc.weightx = 0.1; add(businessValue, gbc);
        gbc.gridx = 5; gbc.weightx = 0.2; add(isAssigned, gbc);
        gbc.gridx = 6; gbc.weightx = 0.1; add(status, gbc);
        gbc.gridx = 7; gbc.weightx = 0.2; add(solution, gbc);
        gbc.gridx = 8; gbc.weightx = 0.2; add(blockerLabel, gbc);
        gbc.gridx = 9; gbc.weightx = 0.3; add(blockerStatusLabel, gbc);

        revalidate();
        repaint();
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.getClickCount() == 2) {  // Double-click to open edit dialog
                    openEditDialog();
                }
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
                updateUserStoryDetails();
            }
        });
    }

    private void updateUserStoryDetails() {
        init();
    }

    private String getAllBlockers() {
        List<String> blockers = userStory.getBlockers();
        return blockers.isEmpty() ? "No Blocker" : String.join(", ", blockers);
    }

    private String getBlockerStatusText() {
        return userStory.isBlockerResolved() ? "Blocker: Resolved" : "Blocker: Unresolved";
    }

    public void updateBlockerStatus() {
        blockerStatusLabel.setText(getBlockerStatusText());
        revalidate();
        repaint();
    }

    public void setBlockerLabel(String blocker) {
        blockerLabel.setText(getAllBlockers());
        updateBlockerStatus(); // Also update blocker status when blockers change
    }
}
