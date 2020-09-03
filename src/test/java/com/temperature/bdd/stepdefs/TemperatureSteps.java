package com.temperature.bdd.stepdefs;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;

import com.temperature.api.add.TemperatureRequest;
import cucumber.api.java8.En;
import io.cucumber.datatable.DataTable;
import io.restassured.response.Response;
import java.util.List;


public class TemperatureSteps extends AbstractSteps implements En {

    public TemperatureSteps() {

        Given("user wants to create an temperature with the following attributes", (DataTable temperatureDt) -> {
            testContext().reset();
            List<TemperatureRequest> temperatureList = temperatureDt.asList(TemperatureRequest.class);

            // First row of DataTable has the temperature attributes hence calling get(0) method.
            super.testContext()
                    .setPayload(temperatureList.get(0));
        });

        When("user saves the new temperature {string}", (String testContext) -> {
            String createTemperatureUrl = "/temperatures/add";

            // AbstractSteps class makes the POST call and stores response in TestContext
            executePost(createTemperatureUrl);
        });

        /**
         * This can be moved to a Class named 'CommonSteps.java so that it can be reused.
         */
        Then("the save {string}", (String expectedResult) -> {
            Response response = testContext().getResponse();
            System.out.println("expectedResult==> "+ expectedResult);
            switch (expectedResult) {
                case "IS SUCCESSFUL":
                    assertThat(response.statusCode()).isIn(200, 201);
                    break;
                case "FAILS":
                    assertThat(response.statusCode()).isBetween(400, 504);
                    break;
                default:
                    fail("Unexpected error");
            }
        });


        When("user call the temperatures by filter date: {string} and conversion: {string}", (String date, String conversion) -> {
            String getBydateTemperatureUrl = "/temperatures/list-by-date?date="+date+"&conversion="+conversion;
            // AbstractSteps class makes the POST call and stores response in TestContext
            executeGet(getBydateTemperatureUrl);
        });

        When("user call the temperatures by filter date: {string} and conversion: {string} group hour", (String date, String conversion) -> {
            String getBydateTemperatureUrl = "/temperatures/list-by-hour?date="+date+"&conversion="+conversion;
            // AbstractSteps class makes the POST call and stores response in TestContext
            executeGet(getBydateTemperatureUrl);
        });

        /**
         * This can be moved to a Class named 'CommonSteps.java so that it can be reused.
         */
        Then("the get {string}", (String expectedResult) -> {
            Response response = testContext().getResponse();
            System.out.println("expectedResult==> "+ expectedResult);
            switch (expectedResult) {
                case "IS SUCCESSFUL":
                    assertThat(response.statusCode()).isIn(200, 201);
                    break;
                case "FAILS":
                    assertThat(response.statusCode()).isBetween(400, 504);
                    break;
                default:
                    fail("Unexpected error");
            }
        });

    }
}
