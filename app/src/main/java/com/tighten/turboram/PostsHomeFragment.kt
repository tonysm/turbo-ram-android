package com.tighten.turboram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import dev.hotwire.turbo.nav.TurboNavGraphDestination

@TurboNavGraphDestination(uri = "turbo://fragment/posts/index")
class PostsHomeFragment : WebFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_posts_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        newPostFabButton()?.setOnClickListener {
            navigateToCreateBlog()
        }
    }

    private fun newPostFabButton(): FloatingActionButton? {
        return view?.findViewById(R.id.new_post_fab)
    }

    private fun navigateToCreateBlog() {
        session.webView.evaluateJavascript("(function () { return window.TurboRamBridge.queryBucketAndBlog(); })();", ValueCallback { value ->
            val currentBlog = Gson().fromJson(value.trimIndent(), CurrentBlogData::class.java)

            navigate(CREATE_POST_URL
                .replace(":bucket", currentBlog.bucket)
                .replace(":blog", currentBlog.blog))
        });
    }
}

data class CurrentBlogData(val bucket: String, val blog: String)