package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

public class EditSprintForm extends JFrame {
    private Sprint sprint;

    private JTextField nameField;
    private JTextField durationField;

    public EditSprintForm(Sprint sprint) {
        this.sprint = sprint;
        this.init();
    }

    private void init() {
        setTitle("Edit Sprint");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagLayout layout = new GridBagLayout();
        JPanel panel = new JPanel(layout);

        nameField = new JTextField(sprint.getName(), 20);
        durationField = new JTextField(String.valueOf(sprint.getLength()), 20); // Using length

        panel.add(new JLabel("Sprint Name:"),
                new CustomConstraints(0, 0, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));
        panel.add(nameField,
                new CustomConstraints(1, 0, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        panel.add(new JLabel("Sprint Duration (days):"),
                new CustomConstraints(0, 1, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));
        panel.add(durationField,
                new CustomConstraints(1, 1, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        JButton saveButton = new JButton("Save");
        saveButton.addActionListener((ActionEvent e) -> {
            sprint.setName(nameField.getText());
            sprint.setLength(Integer.parseInt(durationField.getText())); // Setting length
            dispose(); // Close the form
        });

        panel.add(saveButton,
                new CustomConstraints(0, 2, GridBagConstraints.WEST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        add(panel);
    }
}
