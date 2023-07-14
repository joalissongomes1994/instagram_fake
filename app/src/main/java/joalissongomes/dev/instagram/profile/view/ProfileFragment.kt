package joalissongomes.dev.instagram.profile.view

import android.content.Context
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.google.android.material.bottomnavigation.BottomNavigationView
import joalissongomes.dev.instagram.R
import joalissongomes.dev.instagram.common.base.BaseFragment
import joalissongomes.dev.instagram.common.base.DependencyInjector
import joalissongomes.dev.instagram.common.model.Post
import joalissongomes.dev.instagram.common.model.User
import joalissongomes.dev.instagram.common.model.UserAuth
import joalissongomes.dev.instagram.databinding.FragmentProfileBinding
import joalissongomes.dev.instagram.main.LogoutListener
import joalissongomes.dev.instagram.profile.Profile
import joalissongomes.dev.instagram.profile.presenter.ProfilePresenter
import joalissongomes.dev.instagram.search.view.SearchFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding, Profile.Presenter>(
    R.layout.fragment_profile,
    FragmentProfileBinding::bind
), Profile.View, BottomNavigationView.OnNavigationItemSelectedListener {

    override lateinit var presenter: Profile.Presenter
    private val adapter = PostAdapter()
    private var uuid: String? = null

    private var followListener: FollowListener? = null
    private var logoutListener: LogoutListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is LogoutListener) {
            logoutListener = context
        }

        if (context is FollowListener) {
            followListener = context
        }
    }

    override fun setupPresenter() {
        val repository = DependencyInjector.profileRepository()
        presenter = ProfilePresenter(this, repository)
    }

    override fun setupViews() {
        uuid = arguments?.getString(KEY_USER_ID)

        binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
        binding?.profileRv?.adapter = adapter
        binding?.profileNavTabs?.setOnNavigationItemSelectedListener(this)

        binding?.profileBtnEditProfile?.setOnClickListener {
            if (it.tag == true) {
                binding?.profileBtnEditProfile?.text = getString(R.string.follow)
                binding?.profileBtnEditProfile?.tag = false
                uuid?.let { id -> presenter.followUser(id, false) }
            } else if (it.tag == false) {
                binding?.profileBtnEditProfile?.text = getString(R.string.unfollow)
                binding?.profileBtnEditProfile?.tag = true
                uuid?.let { id -> presenter.followUser(id, true) }
            }
        }
        presenter.fetchUserProfile(uuid)
    }

    override fun showProgress(enabled: Boolean) {
        binding?.profileProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayUserProfile(user: Pair<User, Boolean?>) {
        val (userAuth, following) = user

        binding?.profileTxtPostsCount?.text = userAuth.postCount.toString()
        binding?.profileTxtFollowingCount?.text = userAuth.following.toString()
        binding?.profileTxtFollowersCount?.text = userAuth.followers.toString()
        binding?.profileTxtUsername?.text = userAuth.name
        binding?.profileTxtBio?.text = "BIO de teste"

        binding?.profileImgIcon?.let {
            if(userAuth.photoUrl != null) {
                Glide.with(requireContext()).load(userAuth.photoUrl).into(it)
            }
        }

        binding?.profileBtnEditProfile?.text = when (following) {
            null -> getString(R.string.edit_profile)
            true -> getString(R.string.unfollow)
            false -> getString(R.string.follow)
        }

        binding?.profileBtnEditProfile?.tag = following

        presenter.fetchUserPosts(uuid)
    }

    override fun displayRequestFailure(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    override fun displayEmptyPosts() {
        binding?.profileTxtEmptyPosts?.visibility = View.VISIBLE
        binding?.profileRv?.visibility = View.GONE
    }

    override fun displayFullPosts(posts: List<Post>) {
        binding?.profileTxtEmptyPosts?.visibility = View.GONE
        binding?.profileRv?.visibility = View.VISIBLE
        adapter.items = posts
        adapter.notifyDataSetChanged()
    }

    override fun followUpdated() {
        followListener?.followUpdated()
    }

    override fun getMenu(): Int {
        return R.menu.menu_profile
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_profile_grid -> {
                binding?.profileRv?.layoutManager = GridLayoutManager(requireContext(), 3)
            }
            R.id.menu_profile_list -> {
                binding?.profileRv?.layoutManager = LinearLayoutManager(requireContext())
            }
        }
        return true
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

    interface FollowListener {
        fun followUpdated()
    }

    companion object {
        const val KEY_USER_ID = "key_user_id"
    }
}
