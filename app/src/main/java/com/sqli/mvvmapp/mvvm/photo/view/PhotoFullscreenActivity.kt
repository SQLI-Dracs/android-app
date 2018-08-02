package com.sqli.mvvmapp.mvvm.photo.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.sqli.mvvmapp.R
import com.sqli.mvvmapp.common.Navigator
import com.sqli.mvvmapp.di.components.DaggerNavigatorComponent
import com.sqli.mvvmapp.di.components.NavigatorComponent
import com.sqli.mvvmapp.di.modules.NavigatorModule
import javax.inject.Inject
import dagger.Lazy

class PhotoFullscreenActivity : AppCompatActivity() {

    companion object {
        const val PHOTO_ID = "PHOTO_ID"
    }

    private lateinit var component: NavigatorComponent

    @Inject
    protected lateinit var navigator: Lazy<Navigator>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_fullscreen)
        component = DaggerNavigatorComponent
                .builder()
                .navigatorModule(NavigatorModule(this))
                .build()
        component.inject(this)
        navigator.get()
                .showPhotoFullscreenFragment(this, R.id.flRootLayout,intent?.getLongExtra(PHOTO_ID, -1))
    }

    fun getNavigatorComponent(): NavigatorComponent = component

}