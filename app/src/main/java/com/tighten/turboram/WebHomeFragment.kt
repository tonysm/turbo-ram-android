package com.tighten.turboram

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.ValueCallback
import androidx.core.view.isVisible
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.gson.Gson
import com.tighten.turboram.data.CurrentBlogData
import dev.hotwire.turbo.nav.TurboNavGraphDestination
import dev.hotwire.turbo.visit.TurboVisitAction
import dev.hotwire.turbo.visit.TurboVisitOptions

@TurboNavGraphDestination(uri = "turbo://fragment/web/home")
class WebHomeFragment : WebFragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_web_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMenuBar()
        setupNewPostFab()
    }

    private fun initMenuBar() {
        setListeners()
        markCurrentIconActive()
    }

    private fun setupNewPostFab() {
        val fab = view?.findViewById<FloatingActionButton>(R.id.new_post_fab)

        fab?.setOnClickListener { _view ->
            navigateToCreateBlog()
        }

        fab?.isVisible = location.endsWith("/posts")
    }

    private fun navigateToCreateBlog() {
        session.webView.evaluateJavascript("(function () { return window.TurboRamBridge.queryBucketAndBlog(); })();", ValueCallback { value ->
            val currentBlog = Gson().fromJson(value.trimIndent(), CurrentBlogData::class.java)

            navigate(CREATE_POST_URL
                .replace(":bucket", currentBlog.bucket)
                .replace(":blog", currentBlog.blog))
        });
    }

    private fun markCurrentIconActive() {
        menuBarForNavigation()?.menu?.findItem(when(location.endsWith("/dashboard")) {
            true -> R.id.dashboard_btn
            else -> R.id.blog_btn
        })?.setChecked(true)
    }

    private fun setListeners() {
        menuBarForNavigation()?.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.dashboard_btn -> {
                    navigate(DASHBOARD_URL, options = TurboVisitOptions(
                        TurboVisitAction.REPLACE
                    ))
                }

                R.id.blog_btn -> {
                    session.webView.evaluateJavascript("(function () { return window.TurboRamBridge.queryBucketAndBlog(); })();", ValueCallback { value ->
                        val currentBlog = Gson().fromJson(value.trimIndent(), CurrentBlogData::class.java)

                        navigate(
                            POSTS_URL
                                .replace(":bucket", currentBlog.bucket)
                                .replace(":blog", currentBlog.blog),
                            options = TurboVisitOptions(TurboVisitAction.REPLACE)
                        )
                    })
                }
            }

            true
        }
    }

    private fun menuBarForNavigation(): BottomNavigationView? {
        return view?.findViewById(R.id.home_bottom_navigation)
    }
}