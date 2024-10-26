package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofBlocker;

public class ListofSolutionsWidget extends JPanel {

    private BlockerStore blockerStore;

    public ListofSolutionsWidget() {
        blockerStore = BlockerStore.getInstance();
        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(1, 2, 10, 0));

        JPanel blockerTypePanel = new JPanel();
        blockerTypePanel.setLayout(new BoxLayout(blockerTypePanel, BoxLayout.Y_AXIS));
        blockerTypePanel.setBorder(BorderFactory.createTitledBorder("Blocker Type"));

        JPanel userStoriesPanel = new JPanel();
        userStoriesPanel.setLayout(new BoxLayout(userStoriesPanel, BoxLayout.Y_AXIS));
        userStoriesPanel.setBorder(BorderFactory.createTitledBorder("User Stories"));

        JPanel solutionTypePanel = new JPanel();
        solutionTypePanel.setLayout(new BoxLayout(solutionTypePanel, BoxLayout.Y_AXIS));
        solutionTypePanel.setBorder(BorderFactory.createTitledBorder("Solution"));

        List<ListofBlocker> blockers = blockerStore.getBlockers();
        for (ListofBlocker blocker : blockers) {
            JLabel blockerLabel = new JLabel(blocker.getBlockerType().toString());
            blockerLabel.setFont(new Font("Arial", Font.BOLD, 14));
            blockerTypePanel.add(blockerLabel);
            if (blocker.getUserStories().isEmpty()) {
                JLabel noUserStoriesLabel = new JLabel("No user stories assigned.");
                userStoriesPanel.add(noUserStoriesLabel);
            } else {
                JPanel userStoryGroupPanel = new JPanel();
                userStoryGroupPanel.setLayout(new BoxLayout(userStoryGroupPanel, BoxLayout.Y_AXIS));
                for (String userStory : blocker.getUserStories()) {
                    JLabel userStoryLabel = new JLabel("• " + userStory);
                    userStoryGroupPanel.add(userStoryLabel);
                }
                userStoriesPanel.add(userStoryGroupPanel);
            }
        }
        mainPanel.add(blockerTypePanel);
        mainPanel.add(userStoriesPanel);
        mainPanel.add(solutionTypePanel);
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);
    }
}