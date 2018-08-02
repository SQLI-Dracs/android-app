package com.sqli.mvvmapp.album

import com.nhaarman.mockito_kotlin.whenever
import com.sqli.mvvmapp.base.BaseTest
import com.sqli.mvvmapp.common.Navigator
import com.sqli.mvvmapp.mvvm.album.model.entity.Album
import com.sqli.mvvmapp.mvvm.album.model.repository.data.AlbumRepository
import com.sqli.mvvmapp.mvvm.album.viewmodel.UserAlbumsViewModel
import com.sqli.mvvmapp.utils.FakeDataProvider
import dagger.Lazy
import io.reactivex.Observable
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserAlbumsViewModelTest : BaseTest() {

    @Mock
    private lateinit var albumRepository: AlbumRepository

    @Mock
    private lateinit var navigator: Navigator

    private lateinit var userAlbumsViewModel: UserAlbumsViewModel

    private lateinit var albumList: List<Album>
    private lateinit var throwable: Throwable

    companion object {

        private const val NUM_ALBUMS = 12
        private const val USER_ID_OK: Long = 1
        private const val USER_ID_KO: Long = 2
    }

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        albumList = FakeDataProvider.getInstance().getTestUserAlbums(USER_ID_OK, NUM_ALBUMS)
        throwable = Exception()


        whenever(albumRepository.getAlbums(USER_ID_OK)).thenReturn(testDataAlbumSingle())
        whenever(albumRepository.getAlbums(USER_ID_KO)).thenReturn(testErrorSingle())

        userAlbumsViewModel = UserAlbumsViewModel(Lazy { albumRepository }, Lazy { navigator })
    }

    @Test
    fun albumRetrieveTest() {

        //Test albums OK
        userAlbumsViewModel.retrieveAlbums(USER_ID_OK)
        assertTrue(userAlbumsViewModel.items.get() == albumList)

        //Test albums KO
        userAlbumsViewModel.retrieveAlbums(USER_ID_KO)
        Mockito.verify(navigator, Mockito.times(1)).showError(throwable)

    }

    private fun testDataAlbumSingle(): Observable<List<Album>> {
        return Observable.just(albumList)
    }

    private fun testErrorSingle(): Observable<List<Album>> {
        return Observable.error(throwable)
    }


}
