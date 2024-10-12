package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofBlocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ListofBlockersWidget extends JPanel implements BaseComponent {

    private List<ListofBlocker> blockers;
    private JTable blockerTable;
    private DefaultListModel<String> userStoryListModel;
    private JList<String> userStoryList;

    public ListofBlockersWidget() {
        blockers = new ArrayList<>();
        initBlockers();
        initUserStories();
        this.init();
    }

    private void initBlockers() {
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.TIME_BLOCKER));
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.TECHNICAL_DEBT));
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.PEOPLE_BLOCKER));
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.NEEDS_MORE_INFO));
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.REQUIREMENTS_ISSUE));
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

        
        JPanel userStoryPanel = new JPanel(new BorderLayout());
        userStoryPanel.add(new JLabel("All User Stories:"), BorderLayout.NORTH);
        userStoryPanel.add(new JScrollPane(userStoryList), BorderLayout.CENTER);
        add(userStoryPanel, BorderLayout.EAST);
    }
}