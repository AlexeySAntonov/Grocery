import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.test.MainCoroutineScopeRule
import com.aleksejantonov.core.ui.base.adapter.delegate.RemoveAllItem
import com.aleksejantonov.feature.trolleylist.impl.business.TrolleyListInteractor
import com.aleksejantonov.feature.trolleylist.impl.business.TrolleyListInteractorImpl
import com.aleksejantonov.feature.trolleylist.impl.data.TrolleyListRepository
import io.mockk.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TrolleyListInteractorTest {

  private lateinit var trolleyListInteractor: TrolleyListInteractor

  @get:Rule
  val coroutineScope = MainCoroutineScopeRule()

  private val repositoryMock: TrolleyListRepository = mockk()

  @Before
  fun setup() {
    trolleyListInteractor = TrolleyListInteractorImpl(repository = repositoryMock)
  }

  @Test
  fun `empty items list`() = runBlocking {
    coEvery { repositoryMock.data() } returns flow { emit(TrolleysFactory.emptyData()) }
    val items = trolleyListInteractor.data().first()
    assert(items.isEmpty())
  }

  @Test
  fun `non empty items list`() = runBlocking {
    coEvery { repositoryMock.data() } returns flow { emit(TrolleysFactory.nonEmptyData()) }
    val items = trolleyListInteractor.data().first()
    assert(items.size == TrolleysFactory.nonEmptyData().size + 1) // + Remove all item
    assert(items.last() is RemoveAllItem)
  }

  @Test
  fun `create trolley with valid name`() = runBlocking {
    val validModel = TrolleysFactory.trolleyModelWithValidName()
    coEvery { repositoryMock.createTrolley(any(), any()) } returns validModel.id
    val id = trolleyListInteractor.createTrolley(validModel.name, validModel.name)
    assert(id == validModel.id)
    coVerify { repositoryMock.createTrolley(any(), any()) }
  }

  @Test
  fun `create trolley with invalid name`() = runBlocking {
    var assertionThrown = false
    val invalidModel = TrolleysFactory.trolleyModelWithInvalidName()
    try {
      trolleyListInteractor.createTrolley(invalidModel.name, invalidModel.description)
    } catch (e: IllegalArgumentException) {
      assertionThrown = true
    }
    assert(assertionThrown)
  }

  // Proxy
  @Test
  fun `delete trolley`() = runBlocking {
    coEvery { repositoryMock.deleteTrolley(any()) } just runs
    trolleyListInteractor.deleteTrolley(1L)
    coVerify { repositoryMock.deleteTrolley(any()) }
  }

  // Proxy
  @Test
  fun `delete all trolleys`() = runBlocking {
    coEvery { repositoryMock.deleteAllTrolleys() } just runs
    trolleyListInteractor.deleteAllTrolleys()
    coVerify { repositoryMock.deleteAllTrolleys() }
  }

  // Proxy
  @Test
  fun `sync trolley`() = runBlocking {
    coEvery { repositoryMock.syncTrolley(any()) } just runs
    trolleyListInteractor.syncTrolley(1L)
    coVerify { repositoryMock.syncTrolley(any()) }
  }

  @Test
  fun `sync remote data`() = runBlocking {
    val flow = flow<SyncStatus> {
      emit(SyncStatus.UPDATING)
      delay(10L)
      emit(SyncStatus.FAILED)
      delay(10L)
      emit(SyncStatus.UPDATING)
      delay(10L)
      emit(SyncStatus.DONE)
    }
    coEvery { repositoryMock.syncRemoteData() } returns flow
    val data = trolleyListInteractor.syncRemoteData()
    val firstStatus = data.first()
    assert(firstStatus == SyncStatus.UPDATING)
    coroutineScope.advanceTimeBy(10L)
    val secondStatus = data.drop(1).first()
    assert(secondStatus == SyncStatus.FAILED)
    coroutineScope.advanceTimeBy(10L)
    val thirdStatus = data.drop(2).first()
    assert(thirdStatus == SyncStatus.UPDATING)
    coroutineScope.advanceTimeBy(10L)
    val fourthStatus = data.drop(3).first()
    assert(fourthStatus == SyncStatus.DONE)
  }
}