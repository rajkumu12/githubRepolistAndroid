package com.decode.githubrepolisttask.UI

import android.app.Activity
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.decode.githubrepolisttask.R
import com.decode.githubrepolisttask.data.Repos
import com.decode.githubrepolisttask.databinding.UserRowBinding
import com.squareup.picasso.Picasso
import de.hdodenhof.circleimageview.CircleImageView

class RepoRecyclerViewAdapter(val activity: Activity) :
    RecyclerView.Adapter<RepoRecyclerViewAdapter.MyViewHolder>() {

    private var repolist: List<Repos>? = null
    var row_index = 0


    fun setRepoList(reposlist: List<Repos>?) {
        this.repolist = reposlist
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RepoRecyclerViewAdapter.MyViewHolder {
        val binding = UserRowBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepoRecyclerViewAdapter.MyViewHolder, position: Int) {
        holder.bind(repolist?.get(position)!!, activity)
        holder.itemView.setOnClickListener {
            if (repolist?.get(position)?.isSelected == true) {
                repolist?.get(position)?.set(false)
                notifyDataSetChanged()
            } else {
                repolist?.get(position)?.set(true)
                notifyDataSetChanged()
            }
        }

        if (repolist?.get(position)?.isSelected == true) {
            holder.itemView.setBackgroundColor(Color.parseColor("#2dc8a8"))
        } else {
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"))
        }

    }

    override fun getItemCount(): Int {
        if (repolist == null) return 0
        else return repolist?.size!!
    }

    class MyViewHolder(view: UserRowBinding) : RecyclerView.ViewHolder(view.root) {

        val tvName = view.textRepoName
        val userimage = view.image
        fun bind(data: Repos, activity: Activity) {
            tvName.text = data.repoName
            Picasso.get()
                .load(data.owner.userImage).placeholder(R.drawable.ic_octicons_mark_github)
                .into(userimage);
        }
    }
}