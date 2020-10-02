package xyz.ramil.pikaviewer.view.fragments

import android.os.Bundle
import android.util.Log
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
import xyz.ramil.pikaviewer.R
import xyz.ramil.pikaviewer.data.Status
import xyz.ramil.pikaviewer.database.Repo
import xyz.ramil.pikaviewer.model.PostModel
import xyz.ramil.pikaviewer.view.PostAdapter
import xyz.ramil.pikaviewer.view.WrapContentGridLayoutManager
import xyz.ramil.pikaviewer.viewmodel.PViewerViewModel

class PostFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var pViewerViewModel: PViewerViewModel? = null
    var swiper: SwipeRefreshLayout? = null
    var recyclerView: RecyclerView? = null
    var contentLayout: FrameLayout? = null
    var postAdapter: PostAdapter? = null
    var isSave: Boolean = false


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
        pViewerViewModel = ViewModelProvider(this).get(PViewerViewModel::class.java)
        observeGetPosts()
        pViewerViewModel?.getFeed()
    }

    fun initView() {
        swiper = view!!.findViewById(R.id.swiper)
        swiper?.setOnRefreshListener(this)
        recyclerView = view!!.findViewById(R.id.rv)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.layoutManager = WrapContentGridLayoutManager(activity, 1)
        contentLayout = view!!.findViewById<FrameLayout>(R.id.rootView)

        setupRecyclerView()
    }

    fun setupRecyclerView() {
        (recyclerView?.layoutManager as WrapContentGridLayoutManager).spanCount = 1
        postAdapter = context?.let { PostAdapter(mutableListOf(), it, view) }
        recyclerView?.adapter = postAdapter
        observerRvData()

    }

    fun observerRvData() {
        Repo.getData(context!!)?.observe(viewLifecycleOwner, Observer { data ->
            if(isSave!!) {


                data.forEach {
                    Log.d("HHHHHHHHHH", ""+it.save)
                }

                var a = data.filter { it.save == true }
                postAdapter?.update(a, view)
            } else {
               postAdapter?.update(data, view)
            }

        })
    }

    private fun observeGetPosts() {
        pViewerViewModel?.feedLiveData?.observe(viewLifecycleOwner, Observer {
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
            it.save = false
            Repo.insertData(context!!, it)
        }
        swiper?.isRefreshing = false
    }

    private fun connectionError(error: Error?) {
        swiper?.isRefreshing = false
        Toast.makeText(context, "${error?.message}", Toast.LENGTH_SHORT).show()

    }

    override fun onRefresh() {
        pViewerViewModel?.getFeed()
    }

    fun setIsSaveScreen(boolean: Boolean) {
        isSave = boolean
    }

}



