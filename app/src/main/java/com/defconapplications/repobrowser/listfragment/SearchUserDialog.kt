package com.defconapplications.repobrowser.listfragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import com.defconapplications.repobrowser.R
import com.defconapplications.repobrowser.databinding.SearchUserDialogBinding

class SearchUserDialog() : DialogFragment() {

    lateinit var binding : SearchUserDialogBinding

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.search_user_dialog, container,false)
        dialog?.requestWindowFeature(Window.FEATURE_NO_TITLE)

        arguments?.let {
            binding.field.setText(it.getString("user"))
        }

        binding.searchButton.setOnClickListener {
            (parentFragment as ListFragment).searchName(binding.field.text.toString())
            dialog!!.dismiss()
        }

        binding.cancelButton.setOnClickListener {
            dialog!!.dismiss()
        }

        return binding.root
    }
}