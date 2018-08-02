package com.sqli.mvvmapp.mvvm.todo.view

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
import com.sqli.mvvmapp.databinding.FragmentUserAlbumsBinding
import com.sqli.mvvmapp.databinding.FragmentUserTodosBinding
import com.sqli.mvvmapp.di.components.DaggerFragmentComponent
import com.sqli.mvvmapp.di.components.FragmentComponent
import com.sqli.mvvmapp.di.modules.FragmentModule
import com.sqli.mvvmapp.mvvm.todo.model.entity.Todo
import com.sqli.mvvmapp.mvvm.todo.viewmodel.UserTodosViewModel
import com.sqli.mvvmapp.mvvm.user.view.UserDetailActivity
import com.sqli.mvvmapp.mvvm.view.fragment.UserAlbumsFragment
import javax.inject.Inject
import dagger.Lazy

class UserTodosFragment : Fragment(), BindableRecyclerAdapter.OnClickListener<Todo> {
    companion object {

        const val USER_ID: String = "USER_ID"
        fun newInstance(userId: Long): UserTodosFragment {

            var userTodosFragment = UserTodosFragment()
            var bundle = Bundle()

            bundle.putLong(USER_ID, userId)

            userTodosFragment.arguments = bundle

            return userTodosFragment
        }

    }
    @Inject
    lateinit var userTodosViewModel: UserTodosViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_user_todos, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val fragmentComponent: FragmentComponent = DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule(this))
                .navigatorComponent((activity as UserDetailActivity).getNavigatorComponent())
                .applicationComponent((activity?.application as MVVMApplication).component)
                .build()
        fragmentComponent.inject(this)


        val fragmentUserTodosBinding: FragmentUserTodosBinding? = view?.let { DataBindingUtil.bind(it) }

        fragmentUserTodosBinding?.clickHandler = this
        fragmentUserTodosBinding?.viewModel = userTodosViewModel

        userTodosViewModel.userId = arguments?.getLong(UserAlbumsFragment.USER_ID) ?: -1
        userTodosViewModel.start()
    }

    override fun onStop() {
        super.onStop()
        userTodosViewModel.clear()
    }


    override fun onClick(item: Todo) {

    }

}
