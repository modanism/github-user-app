package com.dicoding.restaurantreview

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.restaurantreview.databinding.FragmentFollowBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_POSITION = "ARG_POSITION"
private const val ARG_USERNAME = "ARG_USERNAME"

/**
 * A simple [Fragment] subclass.
 * Use the [FollowiFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class FollowFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var position: Int? = 0
    private var username: String? = null
    private lateinit var binding : FragmentFollowBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentFollowBinding.inflate(inflater, container, false)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }

        val layoutManager = LinearLayoutManager(this.context)
        binding.rvDetailReview.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this.context, layoutManager.orientation)
        binding.rvDetailReview.addItemDecoration(itemDecoration)


        val detailViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(DetailViewModel::class.java)
        detailViewModel.listFollow.observe(viewLifecycleOwner) {user ->
            setUserFollower(user)
        }

        detailViewModel.isLoading.observe(viewLifecycleOwner) {
            showLoading(it)
        }

        if (position == 1){
            detailViewModel.findFollow("follower",username!!)
        } else {
            detailViewModel.findFollow("following", username!!)
        }
        // Inflate the layout for this fragment
        return binding.root
    }

    private fun setUserFollower(list: List<UserDetailResponse>) {
        val adapter = FollowAdapter(list)
        binding.rvDetailReview.adapter = adapter
    }

    private fun showLoading(isLoading : Boolean) {
        binding.followProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        binding.tvFragment.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param ARG_POSITION Parameter 1.
         * @param ARG_USERNAME Parameter 2.
         * @return A new instance of fragment FollowiFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: Int, param2: String) =
            FollowFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_POSITION, param1)
                    putString(ARG_USERNAME, param2)
                }
            }
    }
}