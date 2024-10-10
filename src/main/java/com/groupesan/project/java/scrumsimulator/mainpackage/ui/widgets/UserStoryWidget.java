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

    JLabel id;
    JLabel points;
    JLabel name;
    JLabel desc;
    JLabel businessValue;
    JLabel isAssigned;
    
    private transient UserStory userStory;

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

        GridBagLayout myGridBagLayout = new GridBagLayout();
        setLayout(myGridBagLayout);

        add(id, new CustomConstraints(0, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        add(points, new CustomConstraints(1, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        add(name, new CustomConstraints(2, 0, GridBagConstraints.WEST, 0.2, 0.0, GridBagConstraints.HORIZONTAL));
        add(desc, new CustomConstraints(3, 0, GridBagConstraints.WEST, 0.7, 0.0, GridBagConstraints.HORIZONTAL));
        add(businessValue, new CustomConstraints(4, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        add(isAssigned, new CustomConstraints(5, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                openEditDialog();
            }
        });
        return label;
    }

    private void openEditDialog() {
        EditUserStoryForm form = new EditUserStoryForm(userStory);
        form.setVisible(true);

        form.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                updateUserStoryDetails();
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
    }
}