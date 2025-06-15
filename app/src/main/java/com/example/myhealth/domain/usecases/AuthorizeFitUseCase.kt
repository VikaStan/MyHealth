@file:Suppress("DEPRECATION")

package com.example.myhealth.domain.usecases

import android.content.Context
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.Result

@Singleton
class AuthorizeFitUseCase @Inject constructor(
    @ApplicationContext private val ctx: Context
) {
    operator fun invoke(context: Context = ctx): Result<Unit> {
        val account : GoogleSignInAccount? = GoogleSignIn.getLastSignedInAccount(context)
        return if (account != null) Result.success(Unit)
        else Result.failure(Exception("Google Fit not authorised"))
    }
}