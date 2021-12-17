package uz.gita.mobilbanking.ui

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import uz.gita.mobilbanking.R

class CustomDialog(context: Context) : Dialog(context) {
    private lateinit var dialog: Dialog
    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var btnCancel: Button
    private lateinit var btnOk: Button

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

    fun setTitle(title: String) {
        this.title.text = title
        this.title.visibility = View.VISIBLE
        dialog.findViewById<View>(R.id.view).visibility = View.VISIBLE
    }

    fun setDescription(description: String) {
        this.description.text = description
        this.description.visibility = View.VISIBLE
    }

    fun setCancelBtnText(textCancel: String, color: Int) {
        btnCancel.text = textCancel
        btnCancel.setTextColor(color)
        btnCancel.visibility = View.VISIBLE
        btnCancel.setOnClickListener {
            this.dismiss()
        }
    }

    fun setCancelBtn(color: Int) {
        btnCancel.setTextColor(color)
        btnCancel.setOnClickListener {
            this.dismiss()
        }
        btnCancel.visibility = View.VISIBLE
    }

    fun setOkBtnText(textOk: String, color: Int, btnOkListener: (Unit) -> Unit) {
        btnOk.text = textOk
        btnOk.setTextColor(color)
        btnOk.setOnClickListener {
            btnOkListener(Unit)
        }
        btnOk.visibility = View.VISIBLE
    }

    fun setOkBtn(color: Int, btnOkListener: (Unit) -> Unit) {
        btnOk.setTextColor(color)
        btnOk.visibility = View.VISIBLE
        btnOk.setOnClickListener {
            btnOkListener(Unit)
        }
    }
}