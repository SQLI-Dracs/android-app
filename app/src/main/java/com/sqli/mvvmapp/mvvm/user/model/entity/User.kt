package com.sqli.mvvmapp.mvvm.user.model.entity

data class User(val id: Long, val name: String, val username: String, val email: String, val phone: String,
                val website: String, val company: Company)
