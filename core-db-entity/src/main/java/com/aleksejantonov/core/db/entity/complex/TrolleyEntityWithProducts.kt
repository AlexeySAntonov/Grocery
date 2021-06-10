package com.aleksejantonov.core.db.entity.complex

import androidx.room.Embedded
import androidx.room.Relation
import com.aleksejantonov.core.db.entity.ProductEntity
import com.aleksejantonov.core.db.entity.TrolleyEntity

data class TrolleyEntityWithProducts(
  @Embedded
  var trolley: TrolleyEntity,
  @Relation(parentColumn = "id", entityColumn = "trolleyId", entity = ProductEntity::class)
  var products: List<ProductEntity>
) {
  constructor() : this(TrolleyEntity(), emptyList())
}