package com.aleksejantonov.core.db.api.store

import com.aleksejantonov.core.model.TrolleyModel
import kotlinx.coroutines.flow.Flow

interface TrolleysStore {
  fun createTrolley(trolley: TrolleyModel): Long
  fun trolleyData(id: Long): Flow<TrolleyModel>
  fun trolleyComplexEntity(id: Long): TrolleyModel?
  fun trolleyComplexData(id: Long): Flow<TrolleyModel>
  fun trolleysData(): Flow<List<TrolleyModel>>
  fun trolleysComplexData(): Flow<List<TrolleyModel>>
  fun deleteTrolley(trolleyId: Long)
  fun deleteAllTrolleys()
}