package com.alessandrofarandagancio.anywhere.ui

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.core.view.ViewCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.alessandrofarandagancio.anywhere.R
import com.alessandrofarandagancio.anywhere.databinding.CharacterListContentBinding
import com.alessandrofarandagancio.anywhere.databinding.FragmentItemListBinding
import com.alessandrofarandagancio.anywhere.ui.model.UICharacter
import com.alessandrofarandagancio.anywhere.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A Fragment representing a list of Pings. This fragment
 * has different presentations for handset and larger screen devices. On
 * handsets, the fragment presents a list of items, which when touched,
 * lead to a {@link ItemDetailFragment} representing
 * item details. On larger screens, the Navigation controller presents the list of items and
 * item details side-by-side using two vertical panes.
 */

@AndroidEntryPoint
class CharacterListFragment : Fragment() {

    /**
     * Method to intercept global key events in the
     * item list fragment to trigger keyboard shortcuts
     * Currently provides a toast when Ctrl + Z and Ctrl + F
     * are triggered
     */
    private val unhandledKeyEventListenerCompat =
        ViewCompat.OnUnhandledKeyEventListenerCompat { v, event ->
            if (event.keyCode == KeyEvent.KEYCODE_Z && event.isCtrlPressed) {
                Toast.makeText(
                    v.context, "Undo (Ctrl + Z) shortcut triggered", Toast.LENGTH_LONG
                ).show()
                true
            } else if (event.keyCode == KeyEvent.KEYCODE_F && event.isCtrlPressed) {
                Toast.makeText(
                    v.context, "Find (Ctrl + F) shortcut triggered", Toast.LENGTH_LONG
                ).show()
                true
            }
            false
        }

    private var _binding: FragmentItemListBinding? = null

    private val characterViewModel: CharacterViewModel by activityViewModels()

    private lateinit var searchView: SearchView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentItemListBinding.inflate(inflater, container, false)

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ViewCompat.addOnUnhandledKeyEventListener(view, unhandledKeyEventListenerCompat)

        val recyclerView: RecyclerView = binding.itemList
        searchView = binding.searchView


        // Leaving this not using view binding as it relies on if the view is visible the current
        // layout configuration (layout, layout-sw600dp)
        val itemDetailFragmentContainer: View? = view.findViewById(R.id.item_detail_nav_container)

        setupRecyclerView(recyclerView, itemDetailFragmentContainer)
    }

    private fun setupRecyclerView(
        recyclerView: RecyclerView, itemDetailFragmentContainer: View?
    ) {
        characterViewModel.characterListResponse.observe(viewLifecycleOwner, Observer {
            var adapter = SimpleItemRecyclerViewAdapter(
                it, itemDetailFragmentContainer
            )
            recyclerView.adapter = adapter
            searchView.setOnQueryTextListener(OnTextChangedListener(adapter))
        })
    }

    class SimpleItemRecyclerViewAdapter(
        private val characters: List<UICharacter>, private val itemDetailFragmentContainer: View?
    ) : RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder>(), Filterable {

        private var charactersFiltered: List<UICharacter> = characters

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

            val binding = CharacterListContentBinding.inflate(
                LayoutInflater.from(parent.context), parent, false
            )
            return ViewHolder(binding)
        }

        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            val item = charactersFiltered[position]
            holder.contentView.text = item.name

            with(holder.itemView) {
                tag = item
                setOnClickListener { itemView ->
                    val item = itemView.tag as UICharacter
                    val bundle = Bundle()
                    bundle.putString(
                        CharacterDetailFragment.ARG_ITEM_ID, item.id
                    )
                    if (itemDetailFragmentContainer != null) {
                        itemDetailFragmentContainer.findNavController()
                            .navigate(R.id.fragment_item_detail, bundle)
                    } else {
                        itemView.findNavController().navigate(R.id.show_item_detail, bundle)
                    }
                }
            }
        }

        override fun getItemCount() = charactersFiltered.size

        inner class ViewHolder(binding: CharacterListContentBinding) :
            RecyclerView.ViewHolder(binding.root) {
            val contentView: TextView = binding.content
        }

        override fun getFilter(): Filter {
            return object : Filter() {
                override fun performFiltering(constraint: CharSequence): FilterResults {
                    charactersFiltered = if (constraint.isEmpty()) characters else {
                        val filteredList = mutableListOf<UICharacter>()
                        characters.filter {
                            (it.id.contains(constraint, true)) or (it.text.contains(constraint, true))
                        }.forEach { filteredList.add(it) }
                        filteredList
                    }
                    return FilterResults().apply { values = charactersFiltered }
                }

                override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                    charactersFiltered = if (results?.values == null) {
                        emptyList()
                    } else {
                        results.values as ArrayList<UICharacter>
                    }
                    notifyDataSetChanged()
                }
            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    class OnTextChangedListener(private val adapter: SimpleItemRecyclerViewAdapter) :
        SearchView.OnQueryTextListener {
        override fun onQueryTextSubmit(query: String?): Boolean {
            adapter.filter.filter(query)
            return false
        }

        override fun onQueryTextChange(newText: String?): Boolean {
            adapter.filter.filter(newText)
            return false
        }

    }
}


