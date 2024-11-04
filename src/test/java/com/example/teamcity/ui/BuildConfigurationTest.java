package com.example.teamcity.ui;

import com.example.teamcity.ui.pages.admin.CreateProjectPage;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.ui.pages.BuildConfigurationPage;
import com.example.teamcity.ui.pages.LoginPage;
import org.openqa.selenium.NoSuchElementException;
import org.testng.annotations.Test;
import com.example.teamcity.api.enums.Endpoint;
import com.example.teamcity.api.models.Project;

public class BuildConfigurationTest extends BaseUiTest {
    private static final String REPO_URL = "https://github.com/AlexPshe/spring-core-for-qa";

    @Test(description = "User should be able to create a build configuration successfully")
    public void userCreatesBuildConfiguration() {
        loginAs(testData.getUser());

        var createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS).create(testData.getProject());
        softy.assertNotNull(createdProject, "The project was not created through the API");

        // Открытие страницы создания билд-конфигурации
        BuildConfigurationPage.open(createdProject.getId())
                .createForm(REPO_URL)
                .setupBuildConfiguration(testData.getBuildType().getName());

        // Проверка, что билд-конфигурация создана через API
        var createdBuildType = superUserCheckRequests.<BuildType>getRequest(Endpoint.BUILD_TYPES)
                .read("name:" + testData.getBuildType().getName());
        softy.assertNotNull(createdBuildType, "The build configuration was not created.");
    }

    @Test(description = "User cannot create build configuration without name")
    public void userCannotCreateBuildConfigurationWithoutName() {
        loginAs(testData.getUser());
        var createdProject = superUserCheckRequests.<Project>getRequest(Endpoint.PROJECTS).create(testData.getProject());

        BuildConfigurationPage.open(createdProject.getId())
                .createForm(REPO_URL)
                .setupBuildConfiguration(""); // Передаем пустое имя для теста

        String errorMessage = BuildConfigurationPage.getErrorMessageText();
        softy.assertEquals(errorMessage, "Build configuration name must not be empty", "Error message is not as expected.");
    }
}
