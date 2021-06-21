import com.aleksejantonov.core.db.api.store.SyncStore
import com.aleksejantonov.core.db.api.store.TrolleysStore
import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.test.MainCoroutineScopeRule
import com.aleksejantonov.feature.trolleylist.impl.data.TrolleyListRepository
import com.aleksejantonov.feature.trolleylist.impl.data.TrolleyListRepositoryImpl
import io.mockk.*
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TrolleyListRepositoryTest {

  private lateinit var trolleyListRepository: TrolleyListRepository

  @get:Rule
  val coroutineScope = MainCoroutineScopeRule()

  private val trolleysStoreMock: TrolleysStore = mockk()
  private val syncStoreMock: SyncStore = mockk()

  @Before
  fun setup() {
    trolleyListRepository = TrolleyListRepositoryImpl(
      trolleysStore = trolleysStoreMock,
      syncStore = syncStoreMock,
    )
  }

  @Test
  fun `empty data`() = runBlocking {
    coEvery { trolleysStoreMock.trolleysComplexData() } returns flow { emit(TrolleysFactory.emptyData()) }
    val models = trolleyListRepository.data().first()
    assert(models.isEmpty())
    coVerify { trolleysStoreMock.trolleysComplexData() }
  }

  @Test
  fun `non empty data`() = runBlocking {
    coEvery { trolleysStoreMock.trolleysComplexData() } returns flow { emit(TrolleysFactory.nonEmptyData()) }
    val models = trolleyListRepository.data().first()
    assert(models.isNotEmpty() && models.size == TrolleysFactory.nonEmptyData().size)
    coVerify { trolleysStoreMock.trolleysComplexData() }
  }

  @Test
  fun `create trolley with valid name`() = runBlocking {
    val validModel = TrolleysFactory.trolleyModelWithValidName()
    coEvery { trolleysStoreMock.createTrolley(any()) } returns validModel.id
    val id = trolleyListRepository.createTrolley(validModel.name, validModel.name)
    assert(id == validModel.id)
    coVerify { trolleysStoreMock.createTrolley(any()) }
  }

  @Test
  fun `create trolley with invalid name`() = runBlocking {
    var assertionThrown = false
    val invalidModel = TrolleysFactory.trolleyModelWithInvalidName()
    try {
      trolleyListRepository.createTrolley(invalidModel.name, invalidModel.description)
    } catch (e: IllegalArgumentException) {
      assertionThrown = true
    }
    assert(assertionThrown)
  }

  // Proxy
  @Test
  fun `delete trolley`() = runBlocking {
    coEvery { trolleysStoreMock.deleteTrolley(any()) } just runs
    trolleyListRepository.deleteTrolley(1L)
    coVerify { trolleysStoreMock.deleteTrolley(any()) }
  }

  // Proxy
  @Test
  fun `delete all trolleys`() = runBlocking {
    coEvery { trolleysStoreMock.deleteAllTrolleys() } just runs
    trolleyListRepository.deleteAllTrolleys()
    coVerify { trolleysStoreMock.deleteAllTrolleys() }
  }

  // Proxy
  @Test
  fun `sync trolley`() = runBlocking {
    val model = TrolleysFactory.trolleyModelWithValidName()
    coEvery { trolleysStoreMock.trolleyComplexEntity(any()) } returns model
    coEvery { syncStoreMock.putTrolley(any()) } just runs
    trolleyListRepository.syncTrolley(model.id)
    coVerifySequence {
      trolleysStoreMock.trolleyComplexEntity(any())
      syncStoreMock.putTrolley(any())
    }
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
    coEvery { syncStoreMock.fetchRemoteData() } returns flow
    val data = trolleyListRepository.syncRemoteData()
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
    coVerify { syncStoreMock.fetchRemoteData() }
  }
}