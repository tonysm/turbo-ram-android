package com.tighten.turboram

const val APP_URL = "http://192.168.0.10"
const val HOME_URL = "${APP_URL}/dashboard"
const val LOGIN_URL = "${APP_URL}/login"
const val DASHBOARD_URL = "${APP_URL}/dashboard"
const val POSTS_URL = "${APP_URL}/buckets/:bucket/blogs/:blog/posts"
const val CREATE_POST_URL = "${APP_URL}/buckets/:bucket/blogs/:blog/posts/create"
const val CREATE_COMMENT_URL = "${APP_URL}/buckets/:bucket/recordings/:recording/comments/create"