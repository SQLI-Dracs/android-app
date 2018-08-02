package com.sqli.mvvmapp.mvvm.post.model.mapper

import com.sqli.mvvmapp.mvvm.post.model.db.PostEntity
import com.sqli.mvvmapp.mvvm.post.model.entity.Post
import javax.inject.Inject

class PostMapper @Inject constructor() {

    fun transform(post: Post): PostEntity = PostEntity(post.userId, post.id, post.title, post.body)

}