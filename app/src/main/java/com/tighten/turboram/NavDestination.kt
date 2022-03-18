package com.tighten.turboram

import android.view.MenuItem
import dev.hotwire.turbo.nav.TurboNavDestination

interface NavDestination: TurboNavDestination {
    val menuProgress: MenuItem?
        get() = toolbarForNavigation()?.menu?.findItem(R.id.menu_progress)
}