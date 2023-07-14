package joalissongomes.dev.instagram.register.view

import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.ImageDecoder
import android.graphics.PorterDuff
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import joalissongomes.dev.instagram.R
import joalissongomes.dev.instagram.common.base.DependencyInjector
import joalissongomes.dev.instagram.common.view.CropperImageFragment
import joalissongomes.dev.instagram.common.view.CustomDialog
import joalissongomes.dev.instagram.databinding.FragmentRegisterPhotoBinding
import joalissongomes.dev.instagram.register.RegisterPhoto
import joalissongomes.dev.instagram.register.presenter.RegisterPhotoPresenter

class RegisterPhotoFragment : Fragment(R.layout.fragment_register_photo), RegisterPhoto.View {

    override lateinit var presenter: RegisterPhoto.Presenter
    private var binding: FragmentRegisterPhotoBinding? = null
    private var fragmentAttachListener: FragmentAttachListener? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val repository = DependencyInjector.registerRepository()
        presenter = RegisterPhotoPresenter(this, repository)

        setFragmentResultListener("cropkey") { request, bundle ->
            val uri = bundle.getParcelable<Uri>(CropperImageFragment.KEY_URI)
                ?: throw IllegalStateException("URI not found")

            onCropImageResult(uri)
        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterPhotoBinding.bind(view)


        binding?.let { it ->
            with(it) {

                when (resources.configuration?.uiMode?.and(Configuration.UI_MODE_NIGHT_MASK)) {
                    Configuration.UI_MODE_NIGHT_YES -> {
                        registerImgProfile.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN)
                    }
                }

                registerBtnJump.setOnClickListener {
                    fragmentAttachListener?.goToMainScreen()
                }

                registerBtnNext.isEnabled = true
                registerBtnNext.setOnClickListener {
                    openDialog()
                }
            }
        }
    }

    private fun openDialog() {
        CustomDialog(requireContext()).apply {
            addButton(R.string.photo, R.string.gallery) { view ->
                when (view.id) {
                    R.string.photo -> fragmentAttachListener?.goToCameraScreen()
                    R.string.gallery -> fragmentAttachListener?.goToGalleryScreen()
                }
            }

            show()
        }
    }

    private fun onCropImageResult(uri: Uri?) {
        if (uri != null) {
            val bitmap = if (Build.VERSION.SDK_INT >= 28) {
                val source = ImageDecoder.createSource(requireContext().contentResolver, uri)
                ImageDecoder.decodeBitmap(source)
            } else {
                MediaStore.Images.Media.getBitmap(requireContext().contentResolver, uri)
            }

            // binding?.registerImgProfile?.setImageURI(uri)
            binding?.registerImgProfile?.setImageBitmap(bitmap)
            binding?.registerImgProfile?.clearColorFilter()
            presenter.updateUser(uri)
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is FragmentAttachListener) {
            fragmentAttachListener = context
        }
    }

    override fun showProgress(enabled: Boolean) {
        binding?.registerBtnNext?.showProgress(enabled)
    }

    override fun onUpdateFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun onUpdateSuccess() {
        fragmentAttachListener?.goToMainScreen()
    }

    override fun onDestroy() {
        binding = null
        presenter.onDestroy()
        fragmentAttachListener = null
        super.onDestroy()
    }
}