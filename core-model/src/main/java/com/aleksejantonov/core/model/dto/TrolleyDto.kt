package com.aleksejantonov.core.model.dto

data class TrolleyDto(
  val id: Long = 0L,
  val name: String = "",
  val description: String = "",
  val created: String? = null,
)