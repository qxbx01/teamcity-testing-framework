package com.example.teamcity.ui.pages.admin;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import com.example.teamcity.api.models.BuildType;
import com.example.teamcity.ui.pages.ProjectsPage;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.page;
import static com.codeborne.selenide.Condition.visible;

public class CreateProjectPage extends CreateBasePage {
    private static final String PROJECT_SHOW_MODE = "createProjectMenu";

    //private SelenideElement projectNameInput = $("#projectName").shouldBe(visible);

    public static CreateProjectPage open(String projectId) {
        return Selenide.open(CREATE_URL.formatted(projectId, PROJECT_SHOW_MODE), CreateProjectPage.class);
    }

    public CreateProjectPage createForm(String url) {
        baseCreateForm(url);
        return this;
    }

    public void setupProject(String projectName, String buildTypeName) {
        SelenideElement projectNameInput = $("#projectName").shouldBe(visible);  // подождите, пока элемент станет видимым
        projectNameInput.val(projectName);
        buildTypeNameInput.val(buildTypeName);  // убедитесь, что buildTypeNameInput тоже виден
        submitButton.click();
        //return page(ProjectsPage.class);
    }

//    public ProjectsPage setupProject(String projectName, String buildTypeName) {
//        //projectNameInput.val(projectName);
//        buildTypeNameInput.val(buildTypeName);
//        submitButton.click();
//        return page(ProjectsPage.class);
//    }
}