package ms.saghafi.baloot.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import ms.saghafi.baloot.R
import ms.saghafi.baloot.databinding.FragmentHomeBinding
import ms.saghafi.baloot.model.Article
import ms.saghafi.baloot.utils.*
import ms.saghafi.baloot.viewModel.HomeViewModel

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var viewModel: HomeViewModel
    private var page = 1L

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_home, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize HomeViewModel As Shared ViewModel:
        viewModel = activity.run {  ViewModelProvider(this!!).get(HomeViewModel::class.java) }

        // Toolbar Settings:
        binding.homeFragmentToolbar.toolbarSimpleBackImageButton.setInvisible()
        binding.homeFragmentToolbar.toolbarSimpleTitleTextView.text = getString(R.string.articles)

        // Define Adapter For Articles List RecyclerView :
        val adapter = ArticlesAdapter(object : IOnItemClickListener<Article> {
            override fun onClick(clickedModel: Article) {
                viewModel.selectedArticle = clickedModel
                findNavController().navigate(R.id.action_homeFragment_to_articleFragment)
            }
        })

        // RecyclerView Settings:
        binding.homeFragmentRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.homeFragmentRecyclerView.adapter = adapter

        // Get First Page Of Articles:
        viewModel.getArticles(page)

        // Observe ViewModel Articles For Update Adapter List:
        viewModel.allArticles.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })

        // Observe ViewModel Message For Toast it:
        viewModel.message.observe(viewLifecycleOwner,{
            if (it.isNotBlank())
                showToastMessage(it)
        })

        addScrollEndListener()

        // Set Click Listener For Delete Cache Floating Action Button To Clear Local Cache:
        binding.homeFragmentDeleteCacheFloatingActionButton.setOnClickListener {
            viewModel.deleteArticles()
            page = 1
            viewModel.getArticles(page)
            addScrollEndListener()
        }

        // Observe ViewModel Status For Show And Hide ProgressBar:
        viewModel.status.observe(viewLifecycleOwner,{
            when(it){
                ApiStatus.LOADING -> binding.homeFragmentProgressBar.setVisible()
                else -> binding.homeFragmentProgressBar.setGone()
            }
        })
    }

    // Add Scroll To End Listener For Request Next Page From Api:
    private fun addScrollEndListener(){
        binding.homeFragmentRecyclerView.addOnScrolledToEnd {
            page++
            viewModel.getArticles(page)
        }
    }

}