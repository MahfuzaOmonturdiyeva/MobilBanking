package uz.gita.mobilbanking.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import androidx.core.content.ContextCompat
import timber.log.Timber
import uz.gita.mobilbanking.R

class CustomDialog private constructor(context: Context) : Dialog(context) {
    private lateinit var dialog: Dialog
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var btnCancel: Button
    private lateinit var btnOk: Button

    class Builder(context: Context) {
        private var dialog: CustomDialog

        init {
            dialog = CustomDialog(context)
        }

        fun setTitle(title: String): Builder {
            dialog.setTitle(title)
            return this
        }

        fun setTitle(title: String, color: Int): Builder {
            dialog.setTitle(title, color)
            return this
        }

        fun setDescription(title: String): Builder {
            dialog.setDescription(title)
            return this
        }

        fun setDescription(title: String, color: Int): Builder {
            dialog.setDescription(title, color)
            return this
        }

        fun setCancelBtn(color: Int): Builder {
            dialog.setCancelBtn(color)
            return this
        }

        fun setCancelBtn(textCancelBtn: String, color: Int): Builder {
            dialog.setCancelBtn(textCancelBtn, color)
            return this
        }

        fun setOkBtn(color: Int, btnOkListener: (Unit) -> Unit): Builder {
            dialog.setOkBtn(color, btnOkListener)
            return this
        }

        fun setOkBtn(textOkBtn: String, color: Int, btnOkListener: (Unit) -> Unit): Builder {
            dialog.setOkBtn(textOkBtn, color, btnOkListener)
            return this
        }

        fun build(): CustomDialog = dialog
    }


    init {
        this.requestWindowFeature(Window.FEATURE_NO_TITLE)
        this.setContentView(R.layout.custom_dialog)

        val window = this.window?.apply {
            this.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT
            )
            this.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            this.attributes.gravity = Gravity.BOTTOM
        }
        dialog = this
        title = dialog.findViewById(R.id.tv_title)
        description = dialog.findViewById(R.id.tv_description)
        btnCancel = dialog.findViewById(R.id.btn_cancel)
        btnOk = dialog.findViewById(R.id.btn_ok)
        title.visibility = View.GONE
        description.visibility = View.GONE
        btnCancel.visibility = View.GONE
        btnOk.visibility = View.GONE
    }

    private fun setTitle(title: String) {
        this.title.text = title
        this.title.visibility = View.VISIBLE
        dialog.findViewById<View>(R.id.view).visibility = View.VISIBLE
    }

    private fun setTitle(title: String, colorText: Int) {
        this.title.setTextColor(ContextCompat.getColor(context, colorText))
        setTitle(title)
    }

    private fun setDescription(description: String) {
        this.description.text = description
        this.description.visibility = View.VISIBLE
    }

    private fun setDescription(description: String, colorText: Int) {
        this.description.setTextColor(ContextCompat.getColor(context, colorText))
        setDescription(description)
    }

    private fun setCancelBtn(textCancel: String, color: Int) {
        btnCancel.text = textCancel
        setCancelBtn(color)
    }


    private fun setCancelBtn(color: Int) {
        btnCancel.setTextColor(ContextCompat.getColor(context, color))
        btnCancel.setOnClickListener {
            this.dismiss()
        }
        btnCancel.visibility = View.VISIBLE
    }

    private fun setOkBtn(textOk: String, color: Int, btnOkListener: (Unit) -> Unit) {
        btnOk.text = textOk
        setOkBtn(color, btnOkListener)
    }

    private fun setOkBtn(color: Int, btnOkListener: (Unit) -> Unit) {
        btnOk.setTextColor(ContextCompat.getColor(context, color))
        btnOk.visibility = View.VISIBLE
        btnOk.setOnClickListener {
            btnOkListener(Unit)
            dismiss()
        }
    }
}