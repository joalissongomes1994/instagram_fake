package joalissongomes.dev.instagram.post.view

import android.net.Uri
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.GridLayoutManager
import joalissongomes.dev.instagram.R
import joalissongomes.dev.instagram.common.base.BaseFragment
import joalissongomes.dev.instagram.common.base.DependencyInjector
import joalissongomes.dev.instagram.databinding.FragmentGalleryBinding
import joalissongomes.dev.instagram.post.Post
import joalissongomes.dev.instagram.post.presenter.PostPresenter

class GalleryFragment : BaseFragment<FragmentGalleryBinding, Post.Presenter>(
    R.layout.fragment_gallery,
    FragmentGalleryBinding::bind
), Post.View {

    override lateinit var presenter: Post.Presenter

    private val adapter = PictureAdapter() { uri ->
        binding?.galleryImgSelected?.setImageURI(uri)
        binding?.galleryNested?.smoothScrollTo(0, 0)

        presenter.selectUri(uri)
    }

    override fun setupPresenter() {
        val repository = DependencyInjector.postRepository(requireContext())
        presenter = PostPresenter(this, repository)
    }

    override fun setupViews() {
        binding?.galleryRv?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.galleryRv?.adapter = adapter

        presenter.fetchPictures()
    }

    override fun getMenu() = R.menu.menu_send

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_send -> {
                setFragmentResult("takePhotoKey", bundleOf("uri" to presenter.getSelectedUri()))
            }
        }
        return super.onOptionsItemSelected(item)
    }


    override fun showProgress(enabled: Boolean) {
        binding?.galleryProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayEmptyPicture() {
        binding?.galleryTxtEmptyPictures?.visibility = View.VISIBLE
        binding?.galleryRv?.visibility = View.GONE
    }

    override fun displayFullPictures(pictures: List<Uri>) {
        binding?.galleryTxtEmptyPictures?.visibility = View.GONE
        binding?.galleryRv?.visibility = View.VISIBLE

        adapter.items = pictures
        adapter.notifyDataSetChanged()

        binding?.galleryImgSelected?.setImageURI(pictures.first())
        binding?.galleryNested?.smoothScrollTo(0, 0)

        presenter.selectUri(pictures.first())
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }
}