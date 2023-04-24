package com.alessandrofarandagancio.anywhere.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import coil.load
import com.alessandrofarandagancio.anywhere.databinding.FragmentCharaterDetailBinding
import com.alessandrofarandagancio.anywhere.ui.model.UICharacter
import com.alessandrofarandagancio.anywhere.viewmodel.CharacterViewModel
import dagger.hilt.android.AndroidEntryPoint

/**
 * A fragment representing a single Item detail screen.
 * This fragment is either contained in a [CharacterListFragment]
 * in two-pane mode (on larger screen devices) or self-contained
 * on handsets.
 */
@AndroidEntryPoint
class CharacterDetailFragment : Fragment() {

    /**
     * The content this fragment is presenting.
     */
    private var character: UICharacter? = null

    private val characterViewModel: CharacterViewModel by activityViewModels()

    lateinit var characterDetailTextView: TextView
    lateinit var characterNameTextView: TextView
    lateinit var characterImageView: ImageView

    private var _binding: FragmentCharaterDetailBinding? = null

    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            if (it.containsKey(ARG_ITEM_ID)) {
                // Load the placeholder content specified by the fragment
                // arguments. In a real-world scenario, use a Loader
                // to load content from a content provider.
                character = characterViewModel.getCharacterById(it.getString(ARG_ITEM_ID).orEmpty())
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentCharaterDetailBinding.inflate(inflater, container, false)
        val rootView = binding.root

        characterDetailTextView = binding.characterDetail
        characterNameTextView = binding.characterName
        characterImageView = binding.image

        updateContent()

        return rootView
    }

    private fun updateContent() {
        character?.let {
            if (it.icon.isNotEmpty()) {
                characterImageView.load(it.icon)
            } else{
                characterImageView.load("https://i.imgflip.com/7jcrbx.jpg")
            }
            characterNameTextView.text = it.name
            characterDetailTextView.text = it.text
        }
    }

    companion object {
        /**
         * The fragment argument representing the item ID that this fragment
         * represents.
         */
        const val ARG_ITEM_ID = "item_id"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}