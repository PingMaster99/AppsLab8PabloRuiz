package com.example.newsapp.home

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.newsapp.databinding.HomeFragmentBinding
import com.example.newsapp.network.AlgoliaApiStatus
/**
 * <h1>HomeFragment</h1>
 *<p>
 * This fragment displays parameters used to search for news articles
 * using the Algolia API
 *</p>
 *
 * @author Pablo Ruiz (PingMaster99)
 * @version 1.0
 * @since 2020-06-02
 **/
class HomeFragment : Fragment() {

    // ViewModel and DataBinding
    private lateinit var viewModel: HomeViewModel
    private lateinit var binding: HomeFragmentBinding

    // Arguments for the next fragment
    private lateinit var author: String
    private lateinit var points: String

    /**
     * Builds the app initialization displays the information requested
     * @param savedInstanceState saved App data
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Updates title and subtitle
        (activity as AppCompatActivity).supportActionBar?.title = "Buscador de noticias"
        (activity as AppCompatActivity).supportActionBar?.subtitle = ""
        binding = HomeFragmentBinding.inflate(inflater)
        return binding.root
    }

    /**
     * Builds the app initialization displays the information requested
     * @param savedInstanceState saved App data
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        binding.viewModel = viewModel


        // Search button goes to the next fragment
        binding.search.setOnClickListener {
            viewModel.actionViewNews()
        }

        // Observer of the state of ViewNews navigates with parameters
        viewModel.viewNews.observe(viewLifecycleOwner, Observer {
            // Author
            if(binding.author.text.toString() != "") {
                author = "author_${binding.author.text}"
            } else {
                author = "story"    // General author if empty
            }

            // Points
            if(binding.minimumPoints.text.toString() != "") {
                points = "points>${binding.minimumPoints.text}"
            } else {
                points = "points>-1"    // General points if empty
            }

            // Navigates to the next fragment if button was pressed
            if(it) {
                requireView().hideKeyboard()
                requireView().findNavController().navigate(
                    HomeFragmentDirections.actionHomeFragmentToNewsListFragment(author,
                        binding.keyword.text.toString(), points)
                )
                viewModel.viewNewsComplete()
            }
        })

        // Not found or API error
        viewModel.status.observe(viewLifecycleOwner, Observer {
            if(it == AlgoliaApiStatus.ERROR) {
                Toast.makeText(this.context, "Error de red o parametros no encontrados",
                    Toast.LENGTH_SHORT).show()
                viewModel.startStatus()
            }
        })
    }

    // Hides the keyboard
    fun View.hideKeyboard() {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE)
                as InputMethodManager
        imm.hideSoftInputFromWindow(windowToken, 0)
    }
}
