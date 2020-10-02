package xyz.ramil.pikaviewer.view

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.get
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import xyz.ramil.pikaviewer.R
import xyz.ramil.pikaviewer.database.Repo
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

        menuClick(holder, position)
        smallImageRvInit(holder, position)
    }

    fun smallImageRvInit(holder: ViewHolder, position: Int) {
        var smallImageAdapter: SmallImageAdapter? = null
        var recyclerView: RecyclerView? = null

        recyclerView = holder.rv
        recyclerView.setHasFixedSize(true)

        val horizontalLayoutManagaer =
            LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        recyclerView.layoutManager = horizontalLayoutManagaer
        smallImageAdapter = context.let { SmallImageAdapter(mutableListOf(), it) }
        recyclerView.adapter = smallImageAdapter
        smallImageAdapter.update(data[position].images)

        holder.title.text = data[position].title

        if (data[position].body != null) {
            if (data[position].body?.isEmpty()!!)
                holder.body.visibility = View.GONE else
                holder.body.visibility = View.VISIBLE
            holder.body.text = data[position].body
        } else holder.body.visibility = View.GONE
    }

    fun menuClick(holder: ViewHolder, position: Int) {
        holder.menu.setOnClickListener {
            val pop = PopupMenu(context, it)
            pop.inflate(R.menu.popup_post)
            if (data[position].save!!) {
                pop.menu.get(0).title = context.getString(R.string.remove_from_saved)
            } else {
                pop.menu.get(0).title = context.getString(R.string.save)
            }
            pop.setOnMenuItemClickListener { item ->

                when (item.itemId) {
                    R.id.itemSave -> {
                        if (!data[position].save!!) {
                            var item = data[position]
                            item.save = true
                            Repo.insertData(context, item)
                            pop.menu.get(0).title = context.getString(R.string.save)

                        } else {
                            var item = data[position]
                            item.save = false
                            Repo.insertData(context, item)
                            pop.menu.get(0).title = context.getString(R.string.remove_from_saved)
                        }
                    }
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
        val rv: RecyclerView
        override fun onClick(view: View) {}

        init {
            view.setOnClickListener(this)
            title = view.findViewById(R.id.tvPostTitle)
            body = view.findViewById(R.id.tvPostBody)
            image = view.findViewById(R.id.ivPicture)
            menu = view.findViewById(R.id.ivMenu)
            rv = view.findViewById(R.id.rvSmall)
        }
    }
}