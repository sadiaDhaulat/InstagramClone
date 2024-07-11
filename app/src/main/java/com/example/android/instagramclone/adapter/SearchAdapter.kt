package com.example.android.instagramclone.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.android.instagramclone.Models.User
import com.example.android.instagramclone.R
import com.example.android.instagramclone.Utils.FOLLOW
import com.example.android.instagramclone.databinding.SearchRvBinding
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SearchAdapter (var context: Context, var userList:ArrayList<User>): RecyclerView.Adapter<SearchAdapter.ViewHolder>(){

    inner class ViewHolder(var binding:SearchRvBinding): RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
       var binding= SearchRvBinding.inflate(LayoutInflater.from(context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var isfollow = false
        Glide.with(context).load(userList.get(position).image).placeholder(R.drawable.user).into(holder.binding.profileImage)
        holder.binding.name.text= userList.get(position).name

        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW)
            .whereEqualTo("email",userList.get(position).email).get().addOnSuccessListener {
                if(it.documents.size==0){
                    isfollow=false
                }else{
                    holder.binding.followBtn.text="Unfollow"
                    isfollow= true
            }
        holder.binding.followBtn.setOnClickListener {
            if(isfollow){
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW)
                    .whereEqualTo("email",userList.get(position).email).get().addOnSuccessListener {
                        Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).document(it.documents.get(0).id).delete()
                        holder.binding.followBtn.text= "Follow"
                        isfollow=false
                        }

            }else{
                Firebase.firestore.collection(Firebase.auth.currentUser!!.uid+ FOLLOW).document().set(userList.get(position))
                holder.binding.followBtn.text= "Unfollow"
                isfollow=true
            }
        }

        }
    }
}