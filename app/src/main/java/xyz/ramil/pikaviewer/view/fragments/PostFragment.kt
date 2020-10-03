package xyz.ramil.pikaviewer.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import xyz.ramil.pikaviewer.R
import xyz.ramil.pikaviewer.model.PostModel
import xyz.ramil.pikaviewer.view.adapters.FeedImageAdapter

class PostFragment(postModel: PostModel) : Fragment() {
    var recyclerView: RecyclerView? = null
    var postAdapterFeed: FeedImageAdapter? = null
    var title: TextView? = null
    var body: TextView? = null
    var image: ImageView? = null
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

        recyclerView = view!!.findViewById(R.id.rvPost)
        recyclerView?.setHasFixedSize(true)
        val verticalLinearLayoutManager =
            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        recyclerView?.layoutManager = verticalLinearLayoutManager
        setupRecyclerView()
    }

    fun setupRecyclerView() {
        postAdapterFeed = context?.let { FeedImageAdapter(mutableListOf(), it) }
        recyclerView?.adapter = postAdapterFeed
        postAdapterFeed?.update(postModel?.images)
    }



}