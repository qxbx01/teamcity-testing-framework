package com.example.teamcity.api;

import com.example.teamcity.api.models.Project;
import com.example.teamcity.api.requests.CheckedRequests;
import com.example.teamcity.api.requests.UncheckedRequests;
import com.example.teamcity.api.requests.unchecked.UncheckedBase;
import com.example.teamcity.api.spec.Specifications;
import org.apache.http.HttpStatus;
import org.testng.annotations.Test;
import static com.example.teamcity.api.enums.Endpoint.PROJECTS;
import static org.hamcrest.Matchers.containsString;

@Test(groups = {"Regression"})
public class ProjectTest extends BaseApiTest {

    @Test(description = "User should be able to create project", groups = {"Positive", "CRUD"})
    public void userCreatesProjectTest() {
        var checkedRequests = new CheckedRequests(Specifications.superUserSpec());
        checkedRequests.getRequest(PROJECTS).create(testData.getProject());

        var createdProject = checkedRequests.<Project>getRequest(PROJECTS).read(testData.getProject().getId());

        softy.assertEquals(testData.getProject().getId(), createdProject.getId(), "Project ID is not correct");

        softy.assertEquals(testData.getProject().getName(), createdProject.getName(), "Project name is not correct");
        softy.assertAll();
    }

    @Test(description = "User should be able to delete a project", groups = {"Positive", "CRUD"})
    public void userDeletesProjectTest() {
        var uncheckedRequests = new UncheckedRequests(Specifications.superUserSpec());

        uncheckedRequests.getRequest(PROJECTS).create(testData.getProject())
                .then().assertThat().statusCode(HttpStatus.SC_OK);

        uncheckedRequests.getRequest(PROJECTS)
                .delete(testData.getProject().getId())
                .then().assertThat().statusCode(HttpStatus.SC_NO_CONTENT);
    }

    @Test(description = "User should not be able to create two projects with the same id", groups = {"Negative", "CRUD"})
    public void userCreatesTwoProjectsWithTheSameIdTest() {
        var checkedRequests = new CheckedRequests(Specifications.superUserSpec());
        checkedRequests.getRequest(PROJECTS).create(testData.getProject());

        Project duplicateProject = Project.builder()
                .id(testData.getProject().getId())
                .name("Duplicate Project Name")
                .build();

        new UncheckedBase(Specifications.superUserSpec(), PROJECTS)
                .create(duplicateProject)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("Project ID \"" + testData.getProject().getId() + "\" is already used by another project"));
    }

    @Test(description = "User should not be able to create project with empty name", groups = {"Negative", "CRUD"})
    public void userCreatesProjectWithEmptyNameTest() {
        Project project = testData.getProject();
        project.setName("");

        new UncheckedBase(Specifications.superUserSpec(), PROJECTS)
                .create(project)
                .then().assertThat().statusCode(HttpStatus.SC_BAD_REQUEST)
                .body(containsString("Project name cannot be empty"));
    }

}