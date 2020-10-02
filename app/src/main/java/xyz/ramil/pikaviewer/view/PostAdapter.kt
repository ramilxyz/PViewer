package xyz.ramil.pikaviewer.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import xyz.ramil.pikaviewer.R
import xyz.ramil.pikaviewer.model.PostModel

class PostAdapter(private var data: List<PostModel>, private val context: Context, view: View?) :
    RecyclerView.Adapter<PostAdapter.ViewHolder>() {
    fun update(data: List<PostModel>, view: View?) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowItem = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
        return ViewHolder(rowItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = data[position].title
        holder.body.text = data[position].body
        if (data[position].images != null && !data[position].images?.isEmpty()!!) {
            holder.image.visibility = View.VISIBLE
            val url = data[position].images?.get(0)!!
            Glide.with(context)
                .load(url)
                .apply(
                    RequestOptions.placeholderOf(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                )
                .into(holder.image)
        } else {
            holder.image.visibility = View.GONE
        }

        holder.menu.setOnClickListener {

            val pop= PopupMenu(context,it)
            pop.inflate(R.menu.popup_post)

            pop.setOnMenuItemClickListener {item->

                when(item.itemId)

                {
//                    R.id.delete->{ }
//
//                    R.id.cancel->{ }


                }
                true
            }
            pop.show()
            true
        }



    }

    override fun getItemCount(): Int {
        return data.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val title: TextView
        val body: TextView
        val image: ImageView
        val menu: ImageView
        override fun onClick(view: View) {}

        init {
            view.setOnClickListener(this)
            title = view.findViewById(R.id.tvPostTitle)
            body = view.findViewById(R.id.tvPostBody)
            image = view.findViewById(R.id.ivPicture)
            menu = view.findViewById(R.id.ivMenu)
        }
    }
}