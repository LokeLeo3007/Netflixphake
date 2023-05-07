package com.example.netflixphake.ui.base

import android.app.Activity.RESULT_OK
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.NavHostFragment
import com.example.netflixphake.R
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.firebase.auth.FirebaseAuth


abstract class BaseFragment<T : BaseViewModel, Y : ViewDataBinding> : Fragment() {
    protected lateinit var auth: FirebaseAuth

    private var hostActivity: AppCompatActivity? = null

    protected lateinit var viewModel: T

    protected lateinit var binding: Y

    private var navController: NavController? = null

    lateinit var baseContext: Context
    private var view: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        viewModel = ViewModelProvider(this)[provideViewModelClass()]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, provideLayoutId(), container, false)
        try {
            navController = NavHostFragment.findNavController(this)
        } catch (_: Exception) {
        }
        val view = inflater.inflate(provideLayoutId(), container, false)
        initView()
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
        initActions()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        baseContext = context
        hostActivity = context as AppCompatActivity
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    protected open fun navigate(@IdRes action: Int, bundle: Bundle?) {
        if (navController != null) {
            val currentDestination = navController?.currentDestination
            if (currentDestination?.getAction(action) != null) {
                navController?.navigate(action, bundle)
            }
        }
    }

    protected open fun popBackStack(@IdRes desId: Int) {
        if (navController != null) {
            val currentDestination = navController?.currentDestination
            if (currentDestination != null && desId != currentDestination.id) {
                navController?.popBackStack(desId, false)
            }
        }
    }

    protected open fun popBackParent() {
        if (navController != null) {
            val currentDestination =
                navController?.currentDestination as FragmentNavigator.Destination?
            if (currentDestination != null) {
                currentDestination.parent?.startDestinationId?.let {
                    navController?.popBackStack(
                        it,
                        true
                    )
                }
            }
        }
    }

    protected open fun popBackStack() {
        if (navController != null) {
            val thisDestinationName = this.javaClass.name
            val currentDestination =
                navController?.currentDestination as FragmentNavigator.Destination?
            if (currentDestination != null) {
                val currentDestinationName = currentDestination.className
                if (thisDestinationName == currentDestinationName) {
                    navController?.popBackStack(currentDestination.id, true)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
    }

    @LayoutRes
    protected abstract fun provideLayoutId(): Int

    protected abstract fun provideViewModelClass(): Class<T>

    protected abstract fun initActions()

    protected abstract fun initData()

    protected abstract fun initView()
}