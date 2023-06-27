package de.qytera.testrail.utils;

import de.qytera.qtaf.core.QtafFactory;
import de.qytera.qtaf.core.config.entity.ConfigMap;
import de.qytera.qtaf.core.log.model.collection.TestSuiteLogCollection;
import de.qytera.qtaf.security.aes.AES;
import de.qytera.testrail.annotations.TestRail;
import de.qytera.testrail.config.TestRailConfigHelper;
import de.qytera.testrail.event_subscriber.UploadTestsSubscriber;
import org.json.simple.JSONObject;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.util.Map;

/**
 * Testrail upload subscriber tests
 */
public class UploadTestsSubscriberTest {
    @Test(description = "Test the setup function")
    public void testClientSetup() throws GeneralSecurityException {
        ConfigMap config = QtafFactory.getConfiguration();
        config.setString(TestRailConfigHelper.TESTRAIL_URL, "http://www.inet.com");
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID, AES.encrypt("Jane", "my-key"));
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET, AES.encrypt("mypass", "my-key"));
        config.setString(TestRailConfigHelper.SECURITY_KEY, "my-key");
        UploadTestsSubscriber subscriber = new UploadTestsSubscriber();
        subscriber.setUpClient();
        Assert.assertEquals(subscriber.getClient().getM_url(), "http://www.inet.com/index.php?/api/v2/");
        Assert.assertEquals(subscriber.getClient().getM_user(), "Jane");
        Assert.assertEquals(subscriber.getClient().getM_password(), "mypass");
    }

    @Test(description = "Test the onFinishedTesting event handler")
    public void testOnFinishedTestingActivated() throws GeneralSecurityException {
        ConfigMap config = QtafFactory.getConfiguration();
        config.setString(TestRailConfigHelper.TESTRAIL_URL, "http://www.inet.com");
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID, AES.encrypt("Jane", "my-key"));
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET, AES.encrypt("mypass", "my-key"));
        config.setString(TestRailConfigHelper.SECURITY_KEY, "my-key");
        config.setBoolean(TestRailConfigHelper.TESTRAIL_ENABLED, true);

        TestSuiteLogCollection logCollection = Mockito.mock(TestSuiteLogCollection.class);

        try (MockedStatic<TestSuiteLogCollection> suiteLogCollectionMock = Mockito.mockStatic(TestSuiteLogCollection.class)) {
            suiteLogCollectionMock.when(TestSuiteLogCollection::getInstance).thenReturn(logCollection);
            UploadTestsSubscriber subscriber = new UploadTestsSubscriber();
            subscriber.setUpClient();
            subscriber.onFinishedTesting("");
            Mockito.verify(logCollection, Mockito.times(1)).getTestFeatureLogCollections();
        }
    }

    @Test(description = "Test the onFinishedTesting event handler")
    public void testOnFinishedTestingDeactivated() throws GeneralSecurityException {
        ConfigMap config = QtafFactory.getConfiguration();
        config.setString(TestRailConfigHelper.TESTRAIL_URL, "http://www.inet.com");
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID, AES.encrypt("Jane", "my-key"));
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET, AES.encrypt("mypass", "my-key"));
        config.setString(TestRailConfigHelper.SECURITY_KEY, "my-key");
        config.setBoolean(TestRailConfigHelper.TESTRAIL_ENABLED, false);

        TestSuiteLogCollection logCollection = Mockito.mock(TestSuiteLogCollection.class);

        try (MockedStatic<TestSuiteLogCollection> suiteLogCollectionMock = Mockito.mockStatic(TestSuiteLogCollection.class)) {
            suiteLogCollectionMock.when(TestSuiteLogCollection::getInstance).thenReturn(logCollection);
            UploadTestsSubscriber subscriber = new UploadTestsSubscriber();
            subscriber.setUpClient();
            subscriber.onFinishedTesting("");
            Mockito.verify(logCollection, Mockito.times(0)).getTestFeatureLogCollections();
        }
    }

    @Test
    public void testHandleScenarioSuccess() throws NoSuchMethodException, GeneralSecurityException {
        ConfigMap config = QtafFactory.getConfiguration();
        config.setString(TestRailConfigHelper.TESTRAIL_URL, "http://www.inet.com");
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_ID, AES.encrypt("Jane", "my-key"));
        config.setString(TestRailConfigHelper.TESTRAIL_AUTHENTICATION_CLIENT_SECRET, AES.encrypt("mypass", "my-key"));
        config.setString(TestRailConfigHelper.SECURITY_KEY, "my-key");
        config.setBoolean(TestRailConfigHelper.TESTRAIL_ENABLED, true);

        DemoScenario demoScenario = new DemoScenario();
        Method method = demoScenario.getClass().getMethod("demoTestMethod");
        TestRail testRailAnnotation = method.getAnnotation(TestRail.class);
        UploadTestsSubscriber subscriber = new UploadTestsSubscriber();
        subscriber.setUpClient();

        JSONObject jsonObject = new JSONObject(Map.of("key1", "val1"));

        try (MockedStatic<TestRailManager> manager = Mockito.mockStatic(TestRailManager.class)) {
            // Mock TestRailManager methods
            manager
                    .when(() -> TestRailManager.addAttachmentForTestCase(Mockito.any(APIClient.class), Mockito.anyString(), Mockito.anyString()))
                    .thenAnswer(invocation -> null);

            manager
                    .when(() -> TestRailManager.addResultForTestCase(Mockito.any(APIClient.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(),  Mockito.anyString()))
                    .thenAnswer(invocation -> null);
            manager
                    .when(() -> TestRailManager.deleteAttachmentForTestCase(Mockito.any(APIClient.class), Mockito.anyString()))
                    .thenAnswer(invocation -> null);
            manager
                    .when(() -> TestRailManager.getAttachmentForTestCase(Mockito.any(APIClient.class), Mockito.anyString()))
                    .thenReturn(jsonObject);

            // Call subscriber method
            subscriber.handleScenarioSuccess(testRailAnnotation);

            // Check how many times manager methods were called
            manager.verify(
                    () -> TestRailManager.addAttachmentForTestCase(Mockito.any(APIClient.class), Mockito.anyString(), Mockito.anyString()),
                    Mockito.times(0)
            );

            manager.verify(
                    () -> TestRailManager.addResultForTestCase(Mockito.any(APIClient.class), Mockito.anyString(), Mockito.anyString(), Mockito.anyInt(),  Mockito.anyString()),
                    Mockito.times(1)
            );

            manager.verify(
                    () -> TestRailManager.deleteAttachmentForTestCase(Mockito.any(APIClient.class), Mockito.anyString()),
                    Mockito.times(0)
            );

            manager.verify(
                    () -> TestRailManager.getAttachmentForTestCase(Mockito.any(APIClient.class), Mockito.anyString()),
                    Mockito.times(1)
            );
        }
    }
}

class CaseIds {
    public static final String CASE1 = "c1";
}

class RunIds {
    public static final String RUN1 = "1";
}

class DemoScenario {
    @TestRail(caseId = {CaseIds.CASE1}, runId = RunIds.RUN1)
    public void demoTestMethod() {

    }
}