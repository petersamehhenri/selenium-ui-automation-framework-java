package com.TAF.Apis;

import com.TAF.Utils.DataReader.PropertyReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Map;

public class Builder {
    private static String BaseURI = PropertyReader.getProperty("BaseUrl_Api");
    private Builder() {
        //private constructor to prevent initialization
    }

    //Build Request Specification
    public static RequestSpecification getUserManagmentRequestSpecification(Map<String, ?> formParams) {
        return new RequestSpecBuilder().setBaseUri(BaseURI).setContentType(ContentType.URLENC).addFormParams(formParams).build();
    }
}