package ungar.mvvm.datepicker.ui.birthdays

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_birthdays.*
import kotlinx.android.synthetic.main.fragment_birthdays.view.*
import kotlinx.coroutines.test.withTestContext
import ungar.mvvm.datepicker.R
import ungar.mvvm.datepicker.databinding.FragmentBirthdaysBinding
import ungar.mvvm.datepicker.model.Birthday
import ungar.mvvm.datepicker.ui.datepicker.DatePickerDialogFragment

@AndroidEntryPoint
class BirthdaysFragment : Fragment(R.layout.fragment_birthdays), BirthdaysAdapter.OnItemClickListener {

    private val viewModel: BirthdaysViewModel by viewModels()

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

        viewModel.birthdays.observe(viewLifecycleOwner) {
            birthdayAdapter.submitList(it)
        }

        btn_add.setOnClickListener {
            viewModel.addBirthday(Birthday(
                et_name.text.toString(),
                btn_date.text.toString().split("/")[0].toInt(),
                btn_date.text.toString().split("/")[1].toInt(),
                btn_date.text.toString().split("/")[2].toInt())
            )
        }

        btn_date.setOnClickListener {
            showDatePickerDialog(btn_date)
        }
    }

    override fun onItemClick(birthday: Birthday) {
        Snackbar.make(requireView(), "${birthday.name}", Snackbar.LENGTH_SHORT).show()
        //This is where "x days until birthday" will go.
    }

    private fun showDatePickerDialog(btn: Button){
        val newFragment = DatePickerDialogFragment(btn)
        newFragment.show(childFragmentManager, "datePicker")
    }

}