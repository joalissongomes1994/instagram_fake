package joalissongomes.dev.instagram.common.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.StringRes
import joalissongomes.dev.instagram.R
import joalissongomes.dev.instagram.databinding.DialogCustomBinding

class CustomDialog(context: Context) : Dialog(context) {

    private lateinit var txtButtons: Array<TextView>

    private lateinit var binding: DialogCustomBinding

    @StringRes private var titleId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DialogCustomBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun addButton(@StringRes vararg texts: Int, listener: View.OnClickListener) {
        // set context
        txtButtons = Array(texts.size) {
            TextView(context)
        }

        // set text in button index
        // actions
        texts.forEachIndexed { index, txtId ->
            txtButtons[index].id = txtId
            txtButtons[index].setText(txtId)
            txtButtons[index].setOnClickListener { view ->
                listener.onClick(view)
                dismiss() // close dialog
            }
        }
    }

    override fun setTitle(titleId: Int) {
        this.titleId = titleId
    }

    override fun show() {
        requestWindowFeature(Window.FEATURE_NO_TITLE) // hide title
        super.show()

        titleId?.let {
            binding.dialogTitle.setText(it)
        }

        // add child view
        for (textView in txtButtons) {
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            layoutParams.setMargins( 30, 50, 30, 50)
            binding.dialogContainer.addView(textView, layoutParams)
        }
    }
}