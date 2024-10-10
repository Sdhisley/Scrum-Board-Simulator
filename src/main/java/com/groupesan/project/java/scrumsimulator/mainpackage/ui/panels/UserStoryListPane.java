package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.UserStoryWidget;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import javax.swing.*;
import javax.swing.border.EmptyBorder; 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class UserStoryListPane extends JFrame implements BaseComponent {
    public UserStoryListPane() {
        this.init();
    }

    private List<UserStoryWidget> widgets = new ArrayList<>();

    @Override
    public void init() {
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setTitle("User Story List");
        setSize(400, 300);

        GridBagLayout myGridbagLayout = new GridBagLayout();
        JPanel myJpanel = new JPanel();
        myJpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        myJpanel.setLayout(myGridbagLayout);

        
        for (UserStory userStory : UserStoryStore.getInstance().getUserStories()) {
            widgets.add(new UserStoryWidget(userStory));
        }

        
        JPanel subPanel = new JPanel();
        subPanel.setLayout(new GridBagLayout());
        int i = 0;

        
        for (UserStoryWidget widget : widgets) {
            subPanel.add(
                widget,
                new CustomConstraints(
                    0,
                    i++,
                    GridBagConstraints.WEST,
                    1.0,
                    0.05, 
                    GridBagConstraints.HORIZONTAL)
            );
        }

        JScrollPane scrollPane = new JScrollPane(subPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        myJpanel.add(
            scrollPane,
            new CustomConstraints(
                0, 0, GridBagConstraints.WEST, 1.0, 0.8, GridBagConstraints.BOTH)
        );

        JButton newSprintButton = new JButton("New User Story");
        newSprintButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                NewUserStoryForm form = new NewUserStoryForm();
                form.setVisible(true);

                form.addWindowListener(new java.awt.event.WindowAdapter() {
                    public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                        UserStory userStory = form.getUserStoryObject();
                        UserStoryStore.getInstance().addUserStory(userStory);
                        UserStoryWidget newWidget = new UserStoryWidget(userStory);
                        widgets.add(newWidget);

                        subPanel.add(newWidget, new CustomConstraints(
                            0,
                            widgets.size() - 1,
                            GridBagConstraints.WEST,
                            1.0,
                            0.05, 
                            GridBagConstraints.HORIZONTAL)
                        );

                        subPanel.revalidate();
                        subPanel.repaint();
                    }
                });
            }
        });

        myJpanel.add(
            newSprintButton,
            new CustomConstraints(
                0, 1, GridBagConstraints.WEST, 1.0, 0.2, GridBagConstraints.HORIZONTAL)
        );

        add(myJpanel);
    }
}
