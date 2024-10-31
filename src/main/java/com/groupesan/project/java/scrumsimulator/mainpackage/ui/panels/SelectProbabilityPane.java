package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

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
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofSolution.SolutionType;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SolutionStore;

public class SelectProbabilityPane extends JPanel {
    private BlockerStore blockerStore;
    private SolutionStore solutionStore;
    private Map<ListofBlocker, JComboBox<SolutionType>> solutionSelectors;

    public SelectProbabilityPane() {
        blockerStore = BlockerStore.getInstance();
        solutionStore = SolutionStore.getInstance();
        solutionSelectors = new HashMap<>();

        initUI();
    }

    private void initUI() {
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel(new GridLayout(1, 4, 10, 0));

        JPanel blockerTypePanel = new JPanel();
        blockerTypePanel.setLayout(new BoxLayout(blockerTypePanel, BoxLayout.Y_AXIS));
        blockerTypePanel.setBorder(BorderFactory.createTitledBorder("Blocker Type"));

        JPanel blockerprobabilitySelectPanel = new JPanel();
        blockerprobabilitySelectPanel.setLayout(new BoxLayout(blockerprobabilitySelectPanel, BoxLayout.Y_AXIS));
        blockerprobabilitySelectPanel.setBorder(BorderFactory.createTitledBorder("Blocker Probablilty"));

        JPanel solutionTypePanel = new JPanel();
        solutionTypePanel.setLayout(new BoxLayout(solutionTypePanel, BoxLayout.Y_AXIS));
        solutionTypePanel.setBorder(BorderFactory.createTitledBorder("Solution"));

        JPanel solutionProbabilitySelectPanel = new JPanel();
        solutionProbabilitySelectPanel.setLayout(new BoxLayout(solutionProbabilitySelectPanel, BoxLayout.Y_AXIS));
        solutionProbabilitySelectPanel.setBorder(BorderFactory.createTitledBorder("Solution Probability"));

        List<ListofBlocker> blockers = blockerStore.getBlockers();
        for (ListofBlocker blocker : blockers) {
            if (!blocker.getUserStories().isEmpty()) {
                JLabel blockerLabel = new JLabel(blocker.getBlockerType().toString());
                blockerLabel.setFont(new Font("Arial", Font.BOLD, 12));
                blockerTypePanel.add(blockerLabel);
                blockerTypePanel.add(Box.createVerticalStrut(10));

                JComboBox<Integer> blockerProbabilityDropdown = new JComboBox<>(new Integer[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100});
                blockerProbabilityDropdown.setPreferredSize(new Dimension(180, 20));
                blockerProbabilityDropdown.setMaximumSize(new Dimension(180, 20));
                blockerprobabilitySelectPanel.add(blockerProbabilityDropdown);

                JComboBox<SolutionType> solutionDropdown = new JComboBox<>(SolutionType.values());
                solutionDropdown.setPreferredSize(new Dimension(180, 20));
                solutionDropdown.setMaximumSize(new Dimension(180, 20));
                solutionSelectors.put(blocker, solutionDropdown);

                SolutionType savedSolution = solutionStore.getSolutionForBlocker(blocker);
                if (savedSolution != null) {
                    solutionDropdown.setSelectedItem(savedSolution);
                }
                
                solutionDropdown.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        SolutionType selectedSolution = (SolutionType) solutionDropdown.getSelectedItem();
                        solutionStore.addSolutionForBlocker(blocker, selectedSolution); // Store with specific blocker
                    }
                });

                JComboBox<Integer> solutionProbabilityDropdown = new JComboBox<>(new Integer[]{10, 20, 30, 40, 50, 60, 70, 80, 90, 100});
                solutionProbabilityDropdown.setPreferredSize(new Dimension(180, 20));
                solutionProbabilityDropdown.setMaximumSize(new Dimension(180, 20));
                solutionProbabilitySelectPanel.add(solutionProbabilityDropdown);

                blockerTypePanel.add(Box.createVerticalStrut(10));
                blockerprobabilitySelectPanel.add(Box.createVerticalStrut(10));
                solutionTypePanel.add(Box.createVerticalStrut(10));
                solutionProbabilitySelectPanel.add(Box.createVerticalStrut(10));
                solutionTypePanel.add(solutionDropdown);
            }
        }

        mainPanel.add(blockerTypePanel);
        mainPanel.add(blockerprobabilitySelectPanel);
        mainPanel.add(solutionTypePanel);
        mainPanel.add(solutionProbabilitySelectPanel);

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        add(scrollPane, BorderLayout.CENTER);
    }
}
