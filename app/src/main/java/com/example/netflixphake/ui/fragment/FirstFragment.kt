package com.example.netflixphake.ui.fragment

import android.app.Activity.RESULT_OK
import android.content.ContentValues.TAG
import android.content.IntentSender
import android.util.Log
import android.view.Gravity
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.netflixphake.R
import com.example.netflixphake.databinding.FragmentFirstBinding
import com.example.netflixphake.ui.base.BaseFragment
import com.example.netflixphake.ui.model.NetflixViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.identity.Identity
import com.google.android.gms.auth.api.identity.SignInClient
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.ActionCodeSettings
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class FirstFragment : BaseFragment<NetflixViewModel, FragmentFirstBinding>() {

    override fun initActions() {
    }

    override fun initData() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()

//        googleSignInClient = activity?.let { GoogleSignIn.getClient(it.applicationContext , gso) }!!

        val currentUser = auth.currentUser
        if (currentUser != null) {
            navigate(R.id.action_FirstFragment_to_NetflixFragment, null)
        }
    }

    override fun initView() {
    }

    override fun provideLayoutId() = R.layout.fragment_first

    override fun provideViewModelClass() = NetflixViewModel::class.java

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearDisposable()
    }
}