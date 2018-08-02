package com.sqli.mvvmapp.mvvm.comments.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sqli.mvvmapp.R
import com.sqli.mvvmapp.common.Navigator
import com.sqli.mvvmapp.di.components.DaggerNavigatorComponent
import com.sqli.mvvmapp.di.components.NavigatorComponent
import com.sqli.mvvmapp.di.modules.NavigatorModule
import javax.inject.Inject
import dagger.Lazy

class PostCommentsActivity : AppCompatActivity(){

    companion object {
        const val POST_ID = "POST_ID"
    }

    @Inject
    lateinit var navigator: Lazy<Navigator>
    lateinit var component: NavigatorComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_post_comments)

        component = DaggerNavigatorComponent
                .builder()
                .navigatorModule(NavigatorModule(this))
                .build()

        component.inject(this)

        val postId = intent?.extras?.getLong(POST_ID)

        navigator.get().showCommentsFragment(this, R.id.flRootLayout, postId)
    }

}