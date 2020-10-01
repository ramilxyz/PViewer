package xyz.ramil.pviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import xyz.ramil.pviewer.data.Event
import xyz.ramil.pviewer.model.PostModel

class PViewerViewModel : BaseViewModel() {

    val feedLiveData = MutableLiveData<Event<List<PostModel>>>()
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