package ms.saghafi.baloot.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import ms.saghafi.baloot.R
import ms.saghafi.baloot.databinding.FragmentArticleBinding
import ms.saghafi.baloot.databinding.FragmentHomeBinding
import ms.saghafi.baloot.viewModel.HomeViewModel

class ArticleFragment : Fragment() {

    private lateinit var binding: FragmentArticleBinding
    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_article, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = activity.run {  ViewModelProvider(this!!).get(HomeViewModel::class.java) }

        // Pass Current Article To Layout DataBinding Variable:
        binding.article = viewModel.selectedArticle

        // Toolbar Settings:
        binding.articleFragmentToolbar.toolbarSimpleBackImageButton.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.articleFragmentToolbar.toolbarSimpleTitleTextView.text = getString(R.string.article_details)

        // Load Image With Picasso:
        viewModel.selectedArticle.urlToImage?.let {
            Picasso.get()
                .load(it)
                .fit()
                .into(binding.articleFragmentImageView)
        }

        // Open Article Link When Click On Source Name:
        binding.articleFragmentSourceTextView.setOnClickListener {
            val uri = Uri.parse(viewModel.selectedArticle.url)
            val intent = Intent(Intent.ACTION_VIEW, uri)
            val bundle = Bundle()
            bundle.putBoolean("new_window", true)
            intent.putExtras(bundle)
            context?.startActivity(intent)
        }
    }

}