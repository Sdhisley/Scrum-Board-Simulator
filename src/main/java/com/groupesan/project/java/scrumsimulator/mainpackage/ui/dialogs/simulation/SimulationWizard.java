package com.groupesan.project.java.scrumsimulator.mainpackage.ui.dialogs.simulation;

import com.groupesan.project.java.scrumsimulator.mainpackage.core.Player;
import com.groupesan.project.java.scrumsimulator.mainpackage.core.ScrumRole;
import com.groupesan.project.java.scrumsimulator.mainpackage.core.Simulation;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.utils.DataModel;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.Wizard;
import com.groupesan.project.java.scrumsimulator.mainpackage.ui.widgets.WizardHandler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SimulationWizard extends Wizard<Simulation> {
    private DataModel<String> simulationName;
    private DataModel<Object> sprintCount;
    private DataModel<List<ScrumRole>> roles;
    private DataModel<List<Player>> users;

    public SimulationWizard(WizardHandler<Simulation> handler) {
        super(handler);
        setTitle("New Simulation");
    }

    @Override
    protected void initDataModels() {
        this.simulationName = new DataModel<>("New Simulation");
        this.sprintCount = new DataModel<>(1);
        this.roles = new DataModel<>(new ArrayList<>());
        this.users = new DataModel<>(new ArrayList<>());
    }

    @Override
    protected List<WizardPage> build() {
        // Create pages and return them as a list
        return Arrays.asList(
                new GeneralPage(simulationName, sprintCount), // Pass simulationName and sprintCount to GeneralPage
                new RolesPage(roles) // Pass roles to RolesPage with single-parameter constructor
        );
    }

    @Override
    protected Simulation process() {
        // Create a new Simulation instance with the collected data
        Simulation simulation = new Simulation(simulationName.getData(), null, (Integer) sprintCount.getData());
        
        // Register each user and add to the simulation
        for (Player player : users.getData()) {
            player.doRegister();
            simulation.addPlayer(player);
        }
        
        return simulation;
    }
}
