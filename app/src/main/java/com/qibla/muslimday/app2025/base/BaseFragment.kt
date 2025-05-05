package com.qibla.muslimday.app2025.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import com.qibla.muslimday.app2025.util.SystemUtil

abstract class BaseFragment<T : ViewBinding> : Fragment() {
    protected lateinit var binding: T

//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected var isLoadNative = false

    abstract fun getViewBinding(): T

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    abstract fun initView()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        SystemUtil.setLanguage(requireActivity())
        binding = getViewBinding()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()

    }

    override fun onStop() {
        super.onStop()
        isLoadNative = true
    }
}