package com.example.twisterfragments.webApiServices;

import com.example.twisterfragments.model.Comments;
import com.example.twisterfragments.model.Messages;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiServices {
    @GET("messages")
    Call<List<Messages>> getAllMessages();

    @GET("messages/{messageId}/comments")
    Call<List<Comments>>getCommentById(@Path("messageId") int messageId);

    @GET("messages")
    Call<List<Messages>>getMessagesByUser(@Query("user") String user);

    @POST("messages/{messageId}/comments")
    Call<Comments>saveCommentBody(@Path("messageId")int messageId, @Body Comments comment);

    @POST("messages")
    Call<Messages>saveMessage(@Body Messages message);


}
