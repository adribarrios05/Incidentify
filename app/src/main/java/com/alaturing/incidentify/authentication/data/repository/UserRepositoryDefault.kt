package com.alaturing.incidentify.authentication.data.repository

import com.alaturing.incidentify.authentication.model.User
import com.alaturing.incidentify.authentication.data.local.UserLocalDatasource
import com.alaturing.incidentify.authentication.data.remote.UserRemoteDatasource
import javax.inject.Inject

/**
 * Implemetación fake del [UserRepository], sin embargo puede
 * ser muy parecida a una implementación real ya que la gestión
 * de token, registro o login se hace en los datasources
 */
class UserRepositoryDefault @Inject constructor(
    private val remote: UserRemoteDatasource,
    private val local: UserLocalDatasource
): UserRepository {
    /**
     * @param identifier
     * @param password
     * @return [Result]
     */
    override suspend fun login(identifier: String, password: String):Result<User> {
        // Probamos en el remoto
        val result = remote.login(identifier, password)
        // Si es un login correcto, lo almacenamos en local
        if (result.isSuccess) {
            local.saveUser(result.getOrNull()!!)
        }
        return result
    }

    /**
     * @param user
     * @param email
     * @param password
     * @return [Result]
     */
    override suspend fun register(user: String, email: String, password: String):Result<User> {
        return remote.register(user,email,password)
    }

    override suspend fun logout() = local.clearUser()

}