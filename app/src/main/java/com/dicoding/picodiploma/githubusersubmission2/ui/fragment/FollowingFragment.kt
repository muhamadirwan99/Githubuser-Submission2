package com.dicoding.picodiploma.githubusersubmission2.ui.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.githubusersubmission2.adapter.FollowingAdapter
import com.dicoding.picodiploma.githubusersubmission2.databinding.FragmentFollowingBinding
import com.dicoding.picodiploma.githubusersubmission2.viewmodel.FollowingViewModel


class FollowingFragment : Fragment() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        fun newInstance(username: String): FollowingFragment {
            val fragment = FollowingFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var adapter: FollowingAdapter
    private lateinit var viewModel: FollowingViewModel
    private lateinit var binding: FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowingBinding.inflate(inflater, container, false)
        adapter = FollowingAdapter()

        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowing.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(EXTRA_USERNAME)

        adapter.notifyDataSetChanged()

        binding.rvFollowing.setHasFixedSize(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)

        viewModel.getFollowing(username.toString())
        Log.d("username: ", username.toString())

        activity?.let {
            viewModel.getListFollowing().observe(it, Observer { userItems ->
                if (userItems != null) {
                    adapter.setData(userItems)
                }
            })
        }
    }

}