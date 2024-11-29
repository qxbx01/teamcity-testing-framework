package com.example.teamcity.ui.pages;
//
import com.codeborne.selenide.Condition;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.ui.pages.admin.CreateBasePage;
import com.example.teamcity.ui.pages.admin.CreateProjectPage;

import static com.codeborne.selenide.Selenide.*;

public class BuildConfigurationPage extends CreateBasePage {

    public static BuildConfigurationPage open(String projectId) {
        Selenide.open("/admin/createObjectMenu.html?projectId=" + projectId + "&showMode=createBuildTypeMenu");
        return page(BuildConfigurationPage.class);
    }

    private static SelenideElement errorMessageElement = $("#error_buildTypeName"); // Убедитесь, что селектор корректен
    public static String getErrorMessageText() {
        return errorMessageElement.shouldBe(Condition.visible).getText();
    }

    public BuildConfigurationPage setupBuildConfiguration(String buildConfigName) {
        buildTypeNameInput.setValue(buildConfigName);
        submitButton.click();
        return this;
    }

    public BuildConfigurationPage createForm(String url) {
        new CreateProjectPage().createForm(url);
        return this;
    }
}
