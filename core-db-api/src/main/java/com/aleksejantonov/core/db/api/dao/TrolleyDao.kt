package com.aleksejantonov.core.db.api.dao

import androidx.room.*
import com.aleksejantonov.core.db.entity.TrolleyEntity
import com.aleksejantonov.core.db.entity.complex.TrolleyEntityWithProducts
import kotlinx.coroutines.flow.Flow

@Dao
interface TrolleyDao {

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertTrolley(trolley: TrolleyEntity): Long

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertTrolleys(trolleys: List<TrolleyEntity>)

  @Query("DELETE FROM trolleys WHERE id = :trolleyId")
  fun deleteTrolley(trolleyId: Long)

  @Query("SELECT * FROM trolleys WHERE id = :id")
  fun trolleyData(id: Long): Flow<TrolleyEntity>

  @Transaction
  @Query("SELECT * FROM trolleys WHERE id = :id")
  fun trolleyComplexEntity(id: Long): TrolleyEntityWithProducts?

  @Transaction
  @Query("SELECT * FROM trolleys WHERE id = :id")
  fun trolleyComplexData(id: Long): Flow<TrolleyEntityWithProducts>

  @Query("SELECT * FROM trolleys ORDER BY created DESC")
  fun trolleysData(): Flow<List<TrolleyEntity>>

  @Transaction
  @Query("SELECT * FROM trolleys ORDER BY created DESC")
  fun trolleysComplexData(): Flow<List<TrolleyEntityWithProducts>>

  @Query("UPDATE trolleys SET syncStatus = :status WHERE id = :trolleyId")
  fun updateStatus(trolleyId: Long, status: Int)

}