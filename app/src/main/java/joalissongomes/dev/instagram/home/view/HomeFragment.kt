package joalissongomes.dev.instagram.home.view

import android.content.Context
import android.view.*
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.recyclerview.widget.LinearLayoutManager
import joalissongomes.dev.instagram.R
import joalissongomes.dev.instagram.common.base.BaseFragment
import joalissongomes.dev.instagram.common.base.DependencyInjector
import joalissongomes.dev.instagram.common.model.Post
import joalissongomes.dev.instagram.databinding.FragmentHomeBinding
import joalissongomes.dev.instagram.home.Home
import joalissongomes.dev.instagram.home.presenter.HomePresenter
import joalissongomes.dev.instagram.main.LogoutListener

class HomeFragment : BaseFragment<FragmentHomeBinding, Home.Presenter>(
    R.layout.fragment_home,
    FragmentHomeBinding::bind
), Home.View {

    override lateinit var presenter: Home.Presenter
    private val adapter = FeedAdapter()

    private var logoutListener: LogoutListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is LogoutListener) {
            logoutListener = context
        }
    }

    override fun setupViews() {
        binding?.homeRv?.layoutManager = LinearLayoutManager(requireContext())
        binding?.homeRv?.adapter = adapter

        presenter.fetchFeed()
    }

    override fun setupPresenter() {
        val repository = DependencyInjector.homeRepository()
        presenter = HomePresenter(this, repository)
    }

    override fun getMenu() = R.menu.menu_profile

    override fun showProgress(enabled: Boolean) {
        binding?.homeProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun displayEmptyPost() {
        binding?.homeTxtEmptyPosts?.visibility = View.VISIBLE
        binding?.homeRv?.visibility = View.GONE
    }

    override fun displayFullPosts(posts: List<Post>) {
        binding?.homeTxtEmptyPosts?.visibility = View.GONE
        binding?.homeRv?.visibility = View.VISIBLE
        adapter.items = posts
        adapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_logout -> {
                logoutListener?.logout()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }
}