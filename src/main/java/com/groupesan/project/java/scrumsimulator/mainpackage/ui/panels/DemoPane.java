package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.groupesan.project.java.scrumsimulator.mainpackage.core.Player;
import com.groupesan.project.java.scrumsimulator.mainpackage.core.ScrumRole;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.SimulationManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.SimulationStateManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils.WizardManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.ListofBlockersWidget;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.ListofSolutionsWidget;
import com.groupesan.project.java.scrumsimulator.mainpackage.utils.CustomConstraints;

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

        // Button to create a new simulation
        JButton rolesButton = new JButton("New Sim");
        rolesButton.addActionListener(
                e -> {
                    WizardManager.get().showSimulationWizard();
                });

        myJpanel.add(
                rolesButton,
                new CustomConstraints(
                        10, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        add(myJpanel);

        // Other existing buttons
        JButton sprintsButton = new JButton("Sprints");
        sprintsButton.addActionListener(
                e -> {
                    SprintListPane form = new SprintListPane();
                    form.setVisible(true);
                });

        SimulationStateManager simulationStateManager = new SimulationStateManager();
        SimulationPanel simulationPanel = new SimulationPanel(simulationStateManager);
        myJpanel.add(
                simulationPanel,
                new CustomConstraints(
                        2, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        myJpanel.add(
                sprintsButton,
                new CustomConstraints(
                        0, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton userStoriesButton = new JButton("User Stories");
        userStoriesButton.addActionListener(
                e -> {
                    UserStoryListPane form = new UserStoryListPane();
                    form.setVisible(true);
                });

        myJpanel.add(
                userStoriesButton,
                new CustomConstraints(
                        1, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton updateStoryStatusButton = new JButton("Update User Story Status");
        updateStoryStatusButton.addActionListener(
                e -> {
                    UpdateUserStoryPanel form = new UpdateUserStoryPanel();
                    form.setVisible(true);
                });

        myJpanel.add(
                updateStoryStatusButton,
                new CustomConstraints(
                        3, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton simulationButton = new JButton("Add User");
        simulationButton.addActionListener(
                e -> {
                    SimulationPane simulationPane = new SimulationPane();
                    simulationPane.setVisible(true);
                });

        myJpanel.add(
                simulationButton,
                new CustomConstraints(
                        7, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton modifySimulationButton = new JButton("Modify Simulation");
        modifySimulationButton.addActionListener(
                e -> {
                    SimulationManager simulationManager = new SimulationManager();
                    ModifySimulationPane modifySimulationPane
                            = new ModifySimulationPane(simulationManager);
                    modifySimulationPane.setVisible(true);
                });

        myJpanel.add(
                modifySimulationButton,
                new CustomConstraints(
                        5, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton joinSimulationButton = new JButton("Join Simulation");
        joinSimulationButton.addActionListener(
                e -> {
                    SimulationUI simulationUserInterface = new SimulationUI();
                    simulationUserInterface.setVisible(true);
                });

        myJpanel.add(
                joinSimulationButton,
                new CustomConstraints(
                        6, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton simulationSwitchRoleButton = new JButton("Switch Role");
        simulationSwitchRoleButton.addActionListener(
                e -> {
                    SimulationSwitchRolePane feedbackPanelUI = new SimulationSwitchRolePane();
                    feedbackPanelUI.setVisible(true);
                });

        myJpanel.add(
                simulationSwitchRoleButton,
                new CustomConstraints(
                        1, 1, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton variantSimulationUIButton = new JButton("Variant Simulation UI");
        variantSimulationUIButton.addActionListener(
                e -> {
                    VariantSimulationUI variantSimulationUI = new VariantSimulationUI();
                    variantSimulationUI.setVisible(true);
                });

        myJpanel.add(
                variantSimulationUIButton,
                new CustomConstraints(
                        3, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton SprintUIButton = new JButton("US Selection UI");
        SprintUIButton.addActionListener(
                e -> {
                    SprintUIPane sprintUIPane = new SprintUIPane(player);
                    sprintUIPane.setVisible(true);
                });

        myJpanel.add(
                SprintUIButton,
                new CustomConstraints(
                        8, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton blockersButton = new JButton("List of Blockers");
        blockersButton.addActionListener(
                e -> {
                    JFrame blockersFrame = new JFrame("List of Blockers");
                    blockersFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    blockersFrame.setSize(800, 400);
                    blockersFrame.add(new ListofBlockersWidget());
                    blockersFrame.setVisible(true);
                });

        myJpanel.add(
            blockersButton,
            new CustomConstraints(
                    9, 0, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));

        JButton solutionsButton = new JButton("Solutions");
        solutionsButton.addActionListener(
                e -> {
                    JFrame solutionsFrame = new JFrame("List of Solutions for Blockers");
                    solutionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    solutionsFrame.setSize(800, 400);
                    solutionsFrame.add(new ListofSolutionsWidget());
                    solutionsFrame.setVisible(true);
                });

        myJpanel.add(
                solutionsButton,
                new CustomConstraints(
                        0, 1, GridBagConstraints.WEST, 1.0, 1.0, GridBagConstraints.HORIZONTAL));
    }
}
