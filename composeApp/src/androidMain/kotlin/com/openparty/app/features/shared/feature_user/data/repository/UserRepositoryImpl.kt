//File: composeApp/src/commonMain/kotlin/com/openparty/app/features/shared/feature_user/data/repository/UserRepositoryImpl.kt
package com.openparty.app.features.shared.feature_user.data.repository

import com.openparty.app.core.shared.domain.DomainResult
import com.openparty.app.core.shared.domain.error.AppError
import com.openparty.app.features.shared.feature_user.data.datasource.UserDataSource
import com.openparty.app.features.shared.feature_user.data.mapper.UserMapper
import com.openparty.app.features.shared.feature_user.data.model.UserDto
import com.openparty.app.features.shared.feature_user.domain.model.UpdateUserRequest
import com.openparty.app.features.shared.feature_user.domain.model.User
import com.openparty.app.features.shared.feature_user.domain.repository.UserRepository
import com.openparty.app.core.shared.domain.GlobalLogger.logger

class UserRepositoryImpl(
    private val userDataSource: UserDataSource
) : UserRepository {

    override suspend fun getUser(userId: String): DomainResult<User> {
        logger.i { "getUser called with userId: $userId" }
        return try {
            val dto = userDataSource.fetchUser(userId)
            val user = UserMapper.map(dto)
            DomainResult.Success(user)
        } catch (e: Exception) {
            logger.e(e) { "Error fetching user with userId: $userId" }
            DomainResult.Failure(AppError.User.General)
        }
    }

    override suspend fun isScreenNameTaken(name: String): DomainResult<Boolean> {
        logger.i { "isScreenNameTaken called for name: $name" }
        return try {
            val taken = userDataSource.isScreenNameTaken(name)
            DomainResult.Success(taken)
        } catch (e: Exception) {
            logger.e(e) { "Error checking if screen name is taken: $name" }
            DomainResult.Failure(AppError.User.General)
        }
    }

    override suspend fun updateUser(userId: String, request: Any): DomainResult<Unit> {
        logger.i { "updateUser called with userId: $userId" }
        return try {
            if (request is UpdateUserRequest) {
                userDataSource.updateUser(userId, request)
                logger.i { "Successfully updated user with userId: $userId" }
            } else {
                logger.i { "Invalid request type for updateUser: $request" }
            }
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            logger.e(e) { "Error updating user with userId: $userId" }
            DomainResult.Failure(AppError.User.General)
        }
    }

    override suspend fun addUser(userId: String, user: UserDto): DomainResult<Unit> {
        logger.i { "addUser called with userId: $userId" }
        return try {
            userDataSource.addUser(userId, user)
            logger.i { "Successfully added user with userId: $userId" }
            DomainResult.Success(Unit)
        } catch (e: Exception) {
            logger.e(e) { "Error adding user with userId: $userId" }
            DomainResult.Failure(AppError.User.General)
        }
    }
}
