package joalissongomes.dev.instagram.register.view

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import joalissongomes.dev.instagram.R
import joalissongomes.dev.instagram.common.base.DependencyInjector
import joalissongomes.dev.instagram.common.util.TxtWatcher
import joalissongomes.dev.instagram.databinding.FragmentRegisterEmailBinding
import joalissongomes.dev.instagram.register.RegisterEmail
import joalissongomes.dev.instagram.register.presenter.RegisterEmailPresenter

class RegisterEmailFragment : Fragment(R.layout.fragment_register_email), RegisterEmail.View {

    private var binding: FragmentRegisterEmailBinding? = null
    override lateinit var presenter: RegisterEmail.Presenter
    private var fragmentAttachListener: FragmentAttachListener? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterEmailBinding.bind(view)

        presenter = RegisterEmailPresenter(this, DependencyInjector.registerRepository())

        binding?.let {
            with(it) {

                when (resources.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        registerImgLogo.imageTintList = ColorStateList.valueOf(Color.WHITE)
//                        registerImgLogo.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
                    }
                }

                // go back to login
                registerTxtLogin.setOnClickListener {
                    activity?.finish()
                }

                registerBtnNext.setOnClickListener {
                    presenter.create(registerEditEmail.text.toString())
                }

                registerEditEmail.addTextChangedListener(watcher)
                registerEditEmail.addTextChangedListener(TxtWatcher {
                    displayEmailFailure(null)
                })
            }
        }
    }

    private val watcher = TxtWatcher {
        binding?.registerBtnNext?.isEnabled =
            binding?.registerEditEmail?.text?.toString()?.isNotEmpty() == true
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        // set context of the attach in interface reference
        if (context is FragmentAttachListener) {
            fragmentAttachListener = context
        }
    }

    override fun goToNameAndPasswordScreen(email: String) {
        fragmentAttachListener?.goToNameAndPasswordScreen(email)
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding?.registerEditEmailInput?.error = emailError?.let { getString(it) }
    }

    override fun onEmailFailure(message: String) {
        binding?.registerEditEmailInput?.error = message
    }

    override fun showProgress(enabled: Boolean) {
        binding?.registerBtnNext?.showProgress(enabled)
    }

    override fun onDestroy() {
        binding = null
        fragmentAttachListener = null
        presenter.onDestroy()
        super.onDestroy()
    }
}