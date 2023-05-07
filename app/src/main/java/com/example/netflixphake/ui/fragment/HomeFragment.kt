package com.example.netflixphake.ui.fragment

import android.app.Activity
import android.app.Activity.RESULT_OK
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import com.example.netflixphake.R
import com.example.netflixphake.databinding.FragmentHomeBinding
import com.example.netflixphake.ui.base.BaseFragment
import com.example.netflixphake.ui.model.NetflixViewModel
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth

class HomeFragment : BaseFragment<NetflixViewModel, FragmentHomeBinding>() {

    private val signInLauncher = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val user = FirebaseAuth.getInstance().currentUser
        if( result.resultCode == RESULT_OK) {
            if (user != null) {
                navigate(R.id.action_HomeFragment_to_NetflixFragment, null)
            }
        }
    }

    override fun initActions() {
        binding.btnLogin.setOnClickListener{
            createSignInIntent()
//            navigate(R.id.action_HomeFragment_to_NetflixFragment,null)
        }

    }

    private fun createSignInIntent() {
        val providers = arrayListOf(
            AuthUI.IdpConfig.EmailBuilder().build(),
            AuthUI.IdpConfig.PhoneBuilder().build(),
            AuthUI.IdpConfig.GoogleBuilder().build())

        val signInIntent = AuthUI.getInstance()
            .createSignInIntentBuilder()
            .setAvailableProviders(providers)
            .setIsSmartLockEnabled(false)
            .build()

        signInLauncher.launch(signInIntent)
    }

    override fun initData() {
    }

    override fun initView() {
        auth = FirebaseAuth.getInstance()
        if(FirebaseAuth.getInstance().currentUser != null){
            navigate(R.id.action_HomeFragment_to_NetflixFragment,null)
        }
    }

    override fun provideLayoutId() = R.layout.fragment_home

    override fun provideViewModelClass() = NetflixViewModel::class.java

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearDisposable()
    }
}