package com.example.ecoveggie.Api

import com.example.ecoveggie.model.DefaultResponse
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface Api {

    @FormUrlEncoded
    @POST("register")
    fun register(

        @Field("full_name") full_name:String,
        @Field("email") email:String,
        @Field("phone") phone:String,
        @Field("password") password:String,
        @Field("role") role:String,
        @Field("business_name") business_name:String,
        @Field("informal_name") informal_name:String,
        @Field("address") address:String,
        @Field("city") city:String,
        @Field("state") state:String,
        @Field("zip_code") zip_code:Number,
        @Field("registration_proof") registration_proof:String,
        @Field("business_hours") business_hours:String,
        @Field("device_token") device_token:String,
        @Field("type") type:String,
        @Field("social_id") social_id:String,

    ): Call<DefaultResponse>

}