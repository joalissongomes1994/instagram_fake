package joalissongomes.dev.instagram.post.view

import android.net.Uri
import android.os.Build
import android.util.Size
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import joalissongomes.dev.instagram.R

class PictureAdapter(private val onClick: (Uri) -> Unit) :
    RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {

    var items: List<Uri> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_profile_grid, parent, false)

        return PictureViewHolder(view)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    inner class PictureViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(image: Uri) {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val bitmap =
                    itemView.context.contentResolver.loadThumbnail(image, Size(200, 200), null)
                itemView.findViewById<ImageView>(R.id.item_profile_img_grid).setImageBitmap(bitmap)
            } else {
                itemView.findViewById<ImageView>(R.id.item_profile_img_grid).setImageURI(image)
            }

            itemView.setOnClickListener {
                onClick.invoke(image)
            }
        }
    }
}