import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.model.TrolleyModel
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneId

object TrolleysFactory {

  fun emptyData(): List<TrolleyModel> = listOf()
  fun nonEmptyData(): List<TrolleyModel> = mutableListOf<TrolleyModel>().apply {
    add(
      TrolleyModel(
        id = 1,
        name = "Test1",
        description = "",
        created = LocalDateTime.now(ZoneId.of("UTC")),
        products = emptyList(),
        syncStatus = SyncStatus.DONE
      )
    )
    add(
      TrolleyModel(
        id = 2,
        name = "Test2",
        description = "",
        created = LocalDateTime.now(ZoneId.of("UTC")),
        products = emptyList(),
        syncStatus = SyncStatus.DONE
      )
    )
    add(
      TrolleyModel(
        id = 3,
        name = "Test3",
        description = "",
        created = LocalDateTime.now(ZoneId.of("UTC")),
        products = emptyList(),
        syncStatus = SyncStatus.DONE
      )
    )
    add(
      TrolleyModel(
        id = 4,
        name = "Test4",
        description = "",
        created = LocalDateTime.now(ZoneId.of("UTC")),
        products = emptyList(),
        syncStatus = SyncStatus.DONE
      )
    )
  }
}