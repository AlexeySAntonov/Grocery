package com.aleksejantonov.core.db.entity

import com.aleksejantonov.core.db.entity.complex.TrolleyEntityWithProducts
import com.aleksejantonov.core.model.ProductModel
import com.aleksejantonov.core.model.SyncStatus
import com.aleksejantonov.core.model.TrolleyModel
import com.aleksejantonov.core.model.dto.ProductDto
import com.aleksejantonov.core.model.dto.TrolleyDto
import org.threeten.bp.LocalDateTime

fun TrolleyEntityWithProducts.model() = TrolleyModel(
  id = trolley.id,
  name = trolley.name,
  description = trolley.description,
  created = trolley.created,
  products = products.map { it.model() },
  syncStatus = trolley.syncStatus,
)

fun TrolleyEntity.model() = TrolleyModel(
  id = id,
  name = name,
  description = description,
  created = created,
  products = emptyList(),
  syncStatus = syncStatus,
)

fun TrolleyModel.entity() = TrolleyEntity(
  id = id,
  name = name,
  description = description,
  created = created,
  syncStatus = syncStatus,
)

fun TrolleyModel.dto() = TrolleyDto(
  id = id,
  name = name,
  description = description,
  created = created?.toString()
)

fun TrolleyDto.entity() = TrolleyEntity(
  id = id,
  name = name,
  description = description,
  created = created?.let { LocalDateTime.parse(it) },
  syncStatus = SyncStatus.DONE,
)

fun ProductEntity.model() = ProductModel(
  id = id,
  name = name,
  created = created,
  trolleyId = trolleyId,
  isChecked = isChecked,
  syncStatus = syncStatus,
)

fun ProductModel.entity() = ProductEntity(
  id = id,
  name = name,
  created = created,
  trolleyId = trolleyId,
  isChecked = isChecked,
  syncStatus = syncStatus,
)

fun ProductModel.dto() = ProductDto(
  id = id,
  name = name,
  created = created?.toString(),
  trolleyId = trolleyId,
  isChecked = isChecked,
)

fun ProductDto.entity() = ProductEntity(
  id = id,
  name = name,
  created = created?.let { LocalDateTime.parse(it) },
  trolleyId = trolleyId,
  isChecked = isChecked,
  syncStatus = SyncStatus.DONE
)