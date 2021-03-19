package ungar.mvvm.birthdayapp.ui.notes

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ungar.mvvm.birthdayapp.model.Birthday
import ungar.mvvm.birthdayapp.model.BirthdayDao
import ungar.mvvm.birthdayapp.model.Note
import ungar.mvvm.birthdayapp.model.PreferencesManager

@HiltViewModel
class NotesViewModel @ViewModelInject constructor(
    private val birthdayDao: BirthdayDao
): ViewModel() {

    val notes = birthdayDao.getNotes().asLiveData()

    fun createNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        birthdayDao.insertNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        birthdayDao.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch(Dispatchers.IO) {
        birthdayDao.deleteNote(note)
    }

    fun onNoteCheckedChanged(note: Note, isChecked: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        birthdayDao.updateNote(note.copy(completed = isChecked))
    }
}