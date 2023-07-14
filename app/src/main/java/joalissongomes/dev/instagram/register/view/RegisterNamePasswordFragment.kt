package joalissongomes.dev.instagram.register.view

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import joalissongomes.dev.instagram.R
import joalissongomes.dev.instagram.common.base.DependencyInjector
import joalissongomes.dev.instagram.common.util.TxtWatcher
import joalissongomes.dev.instagram.databinding.FragmentRegisterNamePasswordBinding
import joalissongomes.dev.instagram.register.RegisterNameAndPassword
import joalissongomes.dev.instagram.register.presenter.RegisterNameAndPasswordPresenter

class RegisterNamePasswordFragment : Fragment(R.layout.fragment_register_name_password),
    RegisterNameAndPassword.View {

    override lateinit var presenter: RegisterNameAndPassword.Presenter
    private var binding: FragmentRegisterNamePasswordBinding? = null
    private var fragmentAttachListener: FragmentAttachListener? = null

    companion object {
        const val KEY_EMAIL = "key_email"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterNamePasswordBinding.bind(view)

        val email =
            arguments?.getString(KEY_EMAIL) ?: throw IllegalStateException("email not found")

        val repository = DependencyInjector.registerRepository()
        presenter = RegisterNameAndPasswordPresenter(this, repository)

        binding?.let {
            with(it) {
                registerTxtLogin.setOnClickListener {
                    activity?.finish()
                }

                registerEditName.addTextChangedListener(watcher)
                registerEditPassword.addTextChangedListener(watcher)
                registerEditConfirm.addTextChangedListener(watcher)

                registerEditName.addTextChangedListener(TxtWatcher {
                    displayNameFailure(null)
                })
                registerEditPassword.addTextChangedListener(TxtWatcher {
                    displayPasswordFailure(null)
                })
                registerEditConfirm.addTextChangedListener(TxtWatcher {
                    displayConfirmPasswordFailure(null)
                })

                registerNamePasswordBtnNext.setOnClickListener {
                    presenter.create(
                        email,
                        registerEditName.text.toString(),
                        registerEditPassword.text.toString(),
                        registerEditConfirm.text.toString(),
                    )
                }
            }
        }
    }

    private val watcher = TxtWatcher {
        binding?.let {
            with(it) {
                registerNamePasswordBtnNext.isEnabled =
                    registerEditName.text.toString().isNotEmpty()
                            && registerEditPassword.text.toString().isNotEmpty()
                            && registerEditConfirm.text.toString().isNotEmpty()
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentAttachListener) {
            fragmentAttachListener = context
        }
    }

    override fun showProgress(enabled: Boolean) {
        binding?.registerNamePasswordBtnNext?.showProgress(enabled)
    }

    override fun displayNameFailure(nameError: Int?) {
        binding?.registerEditNameInput?.error = nameError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding?.registerEditPasswordInput?.error = passwordError?.let { getString(it) }
    }

    override fun displayConfirmPasswordFailure(confirmPasswordError: Int?) {
        binding?.registerEditConfirmInput?.error = confirmPasswordError?.let { getString(it) }
    }

    override fun onCreateFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onCreateSuccess(name: String) {
        fragmentAttachListener?.goToWelcomeScreen(name)
    }

    override fun onDestroy() {
        binding = null
        fragmentAttachListener = null
        presenter.onDestroy()

        super.onDestroy()
    }
}