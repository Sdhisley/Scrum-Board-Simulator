package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels.EditUserStoryForm;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

public class UserStoryWidget extends JPanel implements BaseComponent {

    private JLabel id;
    private JLabel points;
    private JLabel name;
    private JLabel desc;
    private JLabel businessValue;
    private JLabel isAssigned;
    private JLabel status;
    private JLabel solution;

    private transient UserStory userStory;
    private boolean isDialogOpen = false;

    public UserStoryWidget(UserStory userStory) {
        this.userStory = userStory;
        this.init();
    }

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

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        add(id, new CustomConstraints(0, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        add(points, new CustomConstraints(1, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        add(name, new CustomConstraints(2, 0, GridBagConstraints.WEST, 0.2, 0.0, GridBagConstraints.HORIZONTAL));
        add(desc, new CustomConstraints(3, 0, GridBagConstraints.WEST, 0.7, 0.0, GridBagConstraints.HORIZONTAL));
        add(businessValue, new CustomConstraints(4, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        add(isAssigned, new CustomConstraints(5, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        add(status, new CustomConstraints(6, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        add(solution, new CustomConstraints(7, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));

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
        if (isDialogOpen) {
            return;
        }
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
        id.setText(userStory.getId().toString());
        points.setText(Double.toString(userStory.getPointValue()));
        name.setText(userStory.getName());
        desc.setText(userStory.getDescription());
        businessValue.setText(Double.toString(userStory.getBusinessValue()));
        isAssigned.setText(userStory.getAssignStatus());
        status.setText(userStory.getStatus());
        solution.setText(userStory.getSolution());

        revalidate();
        repaint();
    }
}
