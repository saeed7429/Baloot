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

        viewModel = activity.run {  ViewModelProvider(this!!).get(HomeViewModel::class.java) }

        binding.homeFragmentToolbar.toolbarSimpleBackImageButton.setInvisible()
        binding.homeFragmentToolbar.toolbarSimpleTitleTextView.text = getString(R.string.articles)


        val adapter = ArticlesAdapter(object : IOnItemClickListener<Article> {
            override fun onClick(clickedModel: Article) {
                viewModel.selectedArticle = clickedModel
                findNavController().navigate(R.id.action_homeFragment_to_articleFragment)
            }
        })

        binding.homeFragmentRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.homeFragmentRecyclerView.adapter = adapter

        viewModel.getArticles(page)

        viewModel.allArticles.observe(viewLifecycleOwner,{
            adapter.submitList(it)
        })

        viewModel.message.observe(viewLifecycleOwner,{
            if (it.isNotBlank())
                showToastMessage(it)
        })

        addScrollEndListener()

        binding.homeFragmentDeleteCacheFloatingActionButton.setOnClickListener {
            viewModel.deleteArticles()
            page = 1
            viewModel.getArticles(page)
            addScrollEndListener()
        }

        viewModel.status.observe(viewLifecycleOwner,{
            when(it){
                ApiStatus.LOADING -> binding.homeFragmentProgressBar.setVisible()
                else -> binding.homeFragmentProgressBar.setGone()
            }
        })
    }

    private fun addScrollEndListener(){
        binding.homeFragmentRecyclerView.addOnScrolledToEnd {
            page++
            viewModel.getArticles(page)
        }
    }

}