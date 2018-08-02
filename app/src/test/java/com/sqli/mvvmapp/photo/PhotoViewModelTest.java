package com.sqli.mvvmapp.photo;

import com.sqli.mvvmapp.base.BaseTest;
import com.sqli.mvvmapp.common.Navigator;
import com.sqli.mvvmapp.mvvm.photo.model.entity.Photo;
import com.sqli.mvvmapp.mvvm.photo.model.repository.data.PhotoRepository;
import com.sqli.mvvmapp.mvvm.photo.viewmodel.PhotoGridViewModel;
import com.sqli.mvvmapp.utils.FakeDataProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Observable;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class PhotoViewModelTest extends BaseTest {

    private static final int NUM_PHOTOS = 10;
    private static final long ALBUM_ID_OK = 1;
    private static final long ALBUM_ID_KO = 2;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private Navigator navigator;

    private PhotoGridViewModel photoGridViewModel;

    private List<Photo> photoList;

    private Throwable throwable;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        photoList = FakeDataProvider.getInstance().getTestPhotos(ALBUM_ID_OK, NUM_PHOTOS);
        throwable = new Exception();

        when(photoRepository.getPhotos(ALBUM_ID_OK)).thenReturn(testDataPhotoSingle());
        when(photoRepository.getPhotos(ALBUM_ID_KO)).thenReturn(testErrorSingle());

        photoGridViewModel = new PhotoGridViewModel(() -> photoRepository, () -> navigator);

    }

    @Test
    public void photoRetrieveTest(){
        //Test OK
        photoGridViewModel.setAlbumId(ALBUM_ID_OK);
        photoGridViewModel.retrievePhotos();
        assertTrue(photoGridViewModel.getItems().get().equals(photoList));

        //Test KO
        photoGridViewModel.setAlbumId(ALBUM_ID_KO);
        photoGridViewModel.retrievePhotos();
        Mockito.verify(navigator, Mockito.times(1)).showError(throwable);
    }

    private Observable<List<Photo>> testErrorSingle() {
        return Observable.error(throwable);
    }

    private Observable<List<Photo>> testDataPhotoSingle() {
        return Observable.just(photoList);
    }

}
