package co.marvel.math.utils

import androidx.test.espresso.IdlingResource

class FetchingIdlingResource: IdlingResource, FetcherListener {
    private var idle: Boolean = true
    private var resourceCallback: IdlingResource.ResourceCallback? = null

    override fun getName(): String {
        return FetchingIdlingResource::class.java.simpleName
    }

    override fun isIdleNow() = idle

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        resourceCallback = callback
    }

    override fun doneFetching() {
        idle = true
        resourceCallback?.onTransitionToIdle()
    }

    override fun beginFetching() {
        idle = false
    }

    companion object {
        @Volatile
        private var instance: FetchingIdlingResource? = null

        @JvmStatic
        fun sharedInstance(): FetchingIdlingResource =
                instance ?: synchronized(FetchingIdlingResource::class.java) {

                    instance ?: FetchingIdlingResource().also {
                        instance = it
                    }
                }
    }
}