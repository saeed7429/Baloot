package ms.saghafi.baloot.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import ms.saghafi.baloot.R
import ms.saghafi.baloot.databinding.FragmentArticleBinding
import ms.saghafi.baloot.databinding.FragmentProfileBinding
import ms.saghafi.baloot.utils.setInvisible


class ProfileFragment : Fragment() {

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_profile, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // About Me BottomShit Dialog Fragment:
        binding.profileFragmentAboutMeButton.setOnClickListener {
            val aboutMeFragment = AboutMeFragment.newInstance()
            aboutMeFragment.show(parentFragmentManager, "add_to_album")
        }

        // Toolbar Setting:
        binding.profileFragmentToolbar.toolbarSimpleBackImageButton.setInvisible()
        binding.profileFragmentToolbar.toolbarSimpleTitleTextView.text = getString(R.string.profile)

        // Handle Github CardView Click:
        binding.profileFragmentGithubCardView.setOnClickListener {
            val uri = Uri.parse("https://github.com/saeed7429")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            val bundle = Bundle()
            bundle.putBoolean("new_window", true)
            intent.putExtras(bundle)
            context?.startActivity(intent)
        }

        // Handle Linkedin CardView Click:
        binding.profileFragmentLinkedInCardView.setOnClickListener {
            val uri = Uri.parse("https://www.linkedin.com/in/saeed-saghafi/")
            val intent = Intent(Intent.ACTION_VIEW, uri)
            val bundle = Bundle()
            bundle.putBoolean("new_window", true)
            intent.putExtras(bundle)
            context?.startActivity(intent)
        }
    }

}