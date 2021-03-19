package ungar.mvvm.birthdayapp.ui.wishes

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ungar.mvvm.birthdayapp.R
import ungar.mvvm.birthdayapp.databinding.FragmentWishesBinding
import ungar.mvvm.birthdayapp.model.Wish
import ungar.mvvm.birthdayapp.network.APIService
import ungar.mvvm.birthdayapp.network.RetrofitInstance
import ungar.mvvm.birthdayapp.repository.Repository
import ungar.mvvm.birthdayapp.ui.notes.NotesAdapter

@AndroidEntryPoint
class WishesFragment: Fragment(R.layout.fragment_wishes), WishesAdapter.OnItemClickListener {

    val viewModel: WishesViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentWishesBinding.bind(view)

        val wishAdapter = WishesAdapter(this, this.requireContext())


        binding.apply {
            recyclerViewWishes.apply{
                adapter = wishAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }

        }

        val repository = Repository()
        viewModel.getQuotes()
        viewModel.myResponse.observe(viewLifecycleOwner, { response ->
            if(response.isSuccessful)
                Log.d("Response ->", response.body().toString())
            else
                Log.d("Response ->", response.errorBody().toString())

        })

    }

    override fun onItemClick(wish: Wish) {
        TODO("Not yet implemented")
    }
}