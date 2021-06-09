package com.example.studyapp.ui.addcard

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.ui.text.capitalize
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.bravepaw.myapplication.ui.addcard.AddCardViewModel
import com.example.studyapp.Util.closeKeyBoard
import com.example.studyapp.databinding.FragmentAddCardBinding
import com.example.studyapp.realm.RealmHelper.addCardToRealm
import com.example.studyapp.realm.models.Trivia
import com.google.mlkit.nl.languageid.LanguageIdentification
import java.util.*

class AddCardFragment : Fragment() {

    private lateinit var addCardViewModel: AddCardViewModel
    private var _binding: FragmentAddCardBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        addCardViewModel =
            ViewModelProvider(this).get(AddCardViewModel::class.java)

        _binding = FragmentAddCardBinding.inflate(inflater, container, false)
        val root: View = binding.root

        setOnClickListeners()

        return root
    }

    private fun setOnClickListeners() {

        binding.submitButton.setOnClickListener {

            closeKeyBoard(requireContext())
            addCard()
        }

        binding.buttonConfirmSubmit.setOnClickListener {

            addTrivia()

            if(addCardToRealm(addCardViewModel.languageList)){
                addCardViewModel.languageList.clear()
            } else {
                Toast.makeText(context, "Failed to add card!", Toast.LENGTH_LONG).show()
            }
        }

        binding.buttonAddLanguage.setOnClickListener {
            addTrivia()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun addTrivia(){
        addCardViewModel.languageList.add(
            Trivia(
                binding.editTextQuestion.text.toString(),
                binding.editTextAnswer.text.toString(),
                binding.languageText.text.toString().removePrefix("Language: ")
            )
        )

        binding.editTextQuestion.text.clear()
        binding.editTextAnswer.text.clear()

        binding.dialogContainer.visibility = View.GONE
    }

    private fun validateInput(): Boolean = when {
        // zero-length fields are not valid, so prevent users from creating input.
        binding.editTextAnswer.text.toString().isEmpty() -> false
        binding.editTextQuestion.text.toString().isEmpty() -> false
        else -> true
    }

    fun addCard() {

        if (!validateInput()) {
            Toast.makeText(requireContext(), "No Empty Fields Allowed", Toast.LENGTH_LONG).show()
        } else {

            submitOrAddLanguage()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun submitOrAddLanguage() {

        val languageIdentifier = LanguageIdentification.getClient()
        Log.e("LANG", "LANG: "+ binding.editTextAnswer.text.toString() + " " + binding.editTextQuestion.text.toString())
        languageIdentifier.identifyLanguage(binding.editTextAnswer.text.toString() + " " + binding.editTextQuestion.text.toString())
            .addOnSuccessListener { languageCode ->
                if (languageCode == "und") {
                    Log.i("Language", "Can't identify language.")
                    binding.languageText.text = "Can't identify language."
                } else {
                    Log.i("Language", "Language: $languageCode")

                    // get language name from code
                    val loc = Locale(languageCode);
                    val language = loc.getDisplayLanguage(loc)
                        .replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }

                    Log.i("Language", "Language: $language")
                    binding.languageText.text = "Language: $language"
                }
            }
            .addOnFailureListener {
                // Model couldn't be loaded or other internal error.
                binding.languageText.text = "Can't identify language."
            }

        binding.dialogContainer.visibility = View.VISIBLE
    }
}