package com.example.android.instagramclone.Fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.android.instagramclone.Models.Reel
import com.example.android.instagramclone.R
import com.example.android.instagramclone.Utils.REEL
import com.example.android.instagramclone.adapter.ReelAdapter
import com.example.android.instagramclone.databinding.FragmentReelsBinding
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase


class ReelsFragment : Fragment() {
    private lateinit var binding:FragmentReelsBinding
    lateinit var adapter:ReelAdapter
    var reelList=ArrayList<Reel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding= FragmentReelsBinding.inflate(inflater, container, false)
        adapter= ReelAdapter(requireContext(),reelList)
        binding.viewPager.adapter = adapter
        Firebase.firestore.collection(REEL).get().addOnSuccessListener {
            var tempList = ArrayList<Reel>()
            reelList.clear()
            for(i in it.documents){
                var reel = i.toObject<Reel>()!!
                tempList.add(reel)
            }
            reelList.addAll(tempList)
            reelList.reverse()
            adapter.notifyDataSetChanged()
        }
        return binding.root
    }

    companion object {


    }
}