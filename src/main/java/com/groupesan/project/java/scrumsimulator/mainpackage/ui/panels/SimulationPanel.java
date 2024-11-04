package com.groupesan.project.java.scrumsimulator.mainpackage.ui.panels;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingWorker;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofBlocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.state.SimulationStateManager;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.BaseComponent;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.SimulationSolution;

public class SimulationPanel extends JPanel implements BaseComponent {

    private SimulationStateManager simulationStateManager;
    private JButton startSimulationButton;
    private JButton stopSimulationButton;
    private static final Random random = new Random();
    private SwingWorker<Void, Void> simulationWorker;

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
                startBackgroundSimulation();
            }
        });

        stopSimulationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                stopSimulation();
            }
        });

        add(startSimulationButton);
        add(stopSimulationButton);
    }

    private void startBackgroundSimulation() {
        simulationWorker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                BlockerStore blockerStore = BlockerStore.getInstance();
                List<ListofBlocker> blockers = blockerStore.getBlockers();

                while (!isCancelled() && simulationStateManager.isRunning()) {
                    for (ListofBlocker blocker : blockers) {
                        String blockerProbability = blockerStore.getBlockerProbability(blocker);

                        if (blockerProbability != null) {
                            int probabilityValue = Integer.parseInt(blockerProbability.replace("%", "")) / 10;
                            int randomNumber = random.nextInt(10) + 1;

                            if (randomNumber == probabilityValue) {
                                // Stop simulation and show solution dialog
                                stopSimulation();
                                JOptionPane.showMessageDialog(null, "Simulation stopped! Assign a solution for blocker: " + blocker.getBlockerType());
                                openSolutionAssignmentDialog(blocker);
                                return null;
                            }
                        }
                    }
                    // Adding a delay to avoid a tight loop and UI freeze
                    Thread.sleep(1000);
                }
                return null;
            }
        };
        simulationWorker.execute();
    }

    private void stopSimulation() {
        simulationStateManager.stopSimulation();
        if (simulationWorker != null && !simulationWorker.isCancelled()) {
            simulationWorker.cancel(true);
        }
        updateButtonVisibility();
    }

    private void openSolutionAssignmentDialog(ListofBlocker blocker) {
        JFrame solutionsFrame = new JFrame("List of Solutions for Blocker: " + blocker.getBlockerType());
        solutionsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        solutionsFrame.setSize(800, 400);

        SimulationSolution solutionPanel = new SimulationSolution();

        // Set a listener to respond to solution assignment
        solutionPanel.setSolutionAssignmentListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Solution assigned! You may start the simulation again if needed.");
                solutionsFrame.dispose();
            }
        });

        solutionsFrame.add(solutionPanel);
        solutionsFrame.setVisible(true);
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
