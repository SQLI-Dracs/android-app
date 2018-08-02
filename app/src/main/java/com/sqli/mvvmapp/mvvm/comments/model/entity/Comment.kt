package com.sqli.mvvmapp.mvvm.comments.model.entity

data class Comment(val id: Long, val postId: Long, val name: String, val email: String, val body: String)