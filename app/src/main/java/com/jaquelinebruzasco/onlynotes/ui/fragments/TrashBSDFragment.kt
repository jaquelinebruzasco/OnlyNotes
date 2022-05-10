package com.jaquelinebruzasco.onlynotes.ui.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.jaquelinebruzasco.onlynotes.R
import com.jaquelinebruzasco.onlynotes.databinding.FragmentBottomSheetDialogTrashBinding
import com.jaquelinebruzasco.onlynotes.domain.local.model.TrashNotesModel
import com.jaquelinebruzasco.onlynotes.ui.viewModel.TrashBSDFragmentViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TrashBSDFragment(
    private val note: TrashNotesModel
) : BottomSheetDialogFragment() {

    private val viewModel by viewModels<TrashBSDFragmentViewModel>()
    private lateinit var _binding: FragmentBottomSheetDialogTrashBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetDialogTrashBinding.inflate(inflater, container, false)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        _binding.apply {
            tvDelete.setOnClickListener {
                viewModel.permanentlyDelete(note)
                Toast.makeText(requireContext(), R.string.permanently_delete_alert, Toast.LENGTH_LONG).show()
                this@TrashBSDFragment.dismiss()
            }
            tvRestore.setOnClickListener {
                viewModel.restoreNote(note)
                Toast.makeText(requireContext(), R.string.restore_alert, Toast.LENGTH_LONG).show()
                this@TrashBSDFragment.dismiss()
            }
        }
    }
}