import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.test.MainCoroutineScopeRule
import com.aleksejantonov.core.ui.base.adapter.delegate.RemoveAllItem
import com.aleksejantonov.feature.trolleylist.impl.business.TrolleyListInteractor
import com.aleksejantonov.feature.trolleylist.impl.business.TrolleyListInteractorImpl
import com.aleksejantonov.feature.trolleylist.impl.data.TrolleyListRepository
import io.mockk.coEvery
import io.mockk.mockk
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
  fun `create trolley`() = runBlocking {
    coEvery { repositoryMock.createTrolley("Name", "Description") } returns 1L
    val id = trolleyListInteractor.createTrolley("Name", "Description")
    assert(id == 1L)
  }

  // Proxy
  @Test
  fun `delete trolley`() = runBlocking {
    val id = 1L
    coEvery { repositoryMock.deleteTrolley(id) } returns Unit
    val result = trolleyListInteractor.deleteTrolley(id)
    assert(result == Unit)
  }

  // Proxy
  @Test
  fun `delete all trolleys`() = runBlocking {
    coEvery { repositoryMock.deleteAllTrolleys() } returns Unit
    val result = trolleyListInteractor.deleteAllTrolleys()
    assert(result == Unit)
  }

  // Proxy
  @Test
  fun `sync trolley`() = runBlocking {
    val id = 1L
    coEvery { repositoryMock.syncTrolley(id) } returns Unit
    val result = trolleyListInteractor.syncTrolley(id)
    assert(result == Unit)
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