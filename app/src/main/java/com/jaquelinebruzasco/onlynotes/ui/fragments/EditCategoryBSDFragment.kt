package com.jaquelinebruzasco.onlynotes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jaquelinebruzasco.onlynotes.R
import com.jaquelinebruzasco.onlynotes.databinding.FragmentBottomSheetDialogBinding
import com.jaquelinebruzasco.onlynotes.domain.local.model.CategoryModel

class EditCategoryBSDFragment(
    private val category: CategoryModel,
    private val save: (CategoryModel) -> Unit
) : BottomSheetDialogFragment() {

    private lateinit var _binding: FragmentBottomSheetDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentBottomSheetDialogBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding.apply {
            etEditCategory.setText(category.name)
            btnSave.setOnClickListener {
                val newCategoryName = etEditCategory.text.toString()
                when {
                    newCategoryName.isBlank() -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.empty_category_alert),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    newCategoryName == category.name -> {
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.new_category_alert),
                            Toast.LENGTH_LONG
                        ).show()
                    }
                    else -> {
                        category.name = newCategoryName
                        save.invoke(category)
                    }
                }
            }
            btnCancel.setOnClickListener { this@EditCategoryBSDFragment.dismiss() }
        }
    }

}