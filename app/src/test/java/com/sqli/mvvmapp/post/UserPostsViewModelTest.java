package com.sqli.mvvmapp.post;

import com.sqli.mvvmapp.base.BaseTest;
import com.sqli.mvvmapp.common.Navigator;
import com.sqli.mvvmapp.mvvm.post.model.entity.Post;
import com.sqli.mvvmapp.mvvm.post.model.repository.data.PostRepository;
import com.sqli.mvvmapp.mvvm.post.viewmodel.UserPostsViewModel;
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

public class UserPostsViewModelTest extends BaseTest {

    private static final int NUM_POSTS = 6;
    private static final long USER_ID_OK = 1;
    private static final long USER_ID_KO = 2;

    @Mock
    private PostRepository postRepository;

    @Mock
    private Navigator navigator;

    private UserPostsViewModel userPostsViewModel;

    private List<Post> postList;
    private Throwable throwable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        postList = FakeDataProvider.getInstance().getTestUserPosts(USER_ID_OK, NUM_POSTS);
        throwable = new Exception();

        when(postRepository.getPosts(USER_ID_OK)).thenReturn(testDataPostSingle());
        when(postRepository.getPosts(USER_ID_KO)).thenReturn(testErrorSingle());

        userPostsViewModel = new UserPostsViewModel(() -> postRepository, () -> navigator);
    }

    private Observable<List<Post>> testErrorSingle() {
        return Observable.error(throwable);
    }

    @Test
    public void postRetrieveTest() {

        //Test posts OK
        userPostsViewModel.setUserId(USER_ID_OK);
        userPostsViewModel.retrievePosts();
        assertTrue(userPostsViewModel.getItems().get().equals(postList));

        //Test posts KO
        userPostsViewModel.setUserId(USER_ID_KO);
        userPostsViewModel.retrievePosts();
        Mockito.verify(navigator, Mockito.times(1)).showError(throwable);

    }

    private Observable<List<Post>> testDataPostSingle() {
        return Observable.just(postList);
    }
}
