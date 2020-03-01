package com.learntodroid.postrequestwithjson;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface CommentsService {
    @POST("comments")
    Call<ResponseBody> createComment(@Body Comment comment);

    @FormUrlEncoded
    @POST("comments")
    Call<ResponseBody> createComment(@Field("title") String title, @Field("comment") String comment, @Field("author") String author);

    @FormUrlEncoded
    @POST("comments")
    Call<ResponseBody> createComment(@FieldMap Map<String, String> fields);

    @Multipart
    @POST("comments")
    Call<ResponseBody> createComment(@Part("photo") RequestBody photo, @Part("description") RequestBody description);
}
