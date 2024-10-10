package com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofBlocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ListofBlockersWidget extends JPanel implements BaseComponent {

    private List<ListofBlocker> blockers;

    public ListofBlockersWidget() {
        blockers = new ArrayList<>();
        initBlockers();
        this.init();
    }
    private void initBlockers() {
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.TIME_BLOCKER));
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.TECHNICAL_DEBT));
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.NEEDS_MORE_INFO));
        blockers.add(new ListofBlocker(ListofBlocker.BlockerType.REQUIREMENTS_ISSUE));
    }

    public void init() {
        removeAll();
        setLayout(new GridBagLayout());

        int row = 0;
        for (ListofBlocker blocker : blockers) {
            JLabel blockerTitle = new JLabel(blocker.getBlockerType().toString());
            add(blockerTitle, new CustomConstraints(0, row, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
            row++;
        }

        JButton addUserStoryButton = new JButton("Assign User Story");
        addUserStoryButton.addActionListener(e -> assignUserStory());
        add(addUserStoryButton, new CustomConstraints(0, row, GridBagConstraints.WEST, 0.1, 0.0, GridBagConstraints.HORIZONTAL));
    }

    private void assignUserStory() {
    System.out.println("clicked assign user Story button");
    }
}