package com.sqli.mvvmapp.mvvm.comments.model.entity

import com.sqli.mvvmapp.mvvm.post.model.entity.Post

data class PostAndComments(val post: Post, val comments: List<Comment>)