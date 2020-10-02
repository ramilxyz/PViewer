package xyz.ramil.pikaviewer.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import xyz.ramil.pikaviewer.R


class SmallImageAdapter(private var data: List<String?>?, private val context: Context) :
    RecyclerView.Adapter<SmallImageAdapter.ViewHolder>() {

    fun update(data: List<String?>?) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val rowItem =
            LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ViewHolder(rowItem)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context)
            .load(data?.get(position))
            .apply(
                RequestOptions.placeholderOf(R.drawable.ic_launcher_background)
                    .error(R.drawable.ic_launcher_background)
            )
            .into(holder.image)
    }

    override fun getItemCount(): Int {
        return data?.size!!
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val image: ImageView
        override fun onClick(view: View) {}

        init {
            view.setOnClickListener(this)
            image = view.findViewById(R.id.ivSmallPicture)
        }
    }
}