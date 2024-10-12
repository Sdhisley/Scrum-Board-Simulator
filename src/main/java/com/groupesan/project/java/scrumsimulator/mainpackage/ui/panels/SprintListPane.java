package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.JList;
import javax.swing.JOptionPane;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.Sprint;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.SprintStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.SprintWidget;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

public class SprintListPane extends JFrame implements BaseComponent {

    private List<SprintWidget> widgets = new ArrayList<>();
    private JPanel subPanel;

    public SprintListPane() {
        this.init();
    }

    @Override
    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("Sprints list");
        setSize(600, 400);  

        GridBagLayout myGridbagLayout = new GridBagLayout();
        JPanel myJpanel = new JPanel();
        myJpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        myJpanel.setLayout(myGridbagLayout);

        subPanel = new JPanel();
        subPanel.setLayout(new GridBagLayout());

        
        int i = 0;
        for (Sprint sprint : SprintStore.getInstance().getSprints()) {
            SprintWidget widget = new SprintWidget(sprint);
            widgets.add(widget);

            widget.addViewDetailsButtonListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    displaySprintDetails(sprint);
                }
            });

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
                        0, 0, GridBagConstraints.WEST, 1.0, 0.8, GridBagConstraints.BOTH));

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
                            SprintWidget newWidget = new SprintWidget(newSprint);

                            widgets.add(newWidget);
                            int idx = widgets.size() - 1;
                            subPanel.add(
                                    newWidget,
                                    new CustomConstraints(
                                            0,
                                            idx,
                                            GridBagConstraints.WEST,
                                            1.0,
                                            0.1,
                                            GridBagConstraints.HORIZONTAL));

                            subPanel.revalidate();
                            subPanel.repaint();
                        }
                    });
        });


        JButton editSprintButton = new JButton("Edit Sprint");
        editSprintButton.addActionListener((ActionEvent e) -> {
            List<Sprint> sprints = SprintStore.getInstance().getSprints();
            if (sprints.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No sprints available to edit.");
                return;
            }

  
            JList<Sprint> sprintList = new JList<>(sprints.toArray(new Sprint[0]));
            sprintList.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

            int result = JOptionPane.showConfirmDialog(this, new JScrollPane(sprintList), 
                    "Select a Sprint to Edit", JOptionPane.OK_CANCEL_OPTION);

            if (result == JOptionPane.OK_OPTION && !sprintList.isSelectionEmpty()) {
                Sprint selectedSprint = sprintList.getSelectedValue();
                EditSprintForm editForm = new EditSprintForm(selectedSprint);
                editForm.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Please select a sprint to edit.");
            }
        });

        myJpanel.add(newSprintButton,
                new CustomConstraints(0, 1, GridBagConstraints.WEST, 1.0, 0.2, GridBagConstraints.HORIZONTAL));
        myJpanel.add(editSprintButton,
                new CustomConstraints(0, 2, GridBagConstraints.WEST, 1.0, 0.2, GridBagConstraints.HORIZONTAL));

        add(myJpanel);
    }

    private void displaySprintDetails(Sprint sprint) {
        StringBuilder details = new StringBuilder();
        details.append("Sprint ID: ").append(sprint.getId()).append("\n")
               .append("Name: ").append(sprint.getName()).append("\n")
               .append("Description: ").append(sprint.getDescription()).append("\n")
               .append("Length: ").append(sprint.getLength()).append(" days\n")
               .append("Remaining: ").append(sprint.getDaysRemaining()).append(" days\n")
               .append("Number of User Stories: ").append(sprint.getUserStories().size()).append("\n");
    
        details.append("\nUser Stories:\n");
        for (UserStory userStory : sprint.getUserStories()) {
            details.append("- ").append(userStory.getName())
                   .append(" (").append(userStory.getPointValue()).append(" points)\n")
                   .append(" - Assignment status: ").append(userStory.getAssignStatus())
                   .append("\n");
        }
    
        JOptionPane.showMessageDialog(this, details.toString(), "Sprint Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
}