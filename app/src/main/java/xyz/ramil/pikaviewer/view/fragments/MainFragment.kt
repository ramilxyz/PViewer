package xyz.ramil.pikaviewer.view.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.textview.MaterialTextView
import xyz.ramil.pikaviewer.R
import xyz.ramil.pikaviewer.data.Status
import xyz.ramil.pikaviewer.database.DataBaseManager
import xyz.ramil.pikaviewer.model.PostModel
import xyz.ramil.pikaviewer.view.adapters.PostAdapter
import xyz.ramil.pikaviewer.view.customview.WrapContentGridLayoutManager
import xyz.ramil.pikaviewer.viewmodel.MainViewModel

class MainFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var mainViewModel: MainViewModel? = null
    var swiper: SwipeRefreshLayout? = null
    var tvNotSavedPosts: MaterialTextView? = null
    var recyclerView: RecyclerView? = null
    var contentLayout: FrameLayout? = null
    var postAdapter: PostAdapter? = null
    var isSave: Boolean = false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainViewModel = ViewModelProvider(activity!!).get(MainViewModel::class.java)
        observeGetPosts()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        mainViewModel?.getFeed()
    }

    fun initView() {
        swiper = view!!.findViewById(R.id.swiper)
        swiper?.setOnRefreshListener(this)
        recyclerView = view!!.findViewById(R.id.rv)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = WrapContentGridLayoutManager(activity, 1)
        contentLayout = view!!.findViewById<FrameLayout>(R.id.rootView)
        tvNotSavedPosts = view!!.findViewById(R.id.tvNotSavedPosts)

        setupRecyclerView()
    }

    fun setupRecyclerView() {
        (recyclerView?.layoutManager as WrapContentGridLayoutManager).spanCount = 1
        postAdapter = context?.let { PostAdapter(mutableListOf(), it, view) }
        recyclerView?.adapter = postAdapter

        postAdapter?.setOnItemClickListener(object : PostAdapter.OnItemClickListener {
            override fun OnItemClick(postModel: PostModel) {
                val fragment = PostFragment(postModel)
                activity?.supportFragmentManager?.beginTransaction()
                    ?.setCustomAnimations(R.anim.scale_in, R.anim.scale_out)
                    ?.add(R.id.rootView, fragment, "PostFragment" + postModel)
                    ?.addToBackStack(null)?.commit()
            }
        })

        observerRvData()
    }

    fun observerRvData() {
        DataBaseManager.getData(context!!)?.observe(viewLifecycleOwner, Observer { data ->
            if (isSave) {
                val filterData = data.filter { it.save == true }

                if(filterData.isEmpty())
                    tvNotSavedPosts?.visibility = View.VISIBLE
                 else
                    tvNotSavedPosts?.visibility = View.GONE

                postAdapter?.update(filterData, view)
            } else {
                postAdapter?.update(data, view)
            }
        })
    }

    private fun observeGetPosts() {
        mainViewModel?.feedLiveData?.observe(activity!!, Observer {
            when (it.status) {
                Status.LOADING -> loading()
                Status.SUCCESS -> success(it.data)
                Status.ERROR -> connectionError(it.error)
            }
        })
    }

    private fun loading() {
        swiper?.isRefreshing = true
    }

    private fun success(data: Any?) {
        val usersList: MutableList<PostModel>? = data as MutableList<PostModel>?
        usersList?.shuffle()
        usersList?.forEach {
            if (it.save == null) {
                it.save = false
            }

            val post = DataBaseManager.getPost(context!!, it.id!!)

            if (post == null)
                DataBaseManager.insertData(context!!, it)
            else if (!post.save!!) {
                it.save = false
                DataBaseManager.insertData(context!!, it)
            }
        }
        swiper?.isRefreshing = false
    }

    private fun connectionError(error: Error?) {
        swiper?.isRefreshing = false
        if (error?.message != null)
            Toast.makeText(context, "${error.message}", Toast.LENGTH_SHORT).show() else
            Toast.makeText(context, getString(R.string.error), Toast.LENGTH_SHORT).show()
    }

    override fun onRefresh() {
        mainViewModel?.getFeed()
    }

    fun setIsSaveScreen(boolean: Boolean) {
        isSave = boolean
    }
}



