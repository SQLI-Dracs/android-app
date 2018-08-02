package com.sqli.mvvmapp.photo;

import com.sqli.mvvmapp.base.BaseTest;
import com.sqli.mvvmapp.mvvm.photo.model.entity.Photo;
import com.sqli.mvvmapp.mvvm.photo.model.mapper.PhotoMapper;
import com.sqli.mvvmapp.mvvm.photo.model.repository.data.PhotoDataFactory;
import com.sqli.mvvmapp.mvvm.photo.model.repository.data.PhotoDataRepository;
import com.sqli.mvvmapp.mvvm.photo.model.repository.datasource.PhotoDBDataSource;
import com.sqli.mvvmapp.mvvm.photo.model.repository.datasource.PhotoNetworkDataSource;
import com.sqli.mvvmapp.utils.FakeDataProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class PhotoDataRepositoryTest extends BaseTest{

    public static final Long ALBUM_ID_FROM_NETWORK = 0L;
    public static final Long ALBUM_ID_FROM_DATABASE = 1L;

    public static final Long PHOTO_ID_FROM_NETWORK = 2L;
    public static final Long PHOTO_ID_FROM_DATABASE = 3L;

    public static final int NUM_OBJECTS_FROM_NETWORK = 3;
    public static final int NUM_OBJECTS_FROM_DATABASE = 5;

    private PhotoMapper photoMapper;
    private TestObserver<List<Photo>> testObserverList;
    private TestObserver<Photo> testObserverPhoto;

    private List<Photo> photoListFromNetwork;
    private List<Photo> photoListFromDatabase;
    private Photo photoFromDatabase;
    private Photo photoFromNetwork;
    private Throwable throwable;

    @Mock
    private PhotoDataFactory photoDataFactory;

    @Mock
    private PhotoDBDataSource photoDBDataSource;

    @Mock
    private PhotoNetworkDataSource photoNetworkDataSource;

    private PhotoDataRepository photoDataRepository;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        photoMapper = new PhotoMapper();
        testObserverList = new TestObserver<>();
        testObserverPhoto = new TestObserver<>();
        throwable = new Exception();

        photoListFromDatabase = FakeDataProvider.getInstance().getTestPhotos(ALBUM_ID_FROM_DATABASE, NUM_OBJECTS_FROM_DATABASE);
        photoListFromNetwork = FakeDataProvider.getInstance().getTestPhotos(ALBUM_ID_FROM_NETWORK, NUM_OBJECTS_FROM_NETWORK);

        photoFromDatabase = FakeDataProvider.getInstance().getTestPhoto(0, "fromDB");
        photoFromNetwork = FakeDataProvider.getInstance().getTestPhoto(1, "fromNW");

        when(photoDataFactory.createDBDataSource()).thenReturn(photoDBDataSource);
        when(photoDataFactory.createNetworkDataSource()).thenReturn(photoNetworkDataSource);

        when(photoNetworkDataSource.getPhotos(ALBUM_ID_FROM_NETWORK)).thenReturn(testDataNetworkPhotoListSingle());
        when(photoNetworkDataSource.getPhotos(ALBUM_ID_FROM_DATABASE)).thenReturn(testErrorListNetwork());
        when(photoNetworkDataSource.getPhoto(PHOTO_ID_FROM_NETWORK)).thenReturn(testDataNetworkPhotoSingle());
        when(photoNetworkDataSource.getPhoto(PHOTO_ID_FROM_DATABASE)).thenReturn(testErrorNetwork());

        when(photoDBDataSource.insert(any())).thenReturn(testCompletable());
        when(photoDBDataSource.delete(any())).thenReturn(testCompletable());

        when(photoDBDataSource.getPhotos(ALBUM_ID_FROM_DATABASE)).thenReturn(testDataDBPhotoSingle());
        when(photoDBDataSource.getPhotos(ALBUM_ID_FROM_NETWORK)).thenReturn(testDataDBPhotoSingle());
        when(photoDBDataSource.getPhoto(PHOTO_ID_FROM_DATABASE)).thenReturn(testDataDatabasePhotoSingle());
        when(photoDBDataSource.getPhoto(PHOTO_ID_FROM_NETWORK)).thenReturn(testDataDatabasePhotoSingle());

        photoDataRepository = new PhotoDataRepository(() -> photoMapper, () -> photoDataFactory);
    }

    @Test
    public void testPhotoRetrieving(){

        //Test OK List
        testObserverList = photoDataRepository.getPhotos(ALBUM_ID_FROM_NETWORK).test();

        Mockito.verify(photoDBDataSource, Mockito.times(NUM_OBJECTS_FROM_NETWORK)).delete(ArgumentMatchers.any());
        Mockito.verify(photoDBDataSource, Mockito.times(NUM_OBJECTS_FROM_NETWORK)).insert(ArgumentMatchers.any());
        Mockito.verify(photoDBDataSource, Mockito.times(1)).getPhotos(ALBUM_ID_FROM_NETWORK);
        Mockito.verify(photoNetworkDataSource, Mockito.times(1)).getPhotos(ALBUM_ID_FROM_NETWORK);

        testObserverList.assertNoErrors();
        testObserverList.assertComplete();
        testObserverList.assertValues(photoListFromDatabase, photoListFromNetwork);

        //Test KO List
        testObserverList = photoDataRepository.getPhotos(ALBUM_ID_FROM_DATABASE).test();
        Mockito.verify(photoDBDataSource, Mockito.times(1)).getPhotos(ALBUM_ID_FROM_DATABASE);
        testObserverList.assertError(throwable);
        testObserverList.assertNotComplete();
        testObserverList.assertValue(photoListFromDatabase);

        //Test OK Single
        testObserverPhoto = photoDataRepository.getPhoto(PHOTO_ID_FROM_NETWORK).test();
        Mockito.verify(photoDBDataSource, Mockito.times(NUM_OBJECTS_FROM_NETWORK+1)).delete(any());
        Mockito.verify(photoDBDataSource, Mockito.times(NUM_OBJECTS_FROM_NETWORK+1)).insert(any());
        Mockito.verify(photoDBDataSource, Mockito.times(1)).getPhoto(PHOTO_ID_FROM_NETWORK);
        Mockito.verify(photoNetworkDataSource, Mockito.times(1)).getPhoto(PHOTO_ID_FROM_NETWORK);
        testObserverPhoto.assertNoErrors();
        testObserverPhoto.assertComplete();
        testObserverPhoto.assertValues(photoFromDatabase, photoFromNetwork);

        //Test KO Single
        testObserverPhoto = photoDataRepository.getPhoto(PHOTO_ID_FROM_DATABASE).test();
        Mockito.verify(photoDBDataSource, Mockito.times(1)).getPhoto(PHOTO_ID_FROM_DATABASE);
        testObserverPhoto.assertError(throwable);
        testObserverPhoto.assertNotComplete();
        testObserverPhoto.assertValue(photoFromDatabase);

    }

    private Single<Photo> testErrorNetwork() {
        return Single.error(throwable);
    }

    private Single<Photo> testDataNetworkPhotoSingle() {
        return Single.just(photoFromNetwork);
    }

    private Single<Photo> testDataDatabasePhotoSingle() {
        return Single.just(photoFromDatabase);
    }

    private Single<List<Photo>> testDataNetworkPhotoListSingle() {
        return Single.just(photoListFromNetwork);
    }

    private Single<List<Photo>> testDataDBPhotoSingle() {
        return Single.just(photoListFromDatabase);
    }

    private Completable testCompletable() {
        return Completable.complete();
    }

    private Single<List<Photo>> testErrorListNetwork() {
        return Single.error(throwable);
    }
}
