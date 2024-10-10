package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofBlocker;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ListofBlockersWidget extends JPanel implements BaseComponent {

    private List<ListofBlocker> blockers;
    private JTable blockerTable;
    private JButton addUserStoryButton;

    public ListofBlockersWidget() {
        blockers = new ArrayList<>();
        initBlockers();
        this.init();
    }

    private void initBlockers() {
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.TIME_BLOCKER));
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.TECHNICAL_DEBT));
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.PEOPLE_BLOCKER));
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.NEEDS_MORE_INFO));
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.REQUIREMENTS_ISSUE));
    }

    public void init() {
        setLayout(new BorderLayout());

        String[] columnNames = {"Blocker Type", "User Stories"};
        String[][] data = new String[blockers.size()][2];

        for (int i = 0; i < blockers.size(); i++) {
            data[i][0] = blockers.get(i).getBlockerType().toString();
            data[i][1] = "";
        }

        blockerTable = new JTable(data, columnNames);
        blockerTable.setFillsViewportHeight(true);
        blockerTable.setRowHeight(20);
        blockerTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(blockerTable);
        add(scrollPane, BorderLayout.CENTER);

        addUserStoryButton = new JButton("Assign User Story");
        addUserStoryButton.addActionListener(e -> assignUserStory());
        add(addUserStoryButton, BorderLayout.SOUTH);
    }

    private void assignUserStory() {
        System.out.println("clicked assign user Story button");
    }
}