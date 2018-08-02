package com.sqli.mvvmapp.user

import com.nhaarman.mockito_kotlin.whenever
import com.sqli.mvvmapp.base.BaseTest
import com.sqli.mvvmapp.mvvm.user.model.db.UserEntity
import com.sqli.mvvmapp.mvvm.user.model.entity.User
import com.sqli.mvvmapp.mvvm.user.model.mapper.CompanyMapper
import com.sqli.mvvmapp.mvvm.user.model.mapper.UserMapper
import com.sqli.mvvmapp.mvvm.user.model.repository.data.UserDataFactory
import com.sqli.mvvmapp.mvvm.user.model.repository.data.UserDataRepository
import com.sqli.mvvmapp.mvvm.user.model.repository.datasource.UserDBDataSource
import com.sqli.mvvmapp.mvvm.user.model.repository.datasource.UserNetworkDataSource
import com.sqli.mvvmapp.utils.FakeDataProvider
import dagger.Lazy
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.observers.TestObserver
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

class UserDataRepositoryTest: BaseTest() {

    companion object {
        const val NUM_DATABASE_USERS = 4
        const val NUM_NETWORK_USERS = 5
    }

    //Class to test
    lateinit var userDataRepository: UserDataRepository

    //Non mockable objects
    val userMapper: UserMapper = UserMapper()
    val companyMapper: CompanyMapper = CompanyMapper()
    val throwable = Throwable()

    //Mockable objects

    @Mock
    lateinit var userDataFactory: UserDataFactory

    @Mock
    lateinit var userNetworkDataSource: UserNetworkDataSource

    @Mock
    lateinit var userDBDataSource: UserDBDataSource

    //Rx Test objects
    lateinit var testObserverUsers: TestObserver<List<User>>

    //Data providers
    val listUsersDB: List<User> = FakeDataProvider.getInstance().getTestUsers(NUM_DATABASE_USERS)
    val listUsersNw: List<User> = FakeDataProvider.getInstance().getTestUsers(NUM_NETWORK_USERS)

    @Before
    fun setup() {

        MockitoAnnotations.initMocks(this)

        whenever(userNetworkDataSource.userList())
                .thenReturn(testDataUsersNWSingle(), testErrorSingle())

        whenever(userDataFactory.createNetworkDataSource()).thenReturn(userNetworkDataSource)
        whenever(userDataFactory.createDBDataSource()).thenReturn(userDBDataSource)

        whenever(userDBDataSource.userList())
                .thenReturn(testDataUsersDBSingle())

        whenever(userDBDataSource.delete(ArgumentMatchers.any(UserEntity::class.java)))
                .thenReturn(testCompletable())

        whenever(userDBDataSource.insert(ArgumentMatchers.any(UserEntity::class.java)))
                .thenReturn(testCompletable())

        userDataRepository = UserDataRepository(Lazy { userMapper }, Lazy { companyMapper }, Lazy { userDataFactory })
    }

    @Test
    fun testUserRetrieving() {

        //Test network OK
        testObserverUsers = userDataRepository.users.test()

        Mockito.verify(userDBDataSource, Mockito.times(NUM_NETWORK_USERS)).delete(ArgumentMatchers.any())
        Mockito.verify(userDBDataSource, Mockito.times(NUM_NETWORK_USERS)).insert(ArgumentMatchers.any())
        Mockito.verify(userDBDataSource, Mockito.times(1)).userList()
        Mockito.verify(userNetworkDataSource, Mockito.times(1)).userList()

        testObserverUsers.assertNoErrors()
        testObserverUsers.assertComplete()
        testObserverUsers.assertValues(listUsersDB, listUsersNw)

        //Test network KO
        testObserverUsers = userDataRepository.users.test()
        Mockito.verify(userDBDataSource, Mockito.times(2)).userList()

        testObserverUsers.assertError(throwable)
        testObserverUsers.assertNotComplete()
        testObserverUsers.assertValue(listUsersDB)

    }

    private fun testErrorSingle(): Single<MutableList<User>>? {
        return Single.error(throwable)
    }

    private fun testCompletable(): Completable = Completable.complete()

    private fun testDataUsersNWSingle(): Single<List<User>>? = Single.just(listUsersNw)

    private fun testDataUsersDBSingle(): Single<List<User>>? = Single.just(listUsersDB)

}