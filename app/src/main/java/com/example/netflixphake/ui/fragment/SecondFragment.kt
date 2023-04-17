package com.example.netflixphake.ui.fragment

import android.content.ContentValues.TAG
import android.util.Log
import android.widget.Toast
import com.example.netflixphake.R
import com.example.netflixphake.databinding.FragmentSecondBinding
import com.example.netflixphake.ui.base.BaseFragment
import com.example.netflixphake.ui.model.NetflixViewModel
import com.google.firebase.auth.FirebaseUser

class SecondFragment : BaseFragment<NetflixViewModel, FragmentSecondBinding>() {
    override fun initActions() {
        binding.register.setOnClickListener{
            val username = binding.inUsername.text
            val password = binding.inPassword.text
            createAccount(username.toString(),password.toString())
        }
    }

    override fun initData() {
    }

    override fun initView() {
    }

    override fun provideLayoutId() = R.layout.fragment_second

    override fun provideViewModelClass() = NetflixViewModel::class.java

    override fun onDestroyView() {
        super.onDestroyView()
        viewModel.clearDisposable()
    }

    private fun createAccount(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    Log.d(TAG, "createUserWithEmail:success")
                    val user = auth.currentUser
                    updateUI(user)
//                    navigate(R.id.action_SecondFragment_to_HomeFragment,null)
                } else {
                    Log.w(TAG, "createUserWithEmail:failure", task.exception)
                    updateUI(null)
                }
            }
    }

    private fun updateUI(user: FirebaseUser?) {
    }
}