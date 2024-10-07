package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

public class EditSprintForm extends JFrame {
    private Sprint sprint;

    private JTextField nameField;
    private JTextField durationField;
    private JTextField numberOfSprintsField;
    private JTextField descriptionField;
    private JTextArea userStoriesArea;

    public EditSprintForm(Sprint sprint) {
        this.sprint = sprint;
        this.init();
    }

    private void init() {
        setTitle("Edit Sprint");
        setSize(400, 350);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagLayout layout = new GridBagLayout();
        JPanel panel = new JPanel(layout);

        nameField = new JTextField(sprint.getName(), 20);
        durationField = new JTextField(String.valueOf(sprint.getLength()), 20);
        numberOfSprintsField = new JTextField(String.valueOf(sprint.getNumber()), 20);
        descriptionField = new JTextField(sprint.getDescription(), 20);

        userStoriesArea = new JTextArea(5, 20);
        userStoriesArea.setText(sprint.getUserStoriesAsString());

        panel.add(new JLabel("Sprint Name:"),
                new CustomConstraints(0, 0, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));
        panel.add(nameField,
                new CustomConstraints(1, 0, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        panel.add(new JLabel("Sprint Description:"),
                new CustomConstraints(0, 1, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));
        panel.add(descriptionField,
                new CustomConstraints(1, 1, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        panel.add(new JLabel("Sprint Duration (days):"),
                new CustomConstraints(0, 2, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));
        panel.add(durationField,
                new CustomConstraints(1, 2, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        panel.add(new JLabel("Number of Sprints:"),
                new CustomConstraints(0, 3, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));
        panel.add(numberOfSprintsField,
                new CustomConstraints(1, 3, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        panel.add(new JLabel("User Stories:"),
                new CustomConstraints(0, 4, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));
        panel.add(userStoriesArea,
                new CustomConstraints(1, 4, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));
        JButton saveButton = new JButton("Save");
        saveButton.addActionListener((ActionEvent e) -> {
            sprint.setName(nameField.getText());
            sprint.setDescription(descriptionField.getText());
            sprint.setLength(Integer.parseInt(durationField.getText()));
            sprint.setNumber(Integer.parseInt(numberOfSprintsField.getText()));

            String[] userStoryNames = userStoriesArea.getText().split(",\\s*");
            sprint.clearUserStories();
            for (String name : userStoryNames) {
                UserStory userStory = new UserStory(name.trim(), 1.0, 1.0);
                sprint.addUserStory(userStory);
            }

            dispose();
        });

        panel.add(saveButton,
                new CustomConstraints(0, 5, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        add(panel);
        
    }
}
