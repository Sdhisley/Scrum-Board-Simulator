package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.UserStoryStateManager;

public class UpdateUserStoryPanel extends JFrame {

    public UpdateUserStoryPanel() {
        init();
    }

    private void init() {
        setTitle("Update User Story Status");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel();
        placeComponents(panel);
        add(panel);

        setLocationRelativeTo(null);
    }

    private void placeComponents(JPanel panel) {
        panel.setLayout(null);

        JLabel userStoryLabel = new JLabel("Select User Story:");
        userStoryLabel.setBounds(10, 20, 120, 25);
        panel.add(userStoryLabel);

        // Get user stories from the UserStoryStore
        List<UserStory> userStories = UserStoryStore.getInstance().getUserStories();
        JComboBox<UserStory> userStoryComboBox = new JComboBox<>(userStories.toArray(new UserStory[0]));
        userStoryComboBox.setBounds(150, 20, 200, 25);
        panel.add(userStoryComboBox);

        JLabel statusLabel = new JLabel("Select Status:");
        statusLabel.setBounds(10, 50, 120, 25);
        panel.add(statusLabel);

        String[] statusOptions = {"New", "In Progress", "Ready for Test", "Completed"};
        JComboBox<String> statusComboBox = new JComboBox<>(statusOptions);
        statusComboBox.setBounds(150, 50, 200, 25);
        panel.add(statusComboBox);

        JButton updateButton = new JButton("Update Status");
        updateButton.setBounds(150, 80, 150, 25);
        panel.add(updateButton);

        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UserStory selectedUserStory = (UserStory) userStoryComboBox.getSelectedItem();
                String selectedStatus = (String) statusComboBox.getSelectedItem();

                 if (selectedUserStory != null && selectedStatus != null) {
                    // Update the status of the selected user story
                    selectedUserStory.setStatus(selectedStatus);

                    // Optionally: Call UserStoryStateManager if you need to manage transitions
                    UserStoryStateManager.updateUserStoryStatus(
                            selectedUserStory.getDescription(), selectedStatus);
                    JOptionPane.showMessageDialog(null, "Status updated successfully!");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Please select a User Story and Status");
                }
            }
        });
    }

    // For testing purposes, you can add a main method to display the panel
    public static void main(String[] args) {
        // Adding some dummy user stories for demonstration
        UserStoryStore.getInstance().addUserStory(new UserStory(
                "Login Feature",
                "User can log in",
                5,
                8,
                "Assigned",
                "New"
        ));
        UserStoryStore.getInstance().addUserStory(new UserStory(
                "Payment Integration",
                "Integrate payment gateway",
                8,
                13,
                "Unassigned",
                "In Progress"
        ));
        UserStoryStore.getInstance().addUserStory(new UserStory(
                "User Profile",
                "User can edit profile",
                3,
                5,
                "Assigned",
                "Ready for Test"
        ));
        UserStoryStore.getInstance().addUserStory(new UserStory(
                "Search Functionality",
                "Implement search feature",
                8,
                13,
                "Unassigned",
                "Completed"
        ));

        // Display the UpdateUserStoryPanel
        SwingUtilities.invokeLater(() -> new UpdateUserStoryPanel().setVisible(true));
    }
}
