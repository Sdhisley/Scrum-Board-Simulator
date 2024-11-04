package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofBlocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStory;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.UserStoryStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.SimulationStateManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.SimulationSolution;

public class SimulationPanel extends JPanel implements BaseComponent {

    private SimulationStateManager simulationStateManager;
    private JButton startSimulationButton;
    private JButton stopSimulationButton;
    private static final Random random = new Random();

    /** Simulation Panel Initialization. */
    public SimulationPanel(SimulationStateManager simulationStateManager) {
        this.simulationStateManager = simulationStateManager;
        this.init();
    }

    @Override
    public void init() {
        startSimulationButton = new JButton("Start Simulation");
        stopSimulationButton = new JButton("Stop Simulation");

        stopSimulationButton.setVisible(false);

        startSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                simulationStateManager.startSimulation();
                JOptionPane.showMessageDialog(null, "Simulation started!");
                updateButtonVisibility();
            }
        });

        stopSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (areAllBlockersResolved()) {
                    simulationStateManager.stopSimulation();
                    JOptionPane.showMessageDialog(null, "Simulation stopped!");
                    updateButtonVisibility();
                } else {
                    BlockerStore blockerStore = BlockerStore.getInstance();
                    List<ListofBlocker> blockers = blockerStore.getBlockers();

                    for (ListofBlocker blocker : blockers) {
                        String blockerProbability = blockerStore.getBlockerProbability(blocker);

                        if (blockerProbability != null) {
                            int probabilityValue = Integer.parseInt(blockerProbability.replace("%", "")) / 10;
                            int randomNumber = random.nextInt(10) + 1;

                            if (randomNumber == probabilityValue) {
                                JOptionPane.showMessageDialog(null, "Simulation stopped! Add probability of solution for blocker: " + blocker.getBlockerType());
                                JFrame solutionsFrame = new JFrame("List of Solutions for Blockers");
                                solutionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                solutionsFrame.setSize(800, 400);
                                solutionsFrame.add(new SimulationSolution());
                                solutionsFrame.setVisible(true);
                                return;
                            }
                        }
                    }
                }
            }
        });

        add(startSimulationButton);
        add(stopSimulationButton);
    }

    private boolean areAllBlockersResolved() {
        List<UserStory> userStories = UserStoryStore.getInstance().getUserStories();
        for (UserStory userStory : userStories) {
            if (!userStory.isBlockerResolved()) {
                return false;
            }
        }
        return true;
    }

    private void updateButtonVisibility() {
        if (simulationStateManager.isRunning()) {
            stopSimulationButton.setVisible(true);
            startSimulationButton.setVisible(false);
        } else {
            stopSimulationButton.setVisible(false);
            startSimulationButton.setVisible(true);
        }
    }
}
