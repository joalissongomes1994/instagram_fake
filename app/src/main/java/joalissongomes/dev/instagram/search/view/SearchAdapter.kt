package joalissongomes.dev.instagram.search.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import joalissongomes.dev.instagram.R
import joalissongomes.dev.instagram.common.model.User

class SearchAdapter(
    private val itemClick: (String) -> Unit
) : RecyclerView.Adapter<SearchAdapter.SearchViewHolder>() {

    var items: List<User> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_user_list, parent, false)

        return SearchViewHolder(view)
    }

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class SearchViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(user: User) {

            Glide.with(itemView.context).load(user.photoUrl)
                .into(itemView.findViewById(R.id.search_img_user))
            itemView.findViewById<TextView>(R.id.search_txt_username).text = user.name

            itemView.setOnClickListener {
                user.uuid?.let {
                    itemClick.invoke(it)
                }
            }
        }
    }
}