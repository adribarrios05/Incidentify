package com.alaturing.incidentify.remote

import com.alaturing.incidentify.login.data.User

interface RemoteDatasource {

    // Métodos autenticación
    suspend fun login(identifier:String,password:String):Result<User>
    suspend fun register(userName:String,email:String,password:String):Result<User>
}