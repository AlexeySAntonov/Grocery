package com.aleksejantonov.core.ui.base

import android.content.Context
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog

object Dialogs {

  fun alertDialog(
    context: Context,
    @StringRes title: Int,
    @StringRes message: Int,
    @StringRes positiveText: Int = android.R.string.ok,
    @StringRes negativeText: Int = android.R.string.cancel,
    positiveAction: () -> Unit = { },
    negativeEnabled: Boolean = false
  ) = AlertDialog
    .Builder(context, R.style.AppDialogTheme)
    .apply {
      setTitle(title)
      setMessage(message)
      setPositiveButton(positiveText) { dialog, _ ->
        positiveAction()
        dialog.dismiss()
      }
      if (negativeEnabled) setNegativeButton(negativeText) { dialog, _ -> dialog.dismiss() }
    }
    .create()

  fun customInfoDialog(
    customTitle: String? = null,
    customMessage: String? = null,
    customTitleRes: Int? = null,
    customMessageRes: Int? = null,
    positiveAction: () -> Unit = {}
  ): WarningDialog = WarningDialog.newInstance(
    contentRes = R.layout.dialog_custom_info,
    positiveAction = positiveAction,
    customTitle = customTitle,
    customMessage = customMessage,
    customTitleRes = customTitleRes,
    customMessageRes = customMessageRes,
    customButtons = true,
    customBackground = true
  )
}