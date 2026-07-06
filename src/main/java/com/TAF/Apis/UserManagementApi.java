package com.TAF.Apis;

import com.TAF.Utils.Logs.LogsManager;
import com.TAF.Validations.Verification;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.util.HashMap;
import java.util.Map;

public class UserManagementApi {
    //endPoints
    public static final String CreateAccountEndPoint = "/createAccount";
    public static final String DeleteAccountEndPoint = "/deleteAccount";
    RequestSpecification requestSpecification;
    Response response;
    Verification verification;

    public UserManagementApi() {
        requestSpecification = RestAssured.given();
        verification = new Verification();
    }

    //API Methods
    //method for creating Account using rest
    @Step("Create a New User Account By APIS With Full Details")
    public UserManagementApi CreateRegisterUserAccount(String name, String email, String password, String title, String birth_date, String birth_month, String birth_year, String firstname, String lastname, String company, String address1, String address2, String country, String zipcode, String state, String city, String mobile_number) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", password);
        formParams.put("title", title);
        formParams.put("birth_date", birth_date);
        formParams.put("birth_month", birth_month);
        formParams.put("birth_year", birth_year);
        formParams.put("firstname", firstname);
        formParams.put("lastname", lastname);
        formParams.put("company", company);
        formParams.put("address1", address1);
        formParams.put("address2", address2);
        formParams.put("country", country);
        formParams.put("zipcode", zipcode);
        formParams.put("state", state);
        formParams.put("city", city);
        formParams.put("mobile_number", mobile_number);
        response = requestSpecification.spec(Builder.getUserManagmentRequestSpecification(formParams))
                .post(CreateAccountEndPoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }


    //method for deleting Account using rest
    @Step("Delete a New User Account By APIS")
    public UserManagementApi DeleteRegisterUserAccount(String email, String password) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("email", email);
        formParams.put("password", password);
        response = requestSpecification.spec(Builder.getUserManagmentRequestSpecification(formParams))
                .delete(DeleteAccountEndPoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }

    @Step("Create a New User Account By APIS With Minimal Details")
    public UserManagementApi CreateRegisterUserAccountMinimalDetails(String name, String email, String password, String firstname, String lastname) {
        Map<String, String> formParams = new HashMap<>();
        formParams.put("name", name);
        formParams.put("email", email);
        formParams.put("password", password);
        formParams.put("title", "Mr");
        formParams.put("birth_date", "26");
        formParams.put("birth_month", "4");
        formParams.put("birth_year", "2001");
        formParams.put("firstname", firstname);
        formParams.put("lastname", lastname);
        formParams.put("company", "QA");
        formParams.put("address1", "Cairo");
        formParams.put("address2", "Cairo");
        formParams.put("country", "Canada");
        formParams.put("zipcode", "12345");
        formParams.put("state", "Cairo");
        formParams.put("city", "Cairo");
        formParams.put("mobile_number", "01279204279");

        response = requestSpecification.spec(Builder.getUserManagmentRequestSpecification(formParams))
                .post(CreateAccountEndPoint);
        LogsManager.info(response.asPrettyString());
        return this;
    }

    //Validations
    @Step("Verify user is created successfully")
    public UserManagementApi VerifyUserIsCreated() {
        verification.Equals(response.jsonPath().get("message"), "User created!", "user was not created, ");
        return this;
    }

    @Step("Verify user is Deleted successfully")
    public UserManagementApi VerifyUserIsDeleted() {
        verification.Equals(response.jsonPath().get("message"), "Account deleted!", "user was not deleted, ");
        return this;
    }
}