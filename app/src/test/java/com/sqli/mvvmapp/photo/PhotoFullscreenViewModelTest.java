package com.sqli.mvvmapp.photo;

import com.sqli.mvvmapp.base.BaseTest;
import com.sqli.mvvmapp.common.Navigator;
import com.sqli.mvvmapp.mvvm.photo.model.entity.Photo;
import com.sqli.mvvmapp.mvvm.photo.model.repository.data.PhotoRepository;
import com.sqli.mvvmapp.mvvm.photo.viewmodel.PhotoFullscreenViewModel;
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

public class PhotoFullscreenViewModelTest extends BaseTest {

    private static final long PHOTO_ID_OK = 1;
    private static final long PHOTO_ID_KO = 2;

    @Mock
    private PhotoRepository photoRepository;

    @Mock
    private Navigator navigator;

    private PhotoFullscreenViewModel photoFullscreenViewModel;

    private Photo photo;

    private Throwable throwable;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        photo = FakeDataProvider.getInstance().getTestPhoto(PHOTO_ID_OK, "");
        throwable = new Exception();

        when(photoRepository.getPhoto(PHOTO_ID_OK)).thenReturn(testDataPhotoSingle());
        when(photoRepository.getPhoto(PHOTO_ID_KO)).thenReturn(testErrorSingle());

        photoFullscreenViewModel = new PhotoFullscreenViewModel(() -> photoRepository, () -> navigator);

    }

    @Test
    public void photoRetrieveTest(){
        //Test OK
        photoFullscreenViewModel.setPhotoId(PHOTO_ID_OK);
        photoFullscreenViewModel.retievePhoto();
        assertTrue(photoFullscreenViewModel.getUrl().get().equalsIgnoreCase(photo.getUrl()));

        //Test KO
        photoFullscreenViewModel.setPhotoId(PHOTO_ID_KO);
        photoFullscreenViewModel.retievePhoto();
        Mockito.verify(navigator, Mockito.times(1)).showError(throwable);
    }

    private Observable<Photo> testErrorSingle() {
        return Observable.error(throwable);
    }

    private Observable<Photo> testDataPhotoSingle() {
        return Observable.just(photo);
    }

}
