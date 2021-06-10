package com.aleksejantonov.core.model.dto


data class ProductDto(
  val id: Long = 0L,
  val name: String = "",
  val trolleyId: Long = 0L,
  val created: String? = null,
  val isChecked: Boolean = false,
)