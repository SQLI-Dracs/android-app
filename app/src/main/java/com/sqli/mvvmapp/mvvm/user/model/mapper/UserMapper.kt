package com.sqli.mvvmapp.mvvm.user.model.mapper

import com.sqli.mvvmapp.mvvm.user.model.db.CompanyEntity
import com.sqli.mvvmapp.mvvm.user.model.db.UserEntity
import com.sqli.mvvmapp.mvvm.user.model.entity.Company
import com.sqli.mvvmapp.mvvm.user.model.entity.User
import javax.inject.Inject

class UserMapper @Inject constructor() {

    fun transform(user: User): UserEntity {

        val id = user.id
        val name = user.name
        val username = user.username
        val email = user.email
        val phone = user.phone
        val website = user.website
        val company = transform(user.company)

        return UserEntity(id, name, username, email, phone, website, company)
    }

    private fun transform(company: Company?): CompanyEntity {

        val name = company?.name ?: ""
        val catchPhrase = company?.catchPhrase ?: ""
        val bs = company?.bs ?: ""

        return CompanyEntity(name, catchPhrase, bs)

    }
}