package ci.nsu.moble.main.ui.main

import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import ci.nsu.moble.main.R

class MainFragment : Fragment() {

    companion object {
        private const val TAG = "MainFragment"
        fun newInstance() = MainFragment()
    }

    private val viewModel: MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var colorsContainer: LinearLayout
    private lateinit var searchInput: com.google.android.material.textfield.TextInputEditText
    private lateinit var applyButton: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        colorsContainer = view.findViewById(R.id.colorsContainer)
        searchInput = view.findViewById(R.id.searchInput)
        applyButton = view.findViewById(R.id.applyButton)

        showColorPalette()

        applyButton.setOnClickListener {
            val query = searchInput.text.toString().trim()
            if (query.isEmpty()) {
                Log.d(TAG, "Пустой запрос")
                return@setOnClickListener
            }

            val color = viewModel.availableColors[query]
            if (color != null) {
                applyButton.background = GradientDrawable().apply {
                    setColor(color)
                    cornerRadius = 16f
                }
                Log.d(TAG, "Цвет '$query' найден и применён к кнопке")
            } else {
                Log.d(TAG, "Цвет '$query' не найден")
            }
        }
    }

    private fun showColorPalette() {
        colorsContainer.removeAllViews()
        viewModel.availableColors.forEach { (name, color) ->
            val textView = TextView(requireContext()).apply {
                text = name
                setTextColor(Color.WHITE)
                textSize = 18f
                gravity = android.view.Gravity.CENTER_VERTICAL or android.view.Gravity.START
                setPadding(16, 16, 16, 16)
                layoutParams = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                ).apply {
                    setMargins(0, 8, 0, 8)
                }

                background = GradientDrawable().apply {
                    setColor(color)
                    cornerRadius = 16f
                }
            }
            colorsContainer.addView(textView)
        }
    }
}