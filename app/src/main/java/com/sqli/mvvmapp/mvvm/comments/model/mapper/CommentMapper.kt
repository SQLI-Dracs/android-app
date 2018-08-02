package com.sqli.mvvmapp.mvvm.comments.model.mapper

import com.sqli.mvvmapp.mvvm.comments.model.db.CommentEntity
import com.sqli.mvvmapp.mvvm.comments.model.entity.Comment
import javax.inject.Inject

class CommentMapper @Inject constructor(){
    fun transform(comment: Comment) : CommentEntity = CommentEntity(comment.id, comment.postId, comment.name, comment.email, comment.body)
}