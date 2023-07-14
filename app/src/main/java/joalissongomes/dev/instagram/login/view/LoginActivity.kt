package joalissongomes.dev.instagram.login.view

import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import joalissongomes.dev.instagram.common.base.DependencyInjector
import joalissongomes.dev.instagram.common.util.TxtWatcher
import joalissongomes.dev.instagram.databinding.ActivityLoginBinding
import joalissongomes.dev.instagram.login.Login
import joalissongomes.dev.instagram.common.model.UserAuth
import joalissongomes.dev.instagram.login.presenter.LoginPresenter
import joalissongomes.dev.instagram.main.view.MainActivity
import joalissongomes.dev.instagram.register.view.RegisterActivity

class LoginActivity : AppCompatActivity(), Login.View {
    private lateinit var binding: ActivityLoginBinding
    override lateinit var presenter: Login.Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        presenter = LoginPresenter(this, DependencyInjector.loginRepository())

        with(binding) {

            when (resources?.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                Configuration.UI_MODE_NIGHT_YES -> {
                    loginImgLogo.imageTintList = ColorStateList.valueOf(Color.WHITE)
                }
            }

            loginEditEmail.addTextChangedListener(watcher)
            loginEditPassword.addTextChangedListener(watcher)

            loginEditEmail.addTextChangedListener(TxtWatcher {
                displayEmailFailure(null)
            })
            loginEditPassword.addTextChangedListener(TxtWatcher {
                displayPasswordFailure(null)
            })

            loginBtnEnter.setOnClickListener {
                presenter.login(
                    loginEditEmail.text.toString(),
                    loginEditPassword.text.toString()
                )
            }

            loginTxtRegister.setOnClickListener {
                goToRegisterScreen()
            }
        }
    }

    override fun onDestroy() {
        presenter.onDestroy()
        super.onDestroy()
    }

    private val watcher = TxtWatcher {
        binding.loginBtnEnter.isEnabled = binding.loginEditEmail.text.toString().isNotEmpty() &&
                binding.loginEditPassword.text.toString().isNotEmpty()
    }

    private fun goToRegisterScreen() {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    override fun showProgress(enabled: Boolean) {
        binding.loginBtnEnter.showProgress(enabled)
    }

    override fun displayEmailFailure(emailError: Int?) {
        binding.loginEditEmailInput.error = emailError?.let { getString(it) }
    }

    override fun displayPasswordFailure(passwordError: Int?) {
        binding.loginEditPasswordInput.error = passwordError?.let { getString(it) }
    }

    override fun onUserAuthenticated() {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
        startActivity(intent)
    }

    override fun onUserUnauthorized(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
    }
}