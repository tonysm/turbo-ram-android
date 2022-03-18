package com.tighten.turboram

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import android.widget.Button
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import dev.hotwire.turbo.nav.TurboNavGraphDestination

@TurboNavGraphDestination(uri = "turbo://fragment/posts/show")
class ShowBlogPostFragment: WebFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_show_blog_post, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initCommentButton()
    }

    private fun initCommentButton() {
        newCommentButton()?.setOnClickListener {
            navigateToCreateComment()
        }
    }

    private fun newCommentButton(): Button? {
        return view?.findViewById(R.id.new_comment_btn)
    }

    private fun navigateToCreateComment() {
        session.webView.evaluateJavascript("(function () { return window.TurboRamBridge.queryBucketAndRecording(); })();", ValueCallback { value ->
            val currentRecording = Gson().fromJson(value.trimIndent(), CurrentRecordingData::class.java)

            navigate(CREATE_COMMENT_URL
                .replace(":bucket", currentRecording.bucket)
                .replace(":recording", currentRecording.recording))
        });
    }
}

data class CurrentRecordingData(val bucket: String, val recording: String)