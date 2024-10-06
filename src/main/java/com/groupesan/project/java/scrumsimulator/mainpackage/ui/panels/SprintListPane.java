package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.SprintWidget;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

public class SprintListPane extends JFrame implements BaseComponent {
    public SprintListPane() {
        this.init();
    }

    private List<SprintWidget> widgets = new ArrayList<>();

    @Override
    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Sprints list");
        setSize(400, 300);

        GridBagLayout myGridbagLayout = new GridBagLayout();
        JPanel myJpanel = new JPanel();
        myJpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        myJpanel.setLayout(myGridbagLayout);

        // Sprint aSprint = SprintFactory.getSprintFactory().createNewSprint("foo", "bar", 2, 2);
        // Sprint aSprint2 = SprintFactory.getSprintFactory().createNewSprint("foo2", "bar2", 4, 5);
        // widgets.add(new SprintWidget(aSprint));
        // widgets.add(new SprintWidget(aSprint2));

        for (Sprint sprint : SprintStore.getInstance().getSprints()) {
            widgets.add(new SprintWidget(sprint));
        }

        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridBagLayout());
        int i = 0;
        for (SprintWidget widget : widgets) {
            subPanel.add(
                    widget,
                    new CustomConstraints(
                            0,
                            i++,
                            GridBagConstraints.WEST,
                            1.0,
                            0.1,
                            GridBagConstraints.HORIZONTAL));
        }

        myJpanel.add(
                new JScrollPane(subPanel),
                new CustomConstraints(
                        0, 0, GridBagConstraints.WEST, 1.0, 0.8, GridBagConstraints.HORIZONTAL));

        JButton newSprintButton = new JButton("New Sprint");
        newSprintButton.addActionListener((ActionEvent e) -> {
            NewSprintForm form = new NewSprintForm();
            form.setVisible(true);
            
            form.addWindowListener(
                    new java.awt.event.WindowAdapter() {
                        @Override
                        public void windowClosed(
                                java.awt.event.WindowEvent windowEvent) {
                            Sprint newSprint = form.getSprintObject();
                            widgets.add(new SprintWidget(newSprint));
                            int idx = widgets.size() - 1;
                            subPanel.add(
                                    widgets.get(idx),
                                    new CustomConstraints(
                                            0,
                                            idx,
                                            GridBagConstraints.WEST,
                                            1.0,
                                            0.1,
                                            GridBagConstraints.HORIZONTAL));
                        }
                    });
        });
        myJpanel.add(
                newSprintButton,
                new CustomConstraints(
                        0, 1, GridBagConstraints.WEST, 1.0, 0.2, GridBagConstraints.HORIZONTAL));

        add(myJpanel);
    }
}
