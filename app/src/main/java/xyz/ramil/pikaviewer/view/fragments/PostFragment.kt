package xyz.ramil.pikaviewer.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.PopupMenu
import androidx.core.view.get
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.ramil.pikaviewer.R
import xyz.ramil.pikaviewer.database.Repo
import xyz.ramil.pikaviewer.model.PostModel
import xyz.ramil.pikaviewer.view.adapters.PostImageAdapter

class PostFragment(postModel: PostModel) : Fragment() {
    var recyclerView: RecyclerView? = null
    var postAdapterFeed: PostImageAdapter? = null
    var title: TextView? = null
    var body: TextView? = null
    var menu: ImageView? = null
    var postModel: PostModel? = null

    init {
        this.postModel = postModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    fun initView() {

        title = view?.findViewById(R.id.tvPostTitle)
        body = view?.findViewById(R.id.tvPostBody)
        menu = view?.findViewById(R.id.ivMenu)

        title?.text = postModel?.title
        body?.text = postModel?.body

        menuClick()

        recyclerView = view!!.findViewById(R.id.rvPost)
        recyclerView?.setHasFixedSize(true)
        val verticalLinearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = verticalLinearLayoutManager
        setupRecyclerView()
    }

    fun setupRecyclerView() {
        postAdapterFeed = context?.let { PostImageAdapter(mutableListOf(), it) }
        recyclerView?.adapter = postAdapterFeed

        val imageList: MutableList<String> = mutableListOf()

        imageList.addAll(postModel?.images as MutableList<String>)

            //TODO еслсли пост имеет много изображений
        imageList.add("https://i.stack.imgur.com/9TELO.png")
        imageList.add("https://i.stack.imgur.com/9TELO.png")
        imageList.add("https://i.stack.imgur.com/9TELO.png")
        imageList.add("https://i.stack.imgur.com/9TELO.png")
        imageList.add("https://i.stack.imgur.com/9TELO.png")

        postAdapterFeed?.update(imageList)
    }

    fun menuClick() {
        menu?.setOnClickListener {
            val pop = PopupMenu(context!!, it)
            pop.inflate(R.menu.popup_post)
            if (postModel?.save!!) {
                pop.menu.get(0).title = context?.getString(R.string.remove_from_saved)
            } else {
                pop.menu.get(0).title = context?.getString(R.string.save)
            }
            pop.setOnMenuItemClickListener { item ->

                when (item.itemId) {
                    R.id.itemSave -> {
                        if (!postModel!!.save!!) {
                            var item = postModel
                            item?.save = true
                            Repo.insertData(context!!, item!!)
                            pop.menu.get(0).title = context?.getString(R.string.save)

                        } else {
                            var item = postModel
                            item?.save = false
                            Repo.insertData(context!!, item!!)
                            pop.menu.get(0).title = context?.getString(R.string.remove_from_saved)
                        }
                    }
                }
                true
            }
            pop.show()
            true
        }
    }
}