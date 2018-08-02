package com.sqli.mvvmapp.comment;

import com.sqli.mvvmapp.base.BaseTest;
import com.sqli.mvvmapp.common.Navigator;
import com.sqli.mvvmapp.mvvm.comments.model.entity.PostAndComments;
import com.sqli.mvvmapp.mvvm.comments.viewmodel.PostCommentsViewModel;
import com.sqli.mvvmapp.mvvm.post.model.repository.data.PostRepository;
import com.sqli.mvvmapp.utils.FakeDataProvider;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import io.reactivex.Observable;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

public class PostAndCommentsViewModelTest extends BaseTest{

    private static final int NUM_COMMENTS = 10;
    private static final long POST_ID_OK = 1;
    private static final long POST_ID_KO = 2;

    @Mock
    private PostRepository postRepository;

    @Mock
    private Navigator navigator;

    private PostCommentsViewModel postCommentsViewModel;

    private PostAndComments postAndComments;

    private Throwable throwable;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);

        postAndComments = FakeDataProvider.getInstance().getPostAndComments(POST_ID_OK, NUM_COMMENTS);
        throwable = new Exception();

        when(postRepository.getPostAndComments(POST_ID_OK)).thenReturn(testDataSingle());
        when(postRepository.getPostAndComments(POST_ID_KO)).thenReturn(testErrorSingle());

        postCommentsViewModel = new PostCommentsViewModel(() -> postRepository, () -> navigator);

    }

    @Test
    public void postAndCommentsRetrieveTest() {
        //Test OK
        postCommentsViewModel.setPostId(POST_ID_OK);
        postCommentsViewModel.getPostAndComments();
        assertTrue(postCommentsViewModel.getItems().get().equals(postAndComments.getComments()));
        assertTrue(postCommentsViewModel.getPost().get().equals(postAndComments.getPost()));

        //Test KO
        postCommentsViewModel.setPostId(POST_ID_KO);
        postCommentsViewModel.getPostAndComments();
        Mockito.verify(navigator, Mockito.times(1)).showError(throwable);
    }

    private Observable<PostAndComments> testErrorSingle() {
        return Observable.error(throwable);
    }

    private Observable<PostAndComments> testDataSingle() {
        return Observable.just(postAndComments);
    }

}
