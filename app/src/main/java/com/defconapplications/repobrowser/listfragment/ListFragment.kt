package com.defconapplications.repobrowser.listfragment

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.findNavController
import com.defconapplications.repobrowser.R
import com.defconapplications.repobrowser.RepoAdapter
import com.defconapplications.repobrowser.RepoClickListener
import com.defconapplications.repobrowser.databinding.ListFragmentBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : Fragment() {
    private lateinit var binding: ListFragmentBinding
    private lateinit var viewModel: ListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.list_fragment, container, false)
        viewModel =
                ViewModelProvider(this).get(ListViewModel::class.java)

        binding.recycler.adapter = RepoAdapter(RepoClickListener {
            findNavController().navigate(ListFragmentDirections.actionListFragmentToDetailsFragment(it.id))
        })
        binding.toolbar.setNavigationOnClickListener {
            val d = SearchUserDialog()
            val args = Bundle()
            args.putString("user", viewModel.user.value)
            d.arguments = args
            d.show(childFragmentManager, "searchDialogTag")
        }
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.refresh -> {
                    viewModel.fetchRepoData()
                    binding.spinner.visibility = View.VISIBLE
                    binding.recycler.visibility = View.GONE
                    true
                }
                else -> false
            }
        }

        viewModel.user.observe(viewLifecycleOwner, {
            it?.let {
                binding.toolbar.title = "${it}'s repos"
            }
        })

        viewModel.repoList.observe(viewLifecycleOwner, {
            if (!it.isNullOrEmpty()) {
                binding.spinner.visibility = View.GONE
                binding.recycler.visibility = View.VISIBLE
                (binding.recycler.adapter as RepoAdapter).submitList(it)
            } else {
                binding.spinner.visibility = View.VISIBLE
                binding.recycler.visibility = View.GONE
            }
        })

        viewModel.errorMessage.observe(viewLifecycleOwner, {
            it?.let {
                Snackbar.make(binding.root, it, Snackbar.LENGTH_INDEFINITE)
                    .show()
            }
        })

        return binding.root
    }

    fun searchName(name : String) {
        viewModel.searchUser(name)
    }

}