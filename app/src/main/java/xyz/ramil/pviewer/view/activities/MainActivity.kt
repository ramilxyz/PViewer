package xyz.ramil.pviewer.view.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import xyz.ramil.pviewer.R
import xyz.ramil.pviewer.data.Status
import xyz.ramil.pviewer.model.PostModel
import xyz.ramil.pviewer.viewmodel.ActivityViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var activityViewModel: ActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        activityViewModel = ViewModelProviders.of(this).get(ActivityViewModel::class.java)
        observeGetPosts()

        buttonOneClickListener()
        buttonTwoClickListener()
    }

    private fun observeGetPosts() {
        activityViewModel.postLiveData.observe(this, Observer {
            when (it.status) {
                Status.LOADING -> viewOneLoading()
                Status.SUCCESS -> viewOneSuccess(it.data)
                Status.ERROR -> viewOneError(it.error)
            }
        })
    }


    private fun buttonOneClickListener() {
//        btn_test_one.setOnClickListener {
//            activityViewModel.getUsers(page = 1)
//        }
    }

    private fun buttonTwoClickListener() {
//        btn_test_two.setOnClickListener {
//            activityViewModel.getUsersError(page = 2) {
//                when (it.status) {
//                    Status.LOADING -> viewTwoLoading()
//                    Status.SUCCESS -> viewTwoSuccess(it.data)
//                    Status.ERROR -> viewTwoError(it.error)
//                }
//            }
//        }
    }

    private fun viewOneLoading() {

    }

    private fun viewOneSuccess(data: PostModel?) {
//        val usersList: MutableList<Users.Item>? = data?.items as MutableList<Users.Item>?
//        usersList?.shuffle()
//        usersList?.let {
//            Toast.makeText(applicationContext, "${it}", Toast.LENGTH_SHORT).show()
//        }
    }

    private fun viewOneError(error: Error?) {

    }

    private fun viewTwoLoading() {

    }

//    private fun viewTwoSuccess(data: Users?) {
//
//    }
//
//    private fun viewTwoError(error: Error?) {
//        error?.let {
//            Toast.makeText(applicationContext, error.errorMsg, Toast.LENGTH_SHORT).show()
//        }
    }