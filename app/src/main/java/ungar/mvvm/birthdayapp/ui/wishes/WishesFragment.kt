package ungar.mvvm.birthdayapp.ui.wishes

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_birthdays.*
import kotlinx.android.synthetic.main.fragment_notes.*
import kotlinx.android.synthetic.main.fragment_wishes.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.first
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

    private val viewModel: WishesViewModel by viewModels()
    private val wishAdapter = WishesAdapter(this)
    private var binding: FragmentWishesBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentWishesBinding.inflate(inflater, container, false)
        viewModel.getQuotes()
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecyclerView()
        observeResponse()
        observeWishesMotion()
        initWishFilter()
    }

    private fun initRecyclerView() {

        binding?.recyclerViewWishes?.apply {
            adapter = wishAdapter
            layoutManager = LinearLayoutManager(requireContext())
            setHasFixedSize(true)
        }

    }

    //On response, fill recyclerview
    private fun observeResponse() {
        viewModel.myResponse.observe(viewLifecycleOwner) { response ->
            if(response.isNotEmpty()){
                removeErrorPlaceholder()
                wishAdapter.submitList(response)
            }
            else restoreErrorPlaceholder()
        }
    }

    private fun observeWishesMotion() {
        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            if (viewModel.preferencesFlow.first().openWishesCard)
                motionLayoutWish.progress = 1.0F
            else
                motionLayoutWish.progress = 0.0F
        }

        // MotionLayout
        wishesMiniFab.setOnClickListener {
            when (motionLayoutWish.progress) {
                0.0F -> {
                    motionLayoutWish.transitionToEnd()
                    viewModel.onWishesCardChanged(true)
                }
                1.0F -> {
                    motionLayoutWish.transitionToStart()
                    viewModel.onWishesCardChanged(false)
                }
            }
        }

    }

    //*work in progress*//
    private fun initWishFilter(){
        wishFilterEditText.setOnClickListener {
            Snackbar.make(requireView(), getString(R.string.in_development), Snackbar.LENGTH_SHORT).show()
            //val builder = AlertDialog.Builder(this.requireContext())
        }
    }

    override fun onItemClick(wish: Wish) {
        Snackbar.make(requireView(), getString(R.string.wishes_relation, wish.relation), Snackbar.LENGTH_LONG).show()
    }

    private fun removeErrorPlaceholder(){
        noWishes.visibility = View.GONE
    }

    private fun restoreErrorPlaceholder(){
        noWishes.visibility = View.VISIBLE
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}