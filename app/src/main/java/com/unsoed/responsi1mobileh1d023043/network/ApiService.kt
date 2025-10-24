package com.unsoed.responsi1mobileh1d023043.network

import com.unsoed.responsi1mobileh1d023043.model.ApiResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiService {
    
    // Endpoint untuk mendapatkan squad team berdasarkan ID
    @GET("teams/{id}")
    suspend fun getTeamSquad(
        @Path("id") teamId: Int,
        @Header("X-Auth-Token") token: String
    ): Response<ApiResponse>
    
    // Bisa ditambahkan endpoint lain jika diperlukan
    // @GET("teams/{id}/matches")
    // suspend fun getTeamMatches(@Path("id") teamId: Int, @Header("X-Auth-Token") token: String): Response<MatchResponse>
}