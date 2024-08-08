package com.netlifymanjot.instagram_clone.fragment

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.netlifymanjot.instagram_clone.SignUp
import com.netlifymanjot.instagram_clone.User
import com.netlifymanjot.instagram_clone.adapters.ViewPagerAdapter
import com.netlifymanjot.instagram_clone.databinding.FragmentProfileBinding
import com.netlifymanjot.instagram_clone.utils.USER_NODE
import com.squareup.picasso.Picasso
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)

        binding.editProfile.setOnClickListener{
            val intent = Intent(activity, SignUp::class.java)
            intent.putExtra("MODE", 1)
            activity?.startActivity(intent)
            activity?.finish()
        }

        // Initialize ViewPagerAdapter and set it to ViewPager2
        viewPagerAdapter = ViewPagerAdapter(requireActivity())
        viewPagerAdapter.addFragment(MyPostFragment(), "My Post")
        viewPagerAdapter.addFragment(MyPostFragment(), "My Reels")
        binding.profileViewPager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.profileViewPager) { tab, position ->
            tab.text = viewPagerAdapter.getPageTitle(position)
        }.attach()

        return binding.root
    }

    override fun onStart() {
        super.onStart()
        Firebase.firestore.collection(USER_NODE)
            .document(Firebase.auth.currentUser!!.uid)
            .get()
            .addOnSuccessListener { document ->
                val user: User? = document.toObject(User::class.java)
                if (user != null) {
                    binding.name.text = user.name
                    binding.bio.text = user.email
                    if (!user.image.isNullOrEmpty()) {
                        Picasso.get().load(user.image).into(binding.upload)
                    }
                }
            }
    }
}
