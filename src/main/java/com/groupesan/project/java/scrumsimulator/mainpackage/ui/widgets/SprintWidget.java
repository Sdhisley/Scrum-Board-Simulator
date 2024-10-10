package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

public class SprintWidget extends JPanel implements BaseComponent {

    JLabel id;
    JLabel name;
    JLabel desc;
    JLabel len;
    JLabel remaining;
    JLabel numUserStories;

    // Add a JButton for interaction
    JButton viewDetailsButton;

    public SprintWidget(Sprint sprint) {
        id = new JLabel(Integer.toString(sprint.getId()));
        name = new JLabel(sprint.getName());
        desc = new JLabel(sprint.getDescription());
        len = new JLabel(Integer.toString(sprint.getLength()));
        remaining = new JLabel(Integer.toString(sprint.getDaysRemaining()));
        numUserStories = new JLabel(Integer.toString(sprint.getUserStories().size()));

        // Initialize the button
        viewDetailsButton = new JButton("View Details");

        this.init();
    }

    public void init() {
        GridBagLayout myGridBagLayout = new GridBagLayout();
        setLayout(myGridBagLayout);

        add(id, new CustomConstraints(0, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        add(name, new CustomConstraints(1, 0, GridBagConstraints.WEST, 0.2, 0.0, GridBagConstraints.HORIZONTAL));
        add(desc, new CustomConstraints(2, 0, GridBagConstraints.WEST, 0.7, 0.0, GridBagConstraints.HORIZONTAL));
        add(len, new CustomConstraints(3, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        add(remaining, new CustomConstraints(4, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
        add(numUserStories, new CustomConstraints(5, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));

        // Add the new button at the end
        add(viewDetailsButton, new CustomConstraints(6, 0, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
    }

    // Method to add an action listener to the button
    public void addViewDetailsButtonListener(ActionListener listener) {
        viewDetailsButton.addActionListener(listener);
    }
}
