package xyz.ramil.pviewer.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import xyz.ramil.pviewer.R
import xyz.ramil.pviewer.data.Status
import xyz.ramil.pviewer.model.FeedModel
import xyz.ramil.pviewer.model.PostModel
import xyz.ramil.pviewer.viewmodel.PViewerViewModel

class PostFragment : Fragment() {

    private var pViewerViewModel: PViewerViewModel? = null
    var swiper: SwipeRefreshLayout? = null

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
        swiper = view?.findViewById(R.id.swiper)
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


        val usersList: MutableList<PostModel>? = data as MutableList<PostModel>?
        usersList?.shuffle()
        usersList?.let {
            Toast.makeText(context, "${data?.size}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun viewOneError(error: Error?) {
        swiper?.isRefreshing = false
        Toast.makeText(context, "${error?.message}", Toast.LENGTH_SHORT).show()

    }

    private fun viewOneLoading2() {
        swiper?.isRefreshing = true
    }

    private fun viewOneSuccess2(data: Any?) {



            Toast.makeText(context, "${(data as PostModel).title}", Toast.LENGTH_SHORT).show()

    }

    private fun viewOneError2(error: Error?) {
        swiper?.isRefreshing = false
        Toast.makeText(context, "${error?.message}", Toast.LENGTH_SHORT).show()

    }

}



