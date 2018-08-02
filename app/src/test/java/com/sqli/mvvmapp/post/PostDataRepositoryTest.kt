package com.sqli.mvvmapp.post

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.sqli.mvvmapp.base.BaseTest
import com.sqli.mvvmapp.mvvm.comments.model.db.CommentEntity
import com.sqli.mvvmapp.mvvm.comments.model.entity.PostAndComments
import com.sqli.mvvmapp.mvvm.comments.model.mapper.CommentMapper
import com.sqli.mvvmapp.mvvm.post.model.db.PostEntity
import com.sqli.mvvmapp.mvvm.post.model.entity.Post
import com.sqli.mvvmapp.mvvm.post.model.mapper.PostMapper
import com.sqli.mvvmapp.mvvm.post.model.repository.data.PostDataFactory
import com.sqli.mvvmapp.mvvm.post.model.repository.data.PostDataRepository
import com.sqli.mvvmapp.mvvm.post.model.repository.datasource.PostDBDataSource
import com.sqli.mvvmapp.mvvm.post.model.repository.datasource.PostNetworkDataSource
import com.sqli.mvvmapp.utils.FakeDataProvider
import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations


class PostDataRepositoryTest : BaseTest() {

    companion object {
        const val USER_ID_FROM_NETWORK: Long = 1
        const val USER_ID_FROM_DATABASE: Long = 2

        const val POST_ID_FROM_NETWORK: Long = 1
        const val POST_ID_FROM_DATABASE: Long = 2

        const val NUM_OBJECTS_FROM_NETWORK: Int = 5
        const val NUM_OBJECTS_FROM_DATABASE: Int = 3

        const val NUM_COMMENTS_FROM_NETWORK = 12
        const val NUM_COMMENTS_FROM_DATABASE = 33
    }

    private lateinit var postDataRepository: PostDataRepository

    private val postMapper: PostMapper = PostMapper()
    private val commentMapper: CommentMapper = CommentMapper()

    private val throwable: Throwable = Exception()

    private val postListFromNetwork: List<Post> = FakeDataProvider.getInstance().getTestUserPosts(USER_ID_FROM_NETWORK, NUM_OBJECTS_FROM_NETWORK)
    private val postListFromDatabase: List<Post> = FakeDataProvider.getInstance().getTestUserPosts(USER_ID_FROM_DATABASE, NUM_OBJECTS_FROM_DATABASE)

    private val postAndCommentsFromNetwork: PostAndComments = FakeDataProvider.getInstance().getPostAndComments(POST_ID_FROM_NETWORK, NUM_COMMENTS_FROM_NETWORK)
    private val postAndCommentsFromDatabase: PostAndComments = FakeDataProvider.getInstance().getPostAndComments(POST_ID_FROM_DATABASE, NUM_COMMENTS_FROM_DATABASE)

    @Mock
    private lateinit var postDataFactory: PostDataFactory

    @Mock
    private lateinit var postDBDataSource: PostDBDataSource

    @Mock
    private lateinit var postNetworkDataSource: PostNetworkDataSource

    private lateinit var testObserver: TestObserver<List<Post>>
    private lateinit var testObserverPostAndComments: TestObserver<PostAndComments>

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)

        whenever(postDataFactory.createDBDataSource()).thenReturn(postDBDataSource)
        whenever(postDataFactory.createNetworkDataSource()).thenReturn(postNetworkDataSource)

        whenever(postNetworkDataSource.getPosts(USER_ID_FROM_NETWORK)).thenReturn(testDataNetworkPostSingle())
        whenever(postNetworkDataSource.getPosts(USER_ID_FROM_DATABASE)).thenReturn(testErrorNetworkSingle())

        whenever(postNetworkDataSource.getPostAndComments(POST_ID_FROM_NETWORK)).thenReturn(testDataNetworkPostAndCommentSingle())
        whenever(postNetworkDataSource.getPostAndComments(POST_ID_FROM_DATABASE)).thenReturn(testErrorNetworkPostAndCommentSingle())

        whenever(postDBDataSource.insert(ArgumentMatchers.any(PostEntity::class.java))).thenReturn(testCompletable())
        whenever(postDBDataSource.delete(ArgumentMatchers.any(PostEntity::class.java))).thenReturn(testCompletable())

        whenever(postDBDataSource.insert(ArgumentMatchers.any(CommentEntity::class.java))).thenReturn(testCompletable())
        whenever(postDBDataSource.delete(ArgumentMatchers.any(CommentEntity::class.java))).thenReturn(testCompletable())


        whenever(postDBDataSource.getPosts(any())).thenReturn(testDataDBPostSingle())
        whenever(postDBDataSource.getPostAndComments(any())).thenReturn(testDataDatabasePostAndCommentSingle())

        postDataRepository = PostDataRepository(Lazy { postMapper }, Lazy { postDataFactory }, Lazy {commentMapper})

    }

    private fun testErrorNetworkPostAndCommentSingle(): Maybe<PostAndComments> {

       return Maybe.error(throwable)

    }

    private fun testDataNetworkPostAndCommentSingle(): Maybe<PostAndComments> {

        return Maybe.just(postAndCommentsFromNetwork)
    }
    private fun testDataDatabasePostAndCommentSingle(): Maybe<PostAndComments> {

        return Maybe.just(postAndCommentsFromDatabase)
    }

    @Test
    fun testPostRetrieving() {
        testObserver = postDataRepository.getPosts(USER_ID_FROM_NETWORK).test()

        Mockito.verify(postDBDataSource, Mockito.times(NUM_OBJECTS_FROM_NETWORK)).delete(ArgumentMatchers.any(PostEntity::class.java))
        Mockito.verify(postDBDataSource, Mockito.times(NUM_OBJECTS_FROM_NETWORK)).insert(ArgumentMatchers.any(PostEntity::class.java))
        Mockito.verify(postDBDataSource, Mockito.times(1)).getPosts(USER_ID_FROM_NETWORK)
        Mockito.verify(postNetworkDataSource, Mockito.times(1)).getPosts(USER_ID_FROM_NETWORK)

        testObserver.assertNoErrors()
        testObserver.assertComplete()
        testObserver.assertValues(postListFromDatabase, postListFromNetwork)

        testObserver = postDataRepository.getPosts(USER_ID_FROM_DATABASE).test()
        Mockito.verify(postDBDataSource, Mockito.times(1)).getPosts(USER_ID_FROM_DATABASE)
        testObserver.assertError(throwable)
        testObserver.assertNotComplete()
        testObserver.assertValue(postListFromDatabase)
    }

    @Test
    fun testPostAndCommentsRetrieving(){

        //Case OK
        testObserverPostAndComments = postDataRepository.getPostAndComments(POST_ID_FROM_NETWORK).test()

        Mockito.verify(postDBDataSource, Mockito.times(NUM_COMMENTS_FROM_NETWORK)).delete(ArgumentMatchers.any(CommentEntity::class.java))
        Mockito.verify(postDBDataSource, Mockito.times(NUM_COMMENTS_FROM_NETWORK)).insert(ArgumentMatchers.any(CommentEntity::class.java))
        Mockito.verify(postDBDataSource, Mockito.times(1)).getPostAndComments(POST_ID_FROM_NETWORK)
        Mockito.verify(postNetworkDataSource, Mockito.times(1)).getPostAndComments(POST_ID_FROM_NETWORK)

        testObserverPostAndComments.assertNoErrors()
        testObserverPostAndComments.assertComplete()
        testObserverPostAndComments.assertValues(postAndCommentsFromDatabase, postAndCommentsFromNetwork)

        // KO
        testObserverPostAndComments = postDataRepository.getPostAndComments(POST_ID_FROM_DATABASE).test()
        Mockito.verify(postDBDataSource, Mockito.times(1)).getPostAndComments(POST_ID_FROM_DATABASE)
        testObserverPostAndComments.assertError(throwable)
        testObserverPostAndComments.assertNotComplete()
        testObserverPostAndComments.assertValue(postAndCommentsFromDatabase)

    }

    private fun testCompletable(): Completable = Completable.complete()

    private fun testDataDBPostSingle(): Single<List<Post>> = Single.just(postListFromDatabase)

    private fun testErrorNetworkSingle(): Single<MutableList<Post>>? = Single.error(throwable)

    private fun testDataNetworkPostSingle(): Single<List<Post>> = Single.just(postListFromNetwork)
}