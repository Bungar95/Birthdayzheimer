package ungar.mvvm.birthdayapp.ui.notes

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_add_note.*
import kotlinx.android.synthetic.main.dialog_details_note.*
import kotlinx.android.synthetic.main.dialog_details_note.view.*
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.item_note.*
import ungar.mvvm.birthdayapp.R
import ungar.mvvm.birthdayapp.databinding.FragmentNotesBinding
import ungar.mvvm.birthdayapp.model.Note
import ungar.mvvm.birthdayapp.model.Wish

@AndroidEntryPoint
class NotesFragment: Fragment(R.layout.fragment_notes), NotesAdapter.OnItemClickListener {

    private val viewModel: NotesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentNotesBinding.bind(view)

        val noteAdapter = NotesAdapter(this, this.requireContext())

        binding.apply {
            recyclerViewNotes.apply{
                adapter = noteAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.notes.observe(viewLifecycleOwner) { notes ->
            notes?.let { noteAdapter.submitList(notes) }
            if(notes.isNotEmpty()) removeEmptyPlaceholder()
            else restoreEmptyPlaceholder()
        }

        fab_add_note.setOnClickListener {

            var contentValue = ""
            var important: Boolean = false

            // Show a bottom sheet containing the form to insert a new event
            val dialog = MaterialDialog(this.requireContext()).show {
                cornerRadius(res = R.dimen.rounded_corners)
                title(R.string.new_note)
                icon(R.drawable.ic_baseline_cake_24)
                message(R.string.new_note_description)
                customView(R.layout.dialog_add_note)
                //getActionButton(WhichButton.POSITIVE).isEnabled = false

                positiveButton(R.string.add_note) {
                    if(noteContent.text.isNullOrBlank()){
                        Snackbar.make(requireView(), getString(R.string.no_note_content), Snackbar.LENGTH_SHORT ).show()
                        return@positiveButton
                    }
                    contentValue = noteContent.text.toString()
                    important = checkbox_important.isChecked
                    val newNote = Note(
                        contentValue, important
                    )
                    viewModel.createNote(newNote)
                    dismiss()
                }

                negativeButton(R.string.cancel_birthday) {
                    dismiss()
                }

            }
        }

    }

    override fun onItemClick(note: Note) {
        //Snackbar.make(requireView(), viewModel.birthdayCountdown(birthday), Snackbar.LENGTH_SHORT).show()
        val title = getString(R.string.details)
        val dialog = MaterialDialog(this.requireContext()).show {
            title(text = title)
            icon(R.drawable.ic_note_24)
            cornerRadius(res = R.dimen.rounded_corners)
            customView(R.layout.dialog_details_note, scrollable = true)
            negativeButton(R.string.cancel_birthday) {
                dismiss()
            }
            detailsContent.text = note.content
            detailsNoteDate.text = note.createdDateFormatted
            if(note.important) detailsImportant.visibility = View.VISIBLE
            else detailsImportant.visibility = View.GONE

        }
        // Setup listeners and texts
        val customView = dialog.getCustomView()
        val editButton = customView.btnEditNote
        val deleteButton = customView.btnDeleteNote

        deleteButton.setOnClickListener {
            deleteNote(note)
            dialog.dismiss()
        }

        editButton.setOnClickListener {
            editNote(note)
            dialog.dismiss()
        }
    }

    private fun deleteNote(note: Note) {
        viewModel.deleteNote(note)
    }

    private fun editNote(note: Note) {
        var contentValue = note.content
        var important = note.important
        var completed = note.completed
        val dialog =
            MaterialDialog(this.requireContext()).show {
                cornerRadius(res = R.dimen.rounded_corners)
                title(R.string.edit_note)
                icon(R.drawable.ic_baseline_cake_24)
                message(R.string.edit_note_description)
                customView(R.layout.dialog_add_note) // no need for a new dialog
                noteContent.setText(contentValue)
                checkbox_important.isChecked = important
                positiveButton(R.string.edit_birthday) {
                    if(noteContent.text.isNullOrBlank()){
                        Snackbar.make(requireView(), getString(R.string.no_note_content), Snackbar.LENGTH_SHORT ).show()
                        return@positiveButton
                    }
                    contentValue = noteContent.text.toString()
                    important = checkbox_important.isChecked
                    val editedNote = Note(
                        id = note.id,
                        content = contentValue,
                        important = important,
                        completed = completed
                        //might add "edited" to complement "created"
                    )
                    viewModel.updateNote(editedNote)
                    dismiss()
                }

                negativeButton(R.string.cancel_birthday) {
                    dismiss()
                }

            }
    }

    override fun onCheckBoxClick(note: Note, isChecked: Boolean) {
        viewModel.onNoteCheckedChanged(note, isChecked)
    }

    private fun removeEmptyPlaceholder(){
        val placeholder: TextView = requireView().findViewById(R.id.noNotes) ?: return
        placeholder.visibility = View.GONE
    }

    private fun restoreEmptyPlaceholder(){
        noNotes.visibility = View.VISIBLE
    }
}