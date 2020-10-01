package xyz.ramil.pviewer.view.fragments

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import xyz.ramil.pviewer.R
import xyz.ramil.pviewer.data.Status
import xyz.ramil.pviewer.model.PostModel
import xyz.ramil.pviewer.view.WrapContentGridLayoutManager
import xyz.ramil.pviewer.viewmodel.PViewerViewModel
import xyz.ramil.pviewer.viewrepository.PostAdapter

class PostFragment : Fragment(), SwipeRefreshLayout.OnRefreshListener {

    private var pViewerViewModel: PViewerViewModel? = null
    var swiper: SwipeRefreshLayout? = null

    //AppBarLayout appbar;
    var recyclerView: RecyclerView? = null
    var contentLayout: FrameLayout? = null

    var postAdapter: PostAdapter? = null
//    var adapterPagination: CameraPaginationAdapter? = null
//    var adapterFav: CameraFavAdapter? = null





    var currentOrientation = 0
    var displayMetrics: DisplayMetrics? = null

    private val TAG = "TAG"
    private val ACTION_UPDATE = "action_camera_update_data"
    private val ACTION_CHECK_GROUP = "action_group_checked"


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
        observeGetPosts2()
        pViewerViewModel?.getFeed()
        pViewerViewModel?.getPost(1)
    }




    fun initView() {
        swiper = view!!.findViewById(R.id.cam_swiper)
        swiper?.setOnRefreshListener(this)
        recyclerView = view!!.findViewById(R.id.cam_rv)
        recyclerView?.setHasFixedSize(true)
        recyclerView?.setLayoutManager(WrapContentGridLayoutManager(activity, 1))
        contentLayout = view!!.findViewById<FrameLayout>(R.id.rootView)

        setupRV()


    }

    fun setupRV() {

        (recyclerView?.getLayoutManager() as WrapContentGridLayoutManager).setSpanCount(1)
        postAdapter = context?.let { PostAdapter(mutableListOf(), it, view) };

        recyclerView?.adapter = postAdapter

    }


    private fun observeGetPosts() {
        pViewerViewModel?.feedLiveData?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> viewOneLoading()
                Status.SUCCESS -> viewOneSuccess(it.data)
                Status.ERROR -> viewOneError(it.error)
            }
        })
    }

    private fun observeGetPosts2() {
        pViewerViewModel?.postLiveData?.observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.LOADING -> viewOneLoading2()
                Status.SUCCESS -> viewOneSuccess2(it.data)
                Status.ERROR -> viewOneError2(it.error)
            }
        })
    }


    private fun viewOneLoading() {
        swiper?.isRefreshing = true
    }

    private fun viewOneSuccess(data: Any?) {

        swiper?.isRefreshing = false
        val usersList: MutableList<PostModel>? = data as MutableList<PostModel>?
        usersList?.shuffle()
        data?.let { postAdapter?.update(it, view) }

    }

    private fun viewOneError(error: Error?) {
        swiper?.isRefreshing = false
        Toast.makeText(context, "${error?.message}", Toast.LENGTH_SHORT).show()

    }

    private fun viewOneLoading2() {
        swiper?.isRefreshing = true
    }

    private fun viewOneSuccess2(data: Any?) {



    }

    private fun viewOneError2(error: Error?) {
        swiper?.isRefreshing = false
        Toast.makeText(context, "${error?.message}", Toast.LENGTH_SHORT).show()

    }

    override fun onRefresh() {
        pViewerViewModel?.getFeed()
    }

}



