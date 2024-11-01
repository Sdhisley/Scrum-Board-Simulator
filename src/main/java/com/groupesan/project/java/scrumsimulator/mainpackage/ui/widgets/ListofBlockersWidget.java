package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import java.awt.BorderLayout;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofBlocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofSolution.SolutionType;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SolutionStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;

public class ListofBlockersWidget extends JPanel implements BaseComponent {

    private BlockerStore blockerStore;
    private SolutionStore solutionStore;
    private JTable blockerTable;
    private JButton addUserStoryButton;
    private JButton clearAssignmentButton;
    private JButton startSpikeButton;
    private DefaultListModel<String> userStoryListModel;
    private JList<String> userStoryList;

    private Map<UserStory, UserStoryWidget> userStoryWidgetMap;

    public ListofBlockersWidget() {
        blockerStore = BlockerStore.getInstance();
        solutionStore = SolutionStore.getInstance();
        userStoryWidgetMap = new HashMap<>();
        initUserStories();
        this.init();
    }

    private void initUserStories() {
        userStoryListModel = new DefaultListModel<>();
        for (UserStory userStory : UserStoryStore.getInstance().getUserStories()) {
            userStoryListModel.addElement(userStory.getName());
            userStoryWidgetMap.put(userStory, new UserStoryWidget(userStory));
        }
        userStoryList = new JList<>(userStoryListModel);
    }

    public void init() {
        setLayout(new BorderLayout());

        String[] columnNames = {"Blocker Type", "User Stories"};
        String[][] data = new String[blockerStore.getBlockers().size()][2];

        for (int i = 0; i < blockerStore.getBlockers().size(); i++) {
            ListofBlocker blocker = blockerStore.getBlockers().get(i);
            data[i][0] = blocker.getBlockerType().toString();
            data[i][1] = String.join(", ", blocker.getUserStories());

            // Fetching solutions for the blocker
            SolutionType solution = solutionStore.getSolutionForBlocker(blocker);
            if (solution != null) {
                System.out.println("Blocker: " + blocker.getBlockerType() + " | Solution: " + solution);
            } else {
                System.out.println("Blocker: " + blocker.getBlockerType() + " | No Solution Assigned");
            }
        }

        blockerTable = new JTable(data, columnNames);
        blockerTable.setFillsViewportHeight(true);
        blockerTable.setRowHeight(20);
        blockerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        blockerTable.getSelectionModel().addListSelectionListener(e -> updateStartSpikeButton());
        JScrollPane scrollPane = new JScrollPane(blockerTable);
        add(scrollPane, BorderLayout.CENTER);

        JPanel userStoryPanel = new JPanel(new BorderLayout());
        userStoryPanel.add(new JLabel("Available User Stories:"), BorderLayout.NORTH);
        userStoryPanel.add(new JScrollPane(userStoryList), BorderLayout.CENTER);
        add(userStoryPanel, BorderLayout.EAST);

        addUserStoryButton = new JButton("Assign User Story");
        addUserStoryButton.addActionListener(e -> assignUserStory());

        clearAssignmentButton = new JButton("Clear Assignment");
        clearAssignmentButton.addActionListener(e -> clearAssignment());

        startSpikeButton = new JButton(blockerStore.isSpikeActive() ? "Stop Spike" : "Start Spike");
        startSpikeButton.setEnabled(false);
        startSpikeButton.addActionListener(e -> toggleSpike());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(addUserStoryButton);
        buttonPanel.add(clearAssignmentButton);
        buttonPanel.add(startSpikeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void toggleSpike() {
        if (blockerStore.isSpikeActive()) {
            stopSpike();
        } else {
            startSpike();
        }
    }

    private void startSpike() {
        JOptionPane.showMessageDialog(this, "Spike activity started for blocker type 'NEEDS_MORE_INFO'.");
        blockerStore.setSpikeActive(true);
        startSpikeButton.setText("Stop Spike");
    }

    private void stopSpike() {
        JOptionPane.showMessageDialog(this, "Spike activity stopped.");
        blockerStore.setSpikeActive(false);
        startSpikeButton.setText("Start Spike");
    }

    private void assignUserStory() {
        int selectedBlockerRow = blockerTable.getSelectedRow();
        int selectedUserStoryIndex = userStoryList.getSelectedIndex();

        // Remove blocker from each UserStory in the blocker
        if (selectedBlockerRow >= 0 && selectedUserStoryIndex >= 0) {
            ListofBlocker selectedBlocker = blockerStore.getBlockers().get(selectedBlockerRow);
            String selectedUserStoryName = userStoryListModel.get(selectedUserStoryIndex);

            UserStory selectedUserStory = UserStoryStore.getInstance().findUserStoryByName(selectedUserStoryName); 
            if (selectedUserStory != null) {
                selectedUserStory.addBlocker(selectedBlocker.getBlockerType().toString());

                UserStoryWidget userStoryWidget = findUserStoryWidget(selectedUserStory);
                if (userStoryWidget != null) {
                    userStoryWidget.setBlockerLabel(selectedBlocker.getBlockerType().toString());
                }

                selectedBlocker.addUserStory(selectedUserStoryName);
                blockerTable.setValueAt(String.join(", ", selectedBlocker.getUserStories()), selectedBlockerRow, 1);
                updateStartSpikeButton();
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a blocker and a user story.");
        }
    }

    private void clearAssignment() {
        int selectedBlockerRow = blockerTable.getSelectedRow();

        if (selectedBlockerRow >= 0) {
            ListofBlocker selectedBlocker = blockerStore.getBlockers().get(selectedBlockerRow);
            
            for (String userStoryName : selectedBlocker.getUserStories()) {
                UserStory userStory = UserStoryStore.getInstance().findUserStoryByName(userStoryName);
                if (userStory != null) {
                    userStory.removeBlocker(selectedBlocker.getBlockerType().toString());// Remove blocker

                    UserStoryWidget userStoryWidget = findUserStoryWidget(userStory);
                    if (userStoryWidget != null) {
                        userStoryWidget.setBlockerLabel(userStory.getBlockers().isEmpty() ? "No Blocker" : String.join(", ", userStory.getBlockers()));
                    }
                }
            }

             // Clear assignments in the blocker
            selectedBlocker.getUserStories().clear();
            blockerTable.setValueAt("", selectedBlockerRow, 1);
            updateStartSpikeButton();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a blocker to clear the assignment.");
        }
    }

    private void updateStartSpikeButton() {
        int selectedRow = blockerTable.getSelectedRow();
        if (selectedRow >= 0) {
            ListofBlocker selectedBlocker = blockerStore.getBlockers().get(selectedRow);
            boolean isNeedsMoreInfo = selectedBlocker.getBlockerType() == ListofBlocker.BlockerType.NEEDS_MORE_INFO;
            boolean hasUserStories = !selectedBlocker.getUserStories().isEmpty();
            startSpikeButton.setEnabled(isNeedsMoreInfo && hasUserStories);
        } else {
            startSpikeButton.setEnabled(false);
        }
    }

    private UserStoryWidget findUserStoryWidget(UserStory userStory) {
        return userStoryWidgetMap.get(userStory);  // Retrieves UserStoryWidget by UserStory
    }
}