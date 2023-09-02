package de.qytera.qtaf.xray.event_subscriber;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.exception.MissingConfigurationValueException;
import de.qytera.qtaf.core.events.QtafEvents;
import de.qytera.qtaf.core.events.payload.QtafTestContextPayload;
import de.qytera.qtaf.core.log.model.collection.TestFeatureLogCollection;
import de.qytera.qtaf.core.log.model.collection.TestScenarioLogCollection;
import de.qytera.qtaf.xray.annotation.XrayTest;
import de.qytera.qtaf.xray.config.XrayConfigHelper;
import de.qytera.qtaf.xray.repository.xray.XrayTestRepository;
import de.qytera.qtaf.xray.repository.xray.XrayTestRepositoryCloud;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.lang.annotation.Annotation;
import java.net.URISyntaxException;
import java.sql.Date;
import java.time.Instant;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;


public class UploadResultSubscriberTest {

    private static final UploadResultsSubscriber uploadResultsSubscriber = new UploadResultsSubscriber();
    private static final XrayTestRepository XRAY_TEST_REPOSITORY = Mockito.mock(XrayTestRepositoryCloud.class);

    @BeforeClass
    public void beforeClass() {
        uploadResultsSubscriber.initialize();
    }

    @BeforeMethod
    public void beforeTest() {
        QtafFactory.getConfiguration().setString(XrayConfigHelper.PROJECT_KEY, "QTAF");
        QtafFactory.getTestSuiteLogCollection().getTestFeatureLogCollections().addAll(createTestFeatures());
    }

    @Test(description = "import execution results should be called exactly once")
    public void testUploadSubscriberEnabled() {
        QtafFactory.getConfiguration().setBoolean(XrayConfigHelper.XRAY_ENABLED, true);
        try (MockedStatic<XrayTestRepository> xrayRepository = Mockito.mockStatic(XrayTestRepository.class)) {
            xrayRepository.when(XrayTestRepository::getInstance).thenReturn(XRAY_TEST_REPOSITORY);
            QtafEvents.finishedTesting.onNext(new QtafTestContextPayload());
            Mockito.verify(XRAY_TEST_REPOSITORY, Mockito.times(1)).importExecutionResults(any());
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            Assert.fail("unexpected error during execution results import", exception);
        }
    }

    @Test(description = "import execution results should be called zero times")
    public void testUploadSubscriberDisabled() {
        QtafFactory.getConfiguration().setBoolean(XrayConfigHelper.XRAY_ENABLED, false);
        try (MockedStatic<XrayTestRepository> xrayRepository = Mockito.mockStatic(XrayTestRepository.class)) {
            xrayRepository.when(XrayTestRepository::getInstance).thenReturn(XRAY_TEST_REPOSITORY);
            QtafEvents.finishedTesting.onNext(new QtafTestContextPayload());
            Mockito.verify(XRAY_TEST_REPOSITORY, Mockito.times(0)).importExecutionResults(any());
        } catch (URISyntaxException | MissingConfigurationValueException exception) {
            Assert.fail("unexpected error during execution results import", exception);
        }
    }

    private static List<TestFeatureLogCollection> createTestFeatures() {
        TestFeatureLogCollection feature = TestFeatureLogCollection.createFeatureLogCollectionIfNotExists(
                "0",
                "this is a test feature"
        );
        TestScenarioLogCollection scenario = TestScenarioLogCollection.createTestScenarioLogCollection(
                "0",
                "someScenario()",
                "0",
                "this is a test scenario"
        );
        scenario.setStart(Date.from(Instant.ofEpochMilli(42)));
        scenario.setEnd(Date.from(Instant.ofEpochMilli(420)));
        scenario.setAnnotations(
                new Annotation[]{
                        new XrayTest() {

                            @Override
                            public Class<? extends Annotation> annotationType() {
                                return XrayTest.class;
                            }

                            @Override
                            public String key() {
                                return "QTAF-123";
                            }

                            @Override
                            public boolean scenarioReport() {
                                return false;
                            }

                            @Override
                            public boolean screenshots() {
                                return false;
                            }
                        }}
        );
        feature.addScenarioLogCollection(scenario);
        return List.of(feature);
    }
}
