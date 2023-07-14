package joalissongomes.dev.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import joalissongomes.dev.instagram.R
import joalissongomes.dev.instagram.databinding.FragmentRegisterWelcomeBinding

class RegisterWelcomeFragment: Fragment(R.layout.fragment_register_welcome) {

    private var binding: FragmentRegisterWelcomeBinding ? = null
    private var fragmentAttachListener: FragmentAttachListener ? = null

    companion object {
        const val KEY_NAME = "key_name"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterWelcomeBinding.bind(view)

        val name = arguments?.getString(KEY_NAME) ?: throw IllegalStateException("name not found")

        binding?.let {
            with(it) {
                registerTxtWelcome.text = getString(R.string.welcome_to_instagram, name)

                registerBtnNext.setOnClickListener {
                    fragmentAttachListener?.goToPhotoScreen()
                }
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if(context is FragmentAttachListener) {
            fragmentAttachListener = context
        }
    }

    override fun onDestroy() {
        binding = null
        fragmentAttachListener = null
        super.onDestroy()
    }
}