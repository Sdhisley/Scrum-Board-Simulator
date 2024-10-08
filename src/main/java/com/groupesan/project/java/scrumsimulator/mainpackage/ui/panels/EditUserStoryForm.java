package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.UserStoryDeletedState;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

public class EditUserStoryForm extends JFrame implements BaseComponent {

    Double[] pointsList = {1.0, 2.0, 3.0, 5.0, 8.0, 11.0, 19.0, 30.0, 49.0};
    Double [] bvList = {0.0, 1.0, 3.0, 7.0, 11.0, 17.0, 23.0};

    public EditUserStoryForm(UserStory userStory) {
        this.userStory = userStory;
        this.init();
    }

    private UserStory userStory;

    private JTextField nameField = new JTextField();
    private JTextArea descArea = new JTextArea();
    private JComboBox<Double> pointsCombo = new JComboBox<>(pointsList);
    private JComboBox<Double> bvCombo = new JComboBox<>(bvList);

    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Edit User Story " + userStory.getId().toString());
        setSize(500, 400);

        nameField = new JTextField(userStory.getName());
        descArea = new JTextArea(userStory.getDescription());
        pointsCombo = new JComboBox<>(pointsList);
        pointsCombo.setSelectedItem(userStory.getPointValue());
        bvCombo = new JComboBox<>(bvList);
        bvCombo.setSelectedItem(userStory.getBusinessValue());

        GridBagLayout myGridbagLayout = new GridBagLayout();
        JPanel myJpanel = new JPanel();
        myJpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        myJpanel.setLayout(myGridbagLayout);

        BorderLayout myBorderLayout = new BorderLayout();

        setLayout(myBorderLayout);

        JLabel nameLabel = new JLabel("Name:");
        myJpanel.add(
                nameLabel,
                new CustomConstraints(
                        0, 0, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(
                nameField,
                new CustomConstraints(
                        1, 0, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));

        JLabel descLabel = new JLabel("Description:");
        myJpanel.add(
                descLabel,
                new CustomConstraints(
                        0, 1, GridBagConstraints.NORTHWEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(
                new JScrollPane(descArea),
                new CustomConstraints(
                        1, 1, GridBagConstraints.EAST, 1.0, 0.3, GridBagConstraints.BOTH));

        JLabel pointsLabel = new JLabel("Points:");
        myJpanel.add(
                pointsLabel,
                new CustomConstraints(
                        0, 2, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
        myJpanel.add(
                pointsCombo,
                new CustomConstraints(
                        1, 2, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));
        JLabel bvLabel = new JLabel("BV (Business Value)");
        myJpanel.add(
                bvLabel,
                new CustomConstraints(
                       0,3, GridBagConstraints.WEST, GridBagConstraints.HORIZONTAL));
                       myJpanel.add(
                        bvCombo,
                        new CustomConstraints(
                               1, 3, GridBagConstraints.EAST, 1.0, 0.0, GridBagConstraints.HORIZONTAL));
                               
        JButton cancelButton = new JButton("Cancel");

        cancelButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        dispose();
                    }
                });

        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String name = nameField.getText();
                        String description = descArea.getText();
                        Double points = (Double) pointsCombo.getSelectedItem();

                        userStory.setName(name);
                        userStory.setDescription(description);
                        userStory.setPointValue(points);
                        dispose();
                    }
                });
                JButton deleteButton = new JButton("Delete");
       myJpanel.add(
    deleteButton,
    new CustomConstraints(3, 4, GridBagConstraints.WEST, GridBagConstraints.NONE));
                deleteButton.addActionListener(
    new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            int confirm = JOptionPane.showConfirmDialog(
                    EditUserStoryForm.this,
                    "Are you sure you want to delete this User Story?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );
            
            if (confirm == JOptionPane.YES_OPTION) {
                // Logic to delete the userStory from your storage
                 UserStoryStore userStoryStore = UserStoryStore.getInstance();
                userStoryStore.removeUserStory(userStory);
                userStory.changeState(new UserStoryDeletedState(userStory));
                dispose();
            }
  }

            private void UserStoryDeletedState(UserStory userStory) {
                throw new UnsupportedOperationException("Not supported yet.");
            }
        }
);

        add(myJpanel);

        myJpanel.add(
                cancelButton,
                new CustomConstraints(1, 4, GridBagConstraints.EAST, GridBagConstraints.NONE));
        myJpanel.add(
                submitButton,
                new CustomConstraints(2, 4, GridBagConstraints.WEST, GridBagConstraints.NONE));
    }
}
