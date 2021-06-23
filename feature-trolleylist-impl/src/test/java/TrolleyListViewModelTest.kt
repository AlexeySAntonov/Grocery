import com.aleksejantonov.core.navigation.GlobalRouter
import com.aleksejantonov.core.network.util.NetworkState
import com.aleksejantonov.core.network.util.NetworkStateListener
import com.aleksejantonov.core.test.MainCoroutineScopeRule
import com.aleksejantonov.feature.trolleylist.impl.business.TrolleyListInteractor
import com.aleksejantonov.feature.trolleylist.impl.ui.create.TrolleyCreateFragment
import com.aleksejantonov.feature.trolleylist.impl.ui.list.TrolleyListViewModel
import io.mockk.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*

class TrolleyListViewModelTest {

  private lateinit var trolleyListViewModel: TrolleyListViewModel

  @get:Rule
  val coroutineScope = MainCoroutineScopeRule()

  private val interactorMock: TrolleyListInteractor = mockk()
  private val routerMock: GlobalRouter = mockk()
  private val networkStateListenerMock: NetworkStateListener = mockk()

  @Before
  fun setup() {
    coEvery { networkStateListenerMock.networkConnectionFlow() } returns flow<NetworkState> { emit(NetworkState.AVAILABLE) }

    trolleyListViewModel = TrolleyListViewModel(
      componentKey = UUID.randomUUID().toString(),
      dispatcherIO = coroutineScope.dispatcher,
      interactor = interactorMock,
      router = routerMock,
      networkStateListener = networkStateListenerMock,
    )
  }

  @Test
  fun `viewModel init block`() {
    coVerifySequence {
      interactorMock.data()
      networkStateListenerMock.networkConnectionFlow()
      interactorMock.syncRemoteData()
    }
  }

  @Test
  fun `sync data manually`() = runBlocking {
    trolleyListViewModel.syncDataManually()
    coVerify { interactorMock.syncRemoteData() }
  }

  @Test
  fun `navigate to trolley`() {
    every { routerMock.openTrolleyDetails(any()) } just runs
    trolleyListViewModel.navigateToTrolley(1L)
    verify { routerMock.openTrolleyDetails(any()) }
  }

  @Test
  fun `navigate to trolley creation`() {
    // Todo: Mock companion doesn't work
//    mockkStatic(TrolleyCreateFragment.Companion::class)
//    every { TrolleyCreateFragment.Companion.create(any()) } returns TrolleyCreateFragment()
//    every { routerMock.openNext(any()) } just runs
//    trolleyListViewModel.navigateToTrolleyCreation()
//    verify { routerMock.openNext(any<TrolleyCreateFragment>()) }
  }

  @Test
  fun `sync trolley`() {
    coEvery { interactorMock.syncTrolley(any()) } just runs
    trolleyListViewModel.syncTrolley(1L)
    coVerify { interactorMock.syncTrolley(any()) }
  }

  @Test
  fun `delete trolley`() {
    coEvery { interactorMock.deleteTrolley(any()) } just runs
    trolleyListViewModel.onRemoveTrolleyClick(1L)
    coVerify { interactorMock.deleteTrolley(any()) }
  }

  @Test
  fun `delete all trolleys`() {
    coEvery { interactorMock.deleteAllTrolleys() } just runs
    trolleyListViewModel.onRemoveAllTrolleysClick()
    coVerify { interactorMock.deleteAllTrolleys() }
  }

}