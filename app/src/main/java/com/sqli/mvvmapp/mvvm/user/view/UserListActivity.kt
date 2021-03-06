package com.sqli.mvvmapp.mvvm.user.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sqli.mvvmapp.R
//import com.sqli.mvvmapp.di.components.DaggerNavigatorComponent
import com.sqli.mvvmapp.common.Navigator
import com.sqli.mvvmapp.di.components.DaggerNavigatorComponent
import com.sqli.mvvmapp.di.components.NavigatorComponent
import com.sqli.mvvmapp.di.modules.NavigatorModule
import dagger.Lazy
import javax.inject.Inject

class UserListActivity : AppCompatActivity() {

    private lateinit var component: NavigatorComponent

    @Inject
    lateinit var navigator: Lazy<Navigator>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        component = DaggerNavigatorComponent.builder().navigatorModule(NavigatorModule(this)).build()
        component.inject(this)
        navigator.get().showUserListFragment(this, R.id.flRootLayout)
    }


     fun getNavigatorComponent(): NavigatorComponent = component
}
