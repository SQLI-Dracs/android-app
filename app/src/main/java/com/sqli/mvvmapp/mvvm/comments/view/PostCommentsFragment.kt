package com.sqli.mvvmapp.mvvm.comments.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sqli.mvvmapp.MVVMApplication
import com.sqli.mvvmapp.R
import com.sqli.mvvmapp.base.ToolbarExtensions.setToolbar
import com.sqli.mvvmapp.databinding.FragmentPostCommentsBinding
import com.sqli.mvvmapp.di.components.DaggerFragmentComponent
import com.sqli.mvvmapp.di.components.FragmentComponent
import com.sqli.mvvmapp.mvvm.comments.viewmodel.PostCommentsViewModel
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class PostCommentsFragment : Fragment() {

    companion object {

        const val POST_ID = "POST_ID"

        fun newInstance(postId: Long): PostCommentsFragment {
            var postCommentsFragment: PostCommentsFragment = PostCommentsFragment()

            var bundle: Bundle = Bundle()
            bundle.putLong(POST_ID, postId)

            postCommentsFragment.arguments = bundle

            return postCommentsFragment
        }
    }

    @Inject
    protected lateinit var postCommentsViewModel: PostCommentsViewModel


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_post_comments, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        var fragmentComponent: FragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent((activity?.application as MVVMApplication).component)
                .navigatorComponent((activity as PostCommentsActivity).component)
                .build()

        fragmentComponent.inject(this)

        var postId : Long = arguments?.getLong(POST_ID) ?: -1

        var binding: FragmentPostCommentsBinding? = view?.let {DataBindingUtil.bind(it)}

        binding?.title = getString(R.string.comments)
        binding?.viewModel = postCommentsViewModel
        postCommentsViewModel.postId = postId

        setToolbar(toolbar)

        postCommentsViewModel.start()

    }

    override fun onStop() {
        super.onStop()
        postCommentsViewModel.clear()
    }
}