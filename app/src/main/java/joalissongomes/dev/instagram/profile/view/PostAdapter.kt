package joalissongomes.dev.instagram.profile.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import joalissongomes.dev.instagram.R
import joalissongomes.dev.instagram.common.model.Post

class PostAdapter() :
    RecyclerView.Adapter<PostAdapter.PostViewHolder>() {

    var items: List<Post> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_profile_grid, parent, false)

        return PostViewHolder(view)
    }

    override fun onBindViewHolder(holder: PostViewHolder, position: Int) {
        holder.bind(items[position].photoUrl)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class PostViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(photoUrl: String?) {

            Glide.with(itemView.context).load(photoUrl)
                .into(itemView.findViewById(R.id.item_profile_img_grid))
        }
    }
}