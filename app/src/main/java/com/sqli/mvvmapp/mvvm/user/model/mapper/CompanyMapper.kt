package com.sqli.mvvmapp.mvvm.user.model.mapper

import com.sqli.mvvmapp.mvvm.user.model.db.CompanyEntity
import com.sqli.mvvmapp.mvvm.user.model.entity.Company
import javax.inject.Inject

class CompanyMapper @Inject constructor() {

    fun transform(company: Company): CompanyEntity {
        return CompanyEntity(company.name, company.catchPhrase, company.bs)
    }
}