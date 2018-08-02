package com.sqli.mvvmapp.mvvm.user.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.view.ViewPager
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import com.sqli.mvvmapp.R
import com.sqli.mvvmapp.base.ToolbarExtensions.setToolbar
import com.sqli.mvvmapp.common.Navigator
import com.sqli.mvvmapp.databinding.ActivityUserDetailBinding
import com.sqli.mvvmapp.di.components.DaggerNavigatorComponent
import com.sqli.mvvmapp.di.components.NavigatorComponent
import com.sqli.mvvmapp.di.modules.NavigatorModule
import dagger.Lazy
import kotlinx.android.synthetic.main.activity_user_detail.*
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class UserDetailActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener, ViewPager.OnPageChangeListener {
    companion object {


        const val USER_ID: String = "USER_ID"
        const val USER_NAME: String = "USER_NAME"
        const val DEFAULT_USER_ID: Long = -1
    }

    lateinit var component: NavigatorComponent

    @Inject
    lateinit var navigator: Lazy<Navigator>

    private lateinit var userDetailViewPagerAdapter: UserDetailViewPagerAdapter
    lateinit var binding: ActivityUserDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userId: Long = intent?.getLongExtra(USER_ID, DEFAULT_USER_ID) ?: DEFAULT_USER_ID
        val userName: String = intent?.getStringExtra(USER_NAME) ?: ""

        userDetailViewPagerAdapter = UserDetailViewPagerAdapter(supportFragmentManager, userId)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_user_detail)
        binding.listenerContainer = this
        binding.pageChangeListener = this
        binding.title = getString(R.string.user_menu_item_post)
        binding.userName = userName
        binding.adapter = userDetailViewPagerAdapter

        component = DaggerNavigatorComponent
                .builder()
                .navigatorModule(NavigatorModule(this))
                .build()
        component.inject(this)

        setToolbar(toolbar)

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_user_detail_item_post ->
                vpgUserDetail.setCurrentItem(UserDetailViewPagerAdapter.FRAGMENT_POST, true)

            R.id.menu_user_detail_item_album ->
                vpgUserDetail.setCurrentItem(UserDetailViewPagerAdapter.FRAGMENT_ALBUM, true)

            R.id.men_user_detail_item_todo ->
                vpgUserDetail.setCurrentItem(UserDetailViewPagerAdapter.FRAGMENT_TODO, true)

        }
        return true
    }

    fun getNavigatorComponent(): NavigatorComponent = component

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        when (position) {
            UserDetailViewPagerAdapter.FRAGMENT_POST -> {
                bnvUserMenu.selectedItemId = R.id.menu_user_detail_item_post
                binding.title = getString(R.string.user_menu_item_post)
            }
            UserDetailViewPagerAdapter.FRAGMENT_ALBUM -> {
                bnvUserMenu.selectedItemId = R.id.menu_user_detail_item_album
                binding.title = getString(R.string.user_menu_item_album)
            }
            UserDetailViewPagerAdapter.FRAGMENT_TODO -> {
                bnvUserMenu.selectedItemId = R.id.men_user_detail_item_todo
                binding.title = getString(R.string.user_menu_item_todo)
            }

        }
    }
}
