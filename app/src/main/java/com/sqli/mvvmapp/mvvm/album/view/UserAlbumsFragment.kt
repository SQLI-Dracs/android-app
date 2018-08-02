package com.sqli.mvvmapp.mvvm.view.fragment

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.jaumard.recyclerviewbinding.BindableRecyclerAdapter
import com.sqli.mvvmapp.MVVMApplication
import com.sqli.mvvmapp.R
import com.sqli.mvvmapp.common.Navigator
import com.sqli.mvvmapp.databinding.FragmentUserAlbumsBinding
import com.sqli.mvvmapp.di.components.DaggerFragmentComponent
import com.sqli.mvvmapp.di.components.FragmentComponent
import com.sqli.mvvmapp.di.modules.FragmentModule
import com.sqli.mvvmapp.mvvm.album.model.entity.Album
import com.sqli.mvvmapp.mvvm.album.viewmodel.UserAlbumsViewModel
import com.sqli.mvvmapp.mvvm.user.view.UserDetailActivity
import dagger.Lazy
import javax.inject.Inject

class UserAlbumsFragment : Fragment(), BindableRecyclerAdapter.OnClickListener<Album> {

    companion object {
        const val USER_ID: String = "USER_ID"

        fun newInstance(userId: Long): UserAlbumsFragment {

            var userAlbumsFragment = UserAlbumsFragment()
            var bundle = Bundle()

            bundle.putLong(USER_ID, userId)

            userAlbumsFragment.arguments = bundle

            return userAlbumsFragment
        }
    }

    @Inject
    lateinit var userAlbumsViewModel: UserAlbumsViewModel

    @Inject
    lateinit var navigator: Lazy<Navigator>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_albums, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fragmentComponent: FragmentComponent? = DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule(this))
                .navigatorComponent((activity as UserDetailActivity).getNavigatorComponent())
                .applicationComponent((activity?.application as MVVMApplication).component)
                .build()
        fragmentComponent?.inject(this)


        val fragmentUserALbumsBinding: FragmentUserAlbumsBinding? = view?.let { DataBindingUtil.bind(it) }

        fragmentUserALbumsBinding?.clickHandler = this
        fragmentUserALbumsBinding?.viewModel = userAlbumsViewModel

        userAlbumsViewModel.setUserId(arguments?.getLong(USER_ID))
        userAlbumsViewModel.start()

    }

    override fun onClick(item: Album) {
        navigator.get().showPhotoGridActivity(item)
    }


    override fun onStop() {
        super.onStop()
        userAlbumsViewModel.clear()
    }
}