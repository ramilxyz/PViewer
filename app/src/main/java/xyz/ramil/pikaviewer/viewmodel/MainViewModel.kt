package xyz.ramil.pikaviewer.viewmodel

import androidx.lifecycle.MutableLiveData
import xyz.ramil.pikaviewer.data.Event
import xyz.ramil.pikaviewer.model.PostModel

class MainViewModel : BaseViewModel() {

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