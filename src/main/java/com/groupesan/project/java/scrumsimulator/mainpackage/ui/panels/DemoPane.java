package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.groupesan.project.java.scrumsimulator.mainpackage.core.Player;
import com.groupesan.project.java.scrumsimulator.mainpackage.core.ScrumRole;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.SimulationManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.SimulationStateManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.dialogs.simulation.ParticipantsPage;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils.DataModel;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.ListofBlockersWidget;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.ListofSolutionsWidget;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

import java.util.ArrayList;
import java.util.List;

public class DemoPane extends JFrame implements BaseComponent {

    private Player player = new Player("bob", new ScrumRole("demo"));

    public DemoPane() {
        this.init();
        player.doRegister();
    }

    /**
     * Initialization of Demo Pane. Demonstrates creation of User stories,
     * Sprints, and Simulation start.
     */
    public void init() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setTitle("Demo");
        setSize(1200, 300);

        GridBagLayout myGridbagLayout = new GridBagLayout();
        JPanel myJpanel = new JPanel();
        myJpanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        myJpanel.setLayout(myGridbagLayout);

        // Add existing buttons and components with their functionalities

        JButton sprintsButton = new JButton("Sprints");
        sprintsButton.addActionListener(e -> new SprintListPane().setVisible(true));
        myJpanel.add(sprintsButton, new CustomConstraints(0, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton userStoriesButton = new JButton("User Stories");
        userStoriesButton.addActionListener(e -> new UserStoryListPane().setVisible(true));
        myJpanel.add(userStoriesButton, new CustomConstraints(1, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton updateStoryStatusButton = new JButton("Update User Story Status");
        updateStoryStatusButton.addActionListener(e -> new UpdateUserStoryPanel().setVisible(true));
        myJpanel.add(updateStoryStatusButton, new CustomConstraints(3, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton simulationButton = new JButton("Add User");
        simulationButton.addActionListener(e -> new SimulationPane().setVisible(true));
        myJpanel.add(simulationButton, new CustomConstraints(7, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton modifySimulationButton = new JButton("Modify Simulation");
        modifySimulationButton.addActionListener(e -> {
            SimulationManager simulationManager = new SimulationManager();
            new ModifySimulationPane(simulationManager).setVisible(true);
        });
        myJpanel.add(modifySimulationButton, new CustomConstraints(5, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton joinSimulationButton = new JButton("Join Simulation");
        joinSimulationButton.addActionListener(e -> new SimulationUI().setVisible(true));
        myJpanel.add(joinSimulationButton, new CustomConstraints(6, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton simulationSwitchRoleButton = new JButton("Switch Role");
        simulationSwitchRoleButton.addActionListener(e -> new SimulationSwitchRolePane().setVisible(true));
        myJpanel.add(simulationSwitchRoleButton, new CustomConstraints(1, 1, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        // Add "Participants" button next to "Switch Role"
        JButton participantsButton = new JButton("Participants");
        participantsButton.addActionListener(e -> openParticipantsPage());
        myJpanel.add(participantsButton, new CustomConstraints(2, 1, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton variantSimulationUIButton = new JButton("Variant Simulation UI");
        variantSimulationUIButton.addActionListener(e -> new VariantSimulationUI().setVisible(true));
        myJpanel.add(variantSimulationUIButton, new CustomConstraints(3, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton SprintUIButton = new JButton("US Selection UI");
        SprintUIButton.addActionListener(e -> new SprintUIPane(player).setVisible(true));
        myJpanel.add(SprintUIButton, new CustomConstraints(8, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton blockersButton = new JButton("List of Blockers");
        blockersButton.addActionListener(e -> {
            JFrame blockersFrame = new JFrame("List of Blockers");
            blockersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            blockersFrame.setSize(800, 400);
            blockersFrame.add(new ListofBlockersWidget());
            blockersFrame.setVisible(true);
        });
        myJpanel.add(blockersButton, new CustomConstraints(9, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton solutionsButton = new JButton("Solutions");
        solutionsButton.addActionListener(e -> {
            JFrame solutionsFrame = new JFrame("List of Solutions for Blockers");
            solutionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            solutionsFrame.setSize(800, 400);
            solutionsFrame.add(new ListofSolutionsWidget());
            solutionsFrame.setVisible(true);
        });
        myJpanel.add(solutionsButton, new CustomConstraints(0, 2, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        // Add the panel to the frame
        add(myJpanel);
    }

    private void openParticipantsPage() {
        List<Player> players = new ArrayList<>();
        List<ScrumRole> roles = new ArrayList<>();
        roles.add(new ScrumRole("Developer"));
        roles.add(new ScrumRole("Tester"));
        DataModel<List<Player>> usersDataModel = new DataModel<>(players);
        DataModel<List<ScrumRole>> rolesDataModel = new DataModel<>(roles);

        JFrame participantsFrame = new JFrame("Participants");
        ParticipantsPage participantsPage = new ParticipantsPage(usersDataModel, rolesDataModel);
        participantsFrame.add(participantsPage.render());
        participantsFrame.setSize(800, 600);
        participantsFrame.setLocationRelativeTo(this);
        participantsFrame.setVisible(true);
    }
}