package joalissongomes.dev.instagram.search.view

import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import joalissongomes.dev.instagram.R
import joalissongomes.dev.instagram.common.base.BaseFragment
import joalissongomes.dev.instagram.common.base.DependencyInjector
import joalissongomes.dev.instagram.common.model.User
import joalissongomes.dev.instagram.databinding.FragmentSearchBinding
import joalissongomes.dev.instagram.search.Search
import joalissongomes.dev.instagram.search.presenter.SearchPresenter

class SearchFragment : BaseFragment<FragmentSearchBinding, Search.Presenter>(
    R.layout.fragment_search,
    FragmentSearchBinding::bind
), Search.View {

    override lateinit var presenter: Search.Presenter

    private var searchListener: SearchListener? = null

    private val adapter by lazy {
        SearchAdapter(onItemClicked)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        if (context is SearchListener) {
            searchListener = context
        }
    }

    override fun setupViews() {
        binding?.searchRv?.layoutManager = LinearLayoutManager(requireContext())
        binding?.searchRv?.adapter = adapter
    }

    override fun setupPresenter() {
        val repository = DependencyInjector.searchRepository()
        presenter = SearchPresenter(this, repository)
    }

    private val onItemClicked: (String) -> Unit = { uuid ->
        searchListener?.goToProfile(uuid)
    }

    override fun getMenu() = R.menu.menu_search

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)

        val searchManager =
            requireActivity().getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = (menu.findItem(R.id.menu_search).actionView as SearchView)

        searchView.apply {
            setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (query?.isNotEmpty() == true) {
                        presenter.fetchUsers(query)
                        return true
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    if (newText?.isNotEmpty() == true) {
                        presenter.fetchUsers(newText)
                        return true
                    }
                    return false
                }
            })
        }
    }

    override fun showProgress(enabled: Boolean) {
        binding?.searchProgress?.visibility = if (enabled) View.VISIBLE else View.GONE
    }

    override fun displayFullUsers(users: List<User>) {
        binding?.searchRv?.visibility = View.VISIBLE
        binding?.searchTxtEmpty?.visibility = View.GONE

        adapter.items = users
        adapter.notifyDataSetChanged()
    }

    override fun displayEmptyUsers() {
        binding?.searchRv?.visibility = View.GONE
        binding?.searchTxtEmpty?.visibility = View.VISIBLE
    }

    interface SearchListener {
        fun goToProfile(uuid: String)
    }
}