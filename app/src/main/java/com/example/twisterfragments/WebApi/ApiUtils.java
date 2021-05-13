package com.example.twisterfragments.WebApi;

public class ApiUtils {
    private static final String BASE_URL= "https://anbo-restmessages.azurewebsites.net/api/";

    public static ApiServices getMessagesService(){
        return RetrofitServices.getComments(BASE_URL).create(ApiServices.class);
    }
}
