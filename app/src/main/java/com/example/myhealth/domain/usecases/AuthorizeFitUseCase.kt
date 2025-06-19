@file:Suppress("DEPRECATION")

package com.example.myhealth.domain.usecases

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataType
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizeFitUseCase @Inject constructor(
    @ApplicationContext private val ctx: Context
) {
    operator fun invoke(context: Context = ctx): Result<Unit> {
        val account : GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(context)
        return if (account != null) Result.success(Unit)
        else Result.failure(Exception("Google Fit not authorised"))
    }

    fun getSignInIntent(context: Context = ctx): android.content.Intent {
        val fitnessOptions = FitnessOptions.builder()
            .addDataType(DataType.TYPE_STEP_COUNT_DELTA, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_CALORIES_EXPENDED, FitnessOptions.ACCESS_READ)
            .addDataType(DataType.TYPE_HYDRATION, FitnessOptions.ACCESS_WRITE)
            .build()

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .addExtension(fitnessOptions)
            .build()
        val client: GoogleSignInClient = GoogleSignIn.getClient(context, gso)
        return client.signInIntent
    }
}