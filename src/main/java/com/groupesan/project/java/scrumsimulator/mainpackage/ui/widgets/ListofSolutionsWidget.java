package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofBlocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofSolution;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofSolution.SolutionType;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SolutionStore;

public class ListofSolutionsWidget extends JPanel {

    private BlockerStore blockerStore;
    private SolutionStore solutionStore;
    private Map<ListofBlocker, JComboBox<SolutionType>> solutionSelectors;

    public ListofSolutionsWidget() {
        blockerStore = BlockerStore.getInstance();
        solutionStore = SolutionStore.getInstance();
        solutionSelectors = new HashMap<>();

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(1, 3, 10, 0));

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
            if (!blocker.getUserStories().isEmpty()) {
                JLabel blockerLabel = new JLabel(blocker.getBlockerType().toString());
                blockerLabel.setFont(new Font("Arial", Font.BOLD, 12));
                blockerTypePanel.add(blockerLabel);
                blockerTypePanel.add(Box.createVerticalStrut(10));

                JPanel userStoryGroupPanel = new JPanel();
                userStoryGroupPanel.setLayout(new BoxLayout(userStoryGroupPanel, BoxLayout.Y_AXIS));
                for (String userStory : blocker.getUserStories()) {
                    JLabel userStoryLabel = new JLabel("• " + userStory);
                    userStoryGroupPanel.add(userStoryLabel);
                }
                userStoriesPanel.add(userStoryGroupPanel);
                blockerTypePanel.add(Box.createVerticalStrut(10));
                userStoriesPanel.add(Box.createVerticalStrut(10));

                JComboBox<SolutionType> solutionDropdown = new JComboBox<>(SolutionType.values());
                solutionDropdown.setPreferredSize(new Dimension(180, 20));
                solutionDropdown.setMaximumSize(new Dimension(180, 20));
                solutionSelectors.put(blocker, solutionDropdown);
                
                solutionDropdown.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SolutionType selectedSolution = (SolutionType) solutionDropdown.getSelectedItem();
                        solutionStore.addSolutionForBlocker(blocker, selectedSolution); // Store with specific blocker
                    }
                });

                solutionTypePanel.add(Box.createVerticalStrut(15));
                solutionTypePanel.add(solutionDropdown);
            }
        }

        mainPanel.add(blockerTypePanel);
        mainPanel.add(userStoriesPanel);
        mainPanel.add(solutionTypePanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);
    }
}