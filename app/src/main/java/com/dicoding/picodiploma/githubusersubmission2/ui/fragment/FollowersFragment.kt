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
import com.dicoding.picodiploma.githubusersubmission2.adapter.FollowersAdapter
import com.dicoding.picodiploma.githubusersubmission2.databinding.FragmentFollowersBinding
import com.dicoding.picodiploma.githubusersubmission2.viewmodel.FollowersViewModel


class FollowersFragment : Fragment() {

    companion object {
        const val EXTRA_USERNAME = "extra_username"

        fun newInstance(username: String): FollowersFragment {
            val fragment = FollowersFragment()
            val bundle = Bundle()
            bundle.putString(EXTRA_USERNAME, username)
            fragment.arguments = bundle
            return fragment
        }
    }

    private lateinit var adapter: FollowersAdapter
    private lateinit var viewModel: FollowersViewModel
    private lateinit var binding: FragmentFollowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFollowersBinding.inflate(inflater, container, false)
        adapter = FollowersAdapter()

        binding.rvFollowers.layoutManager = LinearLayoutManager(activity)
        binding.rvFollowers.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val username = arguments?.getString(EXTRA_USERNAME)

        adapter.notifyDataSetChanged()

        binding.rvFollowers.setHasFixedSize(true)
        showLoading(true)

        viewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowersViewModel::class.java)
        viewModel.getFollowers(username.toString())
        Log.d("username: ", username.toString())


        viewModel.getListFollowers().observe(activity!!, Observer {userItems ->
            if (userItems != null) {
                adapter.setData(userItems)
                showLoading(false)
            }
        })
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
        }
    }
}