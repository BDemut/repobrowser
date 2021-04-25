package com.defconapplications.repobrowser.detailsfragment

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
import androidx.recyclerview.widget.GridLayoutManager
import com.defconapplications.repobrowser.R
import com.defconapplications.repobrowser.databinding.DetailsFragmentBinding
import com.defconapplications.repobrowser.formatCreatedDate
import com.defconapplications.repobrowser.formatUpdatedDate
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var viewModel: DetailsViewModel
    private lateinit var binding: DetailsFragmentBinding
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)

        binding = DataBindingUtil.inflate(inflater, R.layout.details_fragment, container, false)
        binding.viewModel = viewModel
        binding.lifecycleOwner = this


        viewModel.repo.observe(viewLifecycleOwner, {
            it?.let { repo ->
                binding.stars.text = repo.stargazers_count.toString()
                binding.forks.text = repo.forks_count.toString()
                binding.issues.text = repo.open_issues_count.toString()
                binding.toolbar.title = repo.name
                binding.description.text = repo.description
                binding.created.text = formatCreatedDate(repo.created_at)
                binding.updated.text = formatUpdatedDate(repo.updated_at)

                binding.githubFab.setOnClickListener {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.data = Uri.parse(repo.html_url)
                    startActivity(intent)
                }

                if (repo.languages == null) {
                    viewModel.getLanguageData()
                } else {
                    binding.spinner.visibility = View.GONE
                    binding.langRecycler.visibility = View.VISIBLE
                    binding.langLabel.visibility = View.VISIBLE

                    if (repo.languages.size == 1)
                        binding.langRecycler.layoutManager = GridLayoutManager(activity, 1)
                    else
                        binding.langRecycler.layoutManager = GridLayoutManager(activity, 2)
                    (binding.langRecycler.adapter as TextAdapter).submitList(repo.languages)
                }
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            it?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_INDEFINITE)
                    .show()
            }
        })

        binding.langRecycler.layoutManager = GridLayoutManager(activity, 2)
        binding.langRecycler.adapter = TextAdapter()

        binding.toolbar.setNavigationOnClickListener{
            findNavController().popBackStack()
        }

        return binding.root
    }

}