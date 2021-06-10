package com.aleksejantonov.core.ui.base

import android.app.Dialog
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.fragment.app.DialogFragment
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.TextView

class WarningDialog : DialogFragment() {
  // Base
  private var positiveAction: (() -> Unit)? = null
  private var negativeAction: (() -> Unit)? = null
  private var contentRes: Int? = null
  private var contentView: View? = null
  private var positiveButtonText: Int? = null
  private var negativeButtonText: Int? = null

  // Custom
  private var customTitle: String? = null
  private var customMessage: String? = null
  private var customTitleRes: Int? = null
  private var customMessageRes: Int? = null
  private var customButtons: Boolean = false
  private var customBackground: Boolean = false

  override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
    contentRes?.let { contentView = LayoutInflater.from(context).inflate(it, null, false) }

    /**
     * If you want use custom info dialog (dialog_custom_info.xml) define customTitle or customMessage
     * and don't pass @param positiveButtonText & @param negativeButtonText.
     * negativeAction/positiveAction by default: dismissAllowingStateLoss()
     */
    if (customTitle != null || customMessage != null || customTitleRes != null || customMessageRes != null) {
      val title = customTitle ?: customTitleRes?.let { context?.getText(it) } ?: ""
      val message = customMessage ?: customMessageRes?.let { context?.getText(it) } ?: ""
      contentView?.findViewById<TextView>(R.id.title)?.text = title
      contentView?.findViewById<TextView>(R.id.message)?.text = message
    }

    /**
     * If you only want use your own control buttons instead if AlertDialog buttons
     * set following ids in your dialog_*.xml: @+id/positiveActionButton & @+id/negativeActionButton
     * and don't pass @param positiveButtonText & @param negativeButtonText.
     * negativeAction by default: dismissAllowingStateLoss()
     */
    if (customButtons) {
      contentView?.findViewById<Button>(R.id.positiveActionButton)?.setOnClickListener {
        positiveAction?.invoke()
        dismissAllowingStateLoss()
      }
      // Unused for now
//            contentView?.findViewById<Button>(R.id.negativeActionButton)?.setOnClickListener {
//                negativeAction?.invoke() ?: dismissAllowingStateLoss()
//            }
    }

    return AlertDialog.Builder(ContextThemeWrapper(context, android.R.style.Theme_Dialog))
      .setView(contentView)
      .apply { positiveButtonText?.let { setPositiveButton(it) { _, _ -> positiveAction?.invoke() } } }
      .apply { negativeButtonText?.let { setNegativeButton(it) { _, _ -> dismissAllowingStateLoss() } } }
      .create()
      .apply { if (customBackground) window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) }
  }

  override fun onPause() {
    dismissAllowingStateLoss()
    super.onPause()
  }

  companion object {
    fun newInstance(
      @LayoutRes contentRes: Int? = null,
      @StringRes positiveText: Int? = null,
      @StringRes negativeText: Int? = null,
      cancelable: Boolean = true,
      positiveAction: (() -> Unit)? = null,
      negativeAction: (() -> Unit)? = null,
      customTitle: String? = null,
      customMessage: String? = null,
      customTitleRes: Int? = null,
      customMessageRes: Int? = null,
      customButtons: Boolean = false,
      customBackground: Boolean = false
    ) = WarningDialog().apply {
      // Base
      this.contentRes = contentRes
      this.positiveButtonText = positiveText
      this.negativeButtonText = negativeText
      this.positiveAction = positiveAction
      this.negativeAction = negativeAction

      // Custom
      this.customTitle = customTitle
      this.customMessage = customMessage
      this.customTitleRes = customTitleRes
      this.customMessageRes = customMessageRes
      this.customButtons = customButtons
      this.customBackground = customBackground
      isCancelable = cancelable
    }
  }
}