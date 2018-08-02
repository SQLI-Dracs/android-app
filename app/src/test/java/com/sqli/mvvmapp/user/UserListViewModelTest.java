package com.sqli.mvvmapp.user;

import com.sqli.mvvmapp.base.BaseTest;
import com.sqli.mvvmapp.common.Navigator;
import com.sqli.mvvmapp.mvvm.user.model.entity.User;
import com.sqli.mvvmapp.mvvm.user.model.repository.data.UserRepository;
import com.sqli.mvvmapp.mvvm.user.viewmodel.UserListViewModel;
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

public class UserListViewModelTest extends BaseTest {

    public static final int NUM_USERS = 4;

    @Mock
    private UserRepository userRepository;

    @Mock
    private Navigator navigator;

    private UserListViewModel userListViewModel;

    private List<User> listUsers;
    private Throwable throwable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        listUsers = FakeDataProvider.getInstance().getTestUsers(NUM_USERS);
        throwable = new Exception();

        when(userRepository.getUsers()).thenReturn(testDataUserObservable()).thenReturn(testErrorObservable());

        userListViewModel = new UserListViewModel(() -> userRepository,
                () -> navigator);

    }


    @Test
    public void usersRetrieveTest() {

        //Testing users OK
        userListViewModel.retrieveUsers();
        assertTrue(userListViewModel.getItems().get().equals(listUsers));

        //Testing KO
        userListViewModel.retrieveUsers();
        Mockito.verify(navigator, Mockito.times(1)).showError(throwable);

    }


    private Observable<List<User>> testDataUserObservable() {
        return Observable.just(listUsers);
    }

    private Observable<List<User>> testErrorObservable() {
        return Observable.error(throwable);
    }

}
