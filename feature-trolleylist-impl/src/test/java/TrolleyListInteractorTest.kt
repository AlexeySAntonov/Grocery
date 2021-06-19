import com.aleksejantonov.core.test.MainCoroutineScopeRule
import com.aleksejantonov.core.ui.base.adapter.delegate.RemoveAllItem
import com.aleksejantonov.feature.trolleylist.impl.business.TrolleyListInteractor
import com.aleksejantonov.feature.trolleylist.impl.business.TrolleyListInteractorImpl
import com.aleksejantonov.feature.trolleylist.impl.data.TrolleyListRepository
import io.mockk.coEvery
import io.mockk.mockk
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
}