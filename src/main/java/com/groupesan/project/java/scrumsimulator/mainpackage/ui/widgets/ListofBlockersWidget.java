package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofBlocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;

import javax.swing.*;
import java.awt.*;

public class ListofBlockersWidget extends JPanel implements BaseComponent {

    private BlockerStore blockerStore;
    private JTable blockerTable;
    private JButton addUserStoryButton;
    private JButton clearAssignmentButton;
    private JButton startSpikeButton;
    private DefaultListModel<String> userStoryListModel;
    private JList<String> userStoryList;

    public ListofBlockersWidget() {
        blockerStore = BlockerStore.getInstance();
        initUserStories();
        this.init();
    }

    private void initUserStories() {
        userStoryListModel = new DefaultListModel<>();
        for (UserStory userStory : UserStoryStore.getInstance().getUserStories()) {
            userStoryListModel.addElement(userStory.toString());
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

        startSpikeButton = new JButton("Start Spike");
        startSpikeButton.setEnabled(false);
        startSpikeButton.addActionListener(e -> startSpike());

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.X_AXIS));
        buttonPanel.add(addUserStoryButton);
        buttonPanel.add(clearAssignmentButton);
        buttonPanel.add(startSpikeButton);
        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void assignUserStory() {
        int selectedBlockerRow = blockerTable.getSelectedRow();
        int selectedUserStoryIndex = userStoryList.getSelectedIndex();

        if (selectedBlockerRow >= 0 && selectedUserStoryIndex >= 0) {
            ListofBlocker selectedBlocker = blockerStore.getBlockers().get(selectedBlockerRow);

            if (selectedBlocker.getBlockerType() == ListofBlocker.BlockerType.NEEDS_MORE_INFO 
                    && !selectedBlocker.getUserStories().isEmpty()) {
                JOptionPane.showMessageDialog(
                    this, 
                    "Please resolve the current spike before assigning another user story."
                );
                return;
            }

            String selectedUserStory = userStoryListModel.get(selectedUserStoryIndex);
            selectedBlocker.addUserStory(selectedUserStory);
            blockerTable.setValueAt(String.join(", ", selectedBlocker.getUserStories()), selectedBlockerRow, 1);
            updateStartSpikeButton();
        } else {
            JOptionPane.showMessageDialog(this, "Please select a blocker and a user story.");
        }
    }

    private void clearAssignment() {
        int selectedBlockerRow = blockerTable.getSelectedRow();

        if (selectedBlockerRow >= 0) {
            ListofBlocker selectedBlocker = blockerStore.getBlockers().get(selectedBlockerRow);
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

    private void startSpike() {
        JOptionPane.showMessageDialog(this, "Spike activity started for blocker type 'NEEDS_MORE_INFO'.");
    }
}
