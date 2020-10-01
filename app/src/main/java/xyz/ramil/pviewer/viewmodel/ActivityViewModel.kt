package xyz.ramil.pviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import xyz.ramil.pviewer.data.Event
import xyz.ramil.pviewer.model.FeedModel
import xyz.ramil.pviewer.model.PostModel

class ActivityViewModel : BaseViewModel() {

    val feedLiveData = MutableLiveData<Event<FeedModel>>()
    val postLiveData = MutableLiveData<Event<PostModel>>()

    fun getPost(id: Int) {
        requestWithLiveData(postLiveData) {
            api.getStory(id)
        }
    }

    fun getFeed() {
        requestWithLiveData(feedLiveData) {
            api.getFeed()
        }
    }

}