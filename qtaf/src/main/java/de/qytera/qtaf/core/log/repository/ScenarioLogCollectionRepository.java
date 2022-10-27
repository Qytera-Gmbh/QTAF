package de.qytera.qtaf.core.log.repository;

import de.qytera.qtaf.core.log.model.collection.ScenarioLogCollectionIndex;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;

/**
 * Helper class for finding scenario logs
 */
public class ScenarioLogCollectionRepository {
    /**
     * Index that holds scenario logs
     */
    private static ScenarioLogCollectionIndex index = ScenarioLogCollectionIndex.getInstance();

    /**
     * Find scenario log collection by scenario id
     * @param scenarioId    ID of scenario
     * @return  scenario log if exists, null otherwise
     */
    public static TestScenarioLogCollection findById(String scenarioId) {
        return index.get(scenarioId);
    }
}
