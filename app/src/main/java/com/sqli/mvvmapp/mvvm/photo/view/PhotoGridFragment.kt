package com.sqli.mvvmapp.mvvm.photo.view

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.jaumard.recyclerviewbinding.BindableRecyclerAdapter
import com.sqli.mvvmapp.MVVMApplication
import com.sqli.mvvmapp.R
import com.sqli.mvvmapp.base.ToolbarExtensions.setToolbar
import com.sqli.mvvmapp.common.Navigator
import com.sqli.mvvmapp.common.utils.ImageUtils
import com.sqli.mvvmapp.databinding.FragmentPhotoGridBinding
import com.sqli.mvvmapp.di.components.DaggerFragmentComponent
import com.sqli.mvvmapp.di.components.FragmentComponent
import com.sqli.mvvmapp.di.modules.FragmentModule
import com.sqli.mvvmapp.mvvm.photo.model.entity.Photo
import com.sqli.mvvmapp.mvvm.photo.viewmodel.PhotoGridViewModel
import dagger.Lazy
import kotlinx.android.synthetic.main.toolbar.*
import javax.inject.Inject

class PhotoGridFragment : Fragment(), BindableRecyclerAdapter.OnClickListener<Photo> {
    companion object {

        const val ALBUM_ID = "ALBUM_ID"
        fun newInstance(albumId: Long): PhotoGridFragment {

            var photoGridFragment = PhotoGridFragment()
            var bundle = Bundle()

            bundle.putLong(PhotoGridFragment.ALBUM_ID, albumId)

            photoGridFragment.arguments = bundle

            return photoGridFragment
        }

    }
    @Inject
    lateinit var photoGridViewModel: PhotoGridViewModel

    @Inject
    lateinit var imageUtils: Lazy<ImageUtils>

    @Inject
    lateinit var navigator: Lazy<Navigator>

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_photo_grid, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)


        val fragmentComponent: FragmentComponent = DaggerFragmentComponent.builder()
                .fragmentModule(FragmentModule(this))
                .navigatorComponent((activity as PhotoGridActivity).navigatorComponent)
                .applicationComponent((activity?.application as MVVMApplication).component)
                .build()
        fragmentComponent.inject(this)

        val fragmentPhotoGridBinding: FragmentPhotoGridBinding? = view?.let { DataBindingUtil.bind(it) }

        fragmentPhotoGridBinding?.clickHandler = this
        fragmentPhotoGridBinding?.title = getString(R.string.photo_grid)
        fragmentPhotoGridBinding?.viewModel = photoGridViewModel
        fragmentPhotoGridBinding?.imageUtil = imageUtils.get()

        setToolbar(toolbar)

        photoGridViewModel.albumId = arguments?.getLong(ALBUM_ID) ?: -1
        photoGridViewModel.start()
    }

    override fun onStop() {
        super.onStop()
        photoGridViewModel.clear()
    }

    override fun onClick(item: Photo) {
        navigator.get().showPhotoFullscreenActivity(item)
    }

}
