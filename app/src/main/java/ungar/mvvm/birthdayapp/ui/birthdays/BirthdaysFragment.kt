package ungar.mvvm.birthdayapp.ui.birthdays

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.afollestad.materialdialogs.datetime.datePicker
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.dialog_add_birthday.*
import kotlinx.android.synthetic.main.dialog_details_birthday.*
import kotlinx.android.synthetic.main.dialog_details_birthday.view.*
import kotlinx.android.synthetic.main.fragment_birthdays.*
import kotlinx.coroutines.flow.first
import ungar.mvvm.birthdayapp.R
import ungar.mvvm.birthdayapp.databinding.FragmentBirthdaysBinding
import ungar.mvvm.birthdayapp.model.Birthday
import java.time.LocalDate
import java.util.*

@AndroidEntryPoint
class BirthdaysFragment : Fragment(R.layout.fragment_birthdays), BirthdaysAdapter.OnItemClickListener {

    private val viewModel: BirthdaysViewModel by viewModels()

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentBirthdaysBinding.bind(view)

        val birthdayAdapter = BirthdaysAdapter(this)

        binding.apply {
            recyclerViewBirthdays.apply{
                adapter = birthdayAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }

        viewModel.birthdays.observe(viewLifecycleOwner) { birthdays ->
            birthdays?.let { birthdayAdapter.submitList(birthdays) }
            if(birthdays.isNotEmpty()) removeEmptyPlaceholder()
            else restoreEmptyPlaceholder()
        }

        // The options fab button launches in the same state it was during the last use
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            if (viewModel.preferencesFlow.first().openOptionsCard)
                homeMain.progress = 1.0F
            else
                homeMain.progress = 0.0F
        }

        // MotionLayout
        optionsMiniFab.setOnClickListener {
            when (homeMain.progress) {
                0.0F -> {
                    homeMain.transitionToEnd()
                    viewModel.onOptionsCardChanged(true)
                }
                1.0F -> {
                    homeMain.transitionToStart()
                    viewModel.onOptionsCardChanged(false)
                }
            }
        }

    }

    override fun onItemClick(birthday: Birthday) {
        //Snackbar.make(requireView(), viewModel.birthdayCountdown(birthday), Snackbar.LENGTH_SHORT).show()
        val title = "Details - " + birthday.name
        val dialog = MaterialDialog(this.requireContext()).show {
            title(text = title)
            icon(R.drawable.ic_baseline_cake_24)
            cornerRadius(res = R.dimen.rounded_corners)
            customView(R.layout.dialog_details_birthday, scrollable = true)
            negativeButton(R.string.cancel_birthday) {
                dismiss()
            }
            detailsName.text = birthday.name
            detailsBirthDate.text = "${birthday.day}/${birthday.month}/${birthday.year}"
            detailsCountdown.text = viewModel.birthdayCountdown(birthday)
            viewModel.setYearEditText(birthday, detailsYears)
        }
        // Setup listeners and texts
        val customView = dialog.getCustomView()
        val editButton = customView.btnEditBirthday
        val deleteButton = customView.btnDeleteBirthday

        deleteButton.setOnClickListener {
            deleteBirthday(birthday)
            dialog.dismiss()
        }

        editButton.setOnClickListener {
            editBirthday(birthday)
            dialog.dismiss()
        }
    }

    private fun deleteBirthday(birthday: Birthday) {
        viewModel.deleteBirthday(birthday)
    }

    private fun editBirthday(birthday: Birthday) {
        var nameValue = birthday.name
        var dateValue: LocalDate = LocalDate.of(birthday.year, birthday.month, birthday.day)
        var dateDialog: MaterialDialog? = null
        val endDate = Calendar.getInstance()
        var currentDate = Calendar.getInstance()
        val dialog =
            MaterialDialog(this.requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                cornerRadius(res = R.dimen.rounded_corners)
                title(R.string.edit_birthday)
                icon(R.drawable.ic_baseline_cake_24)
                message(R.string.edit_birthday_description)
                customView(R.layout.dialog_add_birthday) // no need for a new dialog
                birthdayName.setText(nameValue)
                positiveButton(R.string.edit_birthday) {
                    nameValue = birthdayName.text.toString()
                    val editedBirthday = Birthday(
                        id = birthday.id,
                        name = nameValue,
                        day = dateValue.dayOfMonth,
                        month = dateValue.monthValue,
                        year = dateValue.year
                    )
                    viewModel.updateBirthday(editedBirthday)
                    dismiss()
                }

                negativeButton(R.string.cancel_birthday) {
                    dismiss()
                }

                birthdayDate.setOnClickListener {
                    // Prevent double dialogs on fast click
                    if(dateDialog == null) {
                        dateDialog = MaterialDialog(this.windowContext).show {
                            cancelable(false)
                            cancelOnTouchOutside(false)
                            datePicker(maxDate = endDate, currentDate = viewModel.localDatetoCalendar(dateValue)) { _, date ->
                                val year = date.get(Calendar.YEAR)
                                val month = date.get(Calendar.MONTH) + 1
                                val day = date.get(Calendar.DAY_OF_MONTH)
                                dateValue = LocalDate.of(year, month, day)
                                // If ok is pressed, the last selected date is saved if the dialog is reopened
                                //birthdayDate.setText(currentDate.toString()) crashes
                            }
                        }

                        Handler(Looper.getMainLooper()).postDelayed({ dateDialog = null }, 750)
                    }
                }
            }
    }

    private fun removeEmptyPlaceholder(){
        val placeholder: TextView = requireView().findViewById(R.id.noEvents) ?: return
        placeholder.visibility = View.GONE
    }

    private fun restoreEmptyPlaceholder(){
        noEvents.visibility = View.VISIBLE
    }

}