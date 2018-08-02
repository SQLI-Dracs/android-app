package com.sqli.mvvmapp.album;

import com.sqli.mvvmapp.base.BaseTest;
import com.sqli.mvvmapp.mvvm.album.model.db.AlbumEntity;
import com.sqli.mvvmapp.mvvm.album.model.entity.Album;
import com.sqli.mvvmapp.mvvm.album.model.mapper.AlbumMapper;
import com.sqli.mvvmapp.mvvm.album.model.repository.data.AlbumDataFactory;
import com.sqli.mvvmapp.mvvm.album.model.repository.data.AlbumDataRepository;
import com.sqli.mvvmapp.mvvm.album.model.repository.datasource.AlbumDBDataSource;
import com.sqli.mvvmapp.mvvm.album.model.repository.datasource.AlbumNetworkDataSource;
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

import static org.mockito.Mockito.when;

public class AlbumDataRepositoryTest extends BaseTest{

    private static final int USER_ID_FROM_NETWORK = 1;
    private static final int USER_ID_FROM_DATABASE = 2;

    private static final int NUM_OBJECTS_FROM_NETWORK = 4;
    private static final int NUM_OBJECTS_FROM_DATABASE = 10;

    private AlbumDataRepository albumDataRepository;

    private AlbumMapper albumMapper = new AlbumMapper();
    private Throwable throwable = new Exception();
    private List<Album> albumListFromNetwork
            = FakeDataProvider.getInstance().getTestUserAlbums(USER_ID_FROM_NETWORK, NUM_OBJECTS_FROM_NETWORK);
    private List<Album> albumListFromDB
            = FakeDataProvider.getInstance().getTestUserAlbums(USER_ID_FROM_DATABASE, NUM_OBJECTS_FROM_DATABASE);

    @Mock
    private AlbumDataFactory albumDataFactory;

    @Mock
    private AlbumDBDataSource albumDBDataSource;

    @Mock
    private AlbumNetworkDataSource albumNetworkDataSource;

    private TestObserver<List<Album>> testObserver;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        when(albumDataFactory.createDBDataSource()).thenReturn(albumDBDataSource);
        when(albumDataFactory.createNetworkDataSource()).thenReturn(albumNetworkDataSource);

        when(albumNetworkDataSource.getAlbums(USER_ID_FROM_NETWORK)).thenReturn(testDataNetworkUser());
        when(albumNetworkDataSource.getAlbums(USER_ID_FROM_DATABASE)).thenReturn(testErrorNetworkSingle());

        when(albumDBDataSource.insert(ArgumentMatchers.any())).thenReturn(testCompletable());
        when(albumDBDataSource.delete(ArgumentMatchers.any())).thenReturn(testCompletable());

        when(albumDBDataSource.getAlbums(USER_ID_FROM_NETWORK)).thenReturn(testDataDBUser());
        when(albumDBDataSource.getAlbums(USER_ID_FROM_DATABASE)).thenReturn(testDataDBUser());

        albumDataRepository = new AlbumDataRepository(() -> albumMapper, () -> albumDataFactory);
    }

    @Test
    public void testAlbumRetrieving(){
        testObserver = albumDataRepository.getAlbums(USER_ID_FROM_NETWORK).test();

        Mockito.verify(albumDBDataSource, Mockito.times(NUM_OBJECTS_FROM_NETWORK)).delete(ArgumentMatchers.any(AlbumEntity.class));
        Mockito.verify(albumDBDataSource, Mockito.times(NUM_OBJECTS_FROM_NETWORK)).insert(ArgumentMatchers.any(AlbumEntity.class));
        Mockito.verify(albumDBDataSource, Mockito.times(1)).getAlbums(USER_ID_FROM_NETWORK);
        Mockito.verify(albumNetworkDataSource, Mockito.times(1)).getAlbums(USER_ID_FROM_NETWORK);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValues(albumListFromDB, albumListFromNetwork);

        testObserver = albumDataRepository.getAlbums(USER_ID_FROM_DATABASE).test();
        Mockito.verify(albumDBDataSource, Mockito.times(1)).getAlbums(USER_ID_FROM_DATABASE);
        testObserver.assertError(throwable);
        testObserver.assertNotComplete();
        testObserver.assertValue(albumListFromDB);

    }

    private Completable testCompletable() {
        return Completable.complete();
    }

    private Single<List<Album>> testErrorNetworkSingle() {
        return Single.error(throwable);
    }

    private Single<List<Album>> testDataNetworkUser() {
        return Single.just(albumListFromNetwork);
    }

    private Single<List<Album>> testDataDBUser() {
        return Single.just(albumListFromDB);
    }
}
