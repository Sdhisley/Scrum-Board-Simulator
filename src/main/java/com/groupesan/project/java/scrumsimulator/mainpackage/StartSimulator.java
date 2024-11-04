package com.groupesan.project.java.scrumsimulator.mainpackage;

import java.util.List;
import java.util.Random;

import javax.swing.JOptionPane;

import com.groupesan.project.java.scrumsimulator.mainpackage.impl.BlockerStore;
import com.groupesan.project.java.scrumsimulator.mainpackage.impl.ListofBlocker;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.App;

public class StartSimulator {
    private static final Random random = new Random();

    public static void main(String[] args) {
        new App().start();
        startSimulation();
    }

    private static void startSimulation() {
        BlockerStore blockerStore = BlockerStore.getInstance();
        List<ListofBlocker> blockers = blockerStore.getBlockers();

        for (ListofBlocker blocker : blockers) {
            String blockerProbability = blockerStore.getBlockerProbability(blocker);

            if (blockerProbability != null) {
                int probabilityValue = Integer.parseInt(blockerProbability.replace("%", "")) / 10;
                int randomNumber = random.nextInt(10) + 1;

                if (randomNumber == probabilityValue) {
                    JOptionPane.showMessageDialog(null, "Simulation stopped! Add probability of solution for blocker: " + blocker.getBlockerType());
                    return;
                }
            }
        }
    }
}
