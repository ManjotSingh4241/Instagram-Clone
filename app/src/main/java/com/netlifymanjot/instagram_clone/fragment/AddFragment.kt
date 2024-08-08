package com.netlifymanjot.instagram_clone.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.netlifymanjot.instagram_clone.R
import com.netlifymanjot.instagram_clone.databinding.FragmentAddBinding

class AddFragment : BottomSheetDialogFragment() {

    private lateinit var binding:FragmentAddBinding
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding=FragmentAddBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {

    }
}