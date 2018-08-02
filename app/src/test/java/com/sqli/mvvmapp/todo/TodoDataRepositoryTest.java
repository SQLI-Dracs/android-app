package com.sqli.mvvmapp.todo;

import com.sqli.mvvmapp.base.BaseTest;
import com.sqli.mvvmapp.mvvm.album.model.db.AlbumEntity;
import com.sqli.mvvmapp.mvvm.album.model.entity.Album;
import com.sqli.mvvmapp.mvvm.album.model.mapper.AlbumMapper;
import com.sqli.mvvmapp.mvvm.album.model.repository.data.AlbumDataFactory;
import com.sqli.mvvmapp.mvvm.album.model.repository.data.AlbumDataRepository;
import com.sqli.mvvmapp.mvvm.album.model.repository.datasource.AlbumDBDataSource;
import com.sqli.mvvmapp.mvvm.album.model.repository.datasource.AlbumNetworkDataSource;
import com.sqli.mvvmapp.mvvm.todo.model.db.TodoEntity;
import com.sqli.mvvmapp.mvvm.todo.model.entity.Todo;
import com.sqli.mvvmapp.mvvm.todo.model.mapper.TodoMapper;
import com.sqli.mvvmapp.mvvm.todo.model.repository.data.TodoDataFactory;
import com.sqli.mvvmapp.mvvm.todo.model.repository.data.TodoDataRepository;
import com.sqli.mvvmapp.mvvm.todo.model.repository.datasource.TodoDBDataSource;
import com.sqli.mvvmapp.mvvm.todo.model.repository.datasource.TodoNetworkDataSource;
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

public class TodoDataRepositoryTest extends BaseTest {

    private static final int USER_ID_FROM_NETWORK = 1;
    private static final int USER_ID_FROM_DATABASE = 2;

    private static final int NUM_OBJECTS_FROM_NETWORK = 4;
    private static final int NUM_OBJECTS_FROM_DATABASE = 10;

    private TodoDataRepository todoDataRepository;

    private TodoMapper todoMapper = new TodoMapper();
    private Throwable throwable = new Exception();
    private List<Todo> todoListFromNetwork
            = FakeDataProvider.getInstance().getTestTodos(USER_ID_FROM_NETWORK, NUM_OBJECTS_FROM_NETWORK);
    private List<Todo> todoListFromDB
            = FakeDataProvider.getInstance().getTestTodos(USER_ID_FROM_DATABASE, NUM_OBJECTS_FROM_DATABASE);

    @Mock
    private TodoDataFactory todoDataFactory;

    @Mock
    private TodoDBDataSource todoDBDataSource;

    @Mock
    private TodoNetworkDataSource todoNetworkDataSource;

    private TestObserver<List<Todo>> testObserver;

    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);

        when(todoNetworkDataSource.getTodos(USER_ID_FROM_NETWORK)).thenReturn(testDataNetworkUser());
        when(todoNetworkDataSource.getTodos(USER_ID_FROM_DATABASE)).thenReturn(testErrorNetworkSingle());

        when(todoDataFactory.createNetworkDataSource()).thenReturn(todoNetworkDataSource);
        when(todoDataFactory.createDBDataSource()).thenReturn(todoDBDataSource);

        when(todoDBDataSource.insert(ArgumentMatchers.any())).thenReturn(testCompletable());
        when(todoDBDataSource.delete(ArgumentMatchers.any())).thenReturn(testCompletable());

        when(todoDBDataSource.getTodos(USER_ID_FROM_DATABASE)).thenReturn(testDataDBUser());
        when(todoDBDataSource.getTodos(USER_ID_FROM_NETWORK)).thenReturn(testDataDBUser());

        todoDataRepository = new TodoDataRepository(() -> todoMapper, () -> todoDataFactory);
    }

    @Test
    public void testTodoRetrieving(){

        testObserver = todoDataRepository.getTodo(USER_ID_FROM_NETWORK).test();

        Mockito.verify(todoDBDataSource, Mockito.times(NUM_OBJECTS_FROM_NETWORK)).delete(ArgumentMatchers.any(TodoEntity.class));
        Mockito.verify(todoDBDataSource, Mockito.times(NUM_OBJECTS_FROM_NETWORK)).insert(ArgumentMatchers.any(TodoEntity.class));
        Mockito.verify(todoDBDataSource, Mockito.times(1)).getTodos(USER_ID_FROM_NETWORK);
        Mockito.verify(todoNetworkDataSource, Mockito.times(1)).getTodos(USER_ID_FROM_NETWORK);

        testObserver.assertNoErrors();
        testObserver.assertComplete();
        testObserver.assertValues(todoListFromDB, todoListFromNetwork);

        testObserver = todoDataRepository.getTodo(USER_ID_FROM_DATABASE).test();
        Mockito.verify(todoDBDataSource, Mockito.times(1)).getTodos(USER_ID_FROM_DATABASE);
        testObserver.assertError(throwable);
        testObserver.assertNotComplete();
        testObserver.assertValue(todoListFromDB);

    }

    private Completable testCompletable() {
        return Completable.complete();
    }

    private Single<List<Todo>> testErrorNetworkSingle() {
        return Single.error(throwable);
    }

    private Single<List<Todo>> testDataNetworkUser() {
        return Single.just(todoListFromNetwork);
    }

    private Single<List<Todo>> testDataDBUser() {
        return Single.just(todoListFromDB);
    }
}
