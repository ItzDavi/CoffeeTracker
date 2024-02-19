package com.d.coffeetracker.fragments

import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.d.coffeetracker.MainActivity
import com.d.coffeetracker.R
import com.d.coffeetracker.arch.CoffeeSizes
import com.d.coffeetracker.arch.CoffeeVM
import com.d.coffeetracker.arch.Constants
import com.d.coffeetracker.utils.MyUtils.customGetSerializable

class AddCoffeeDialogFragment() : DialogFragment() {

    private var size: CoffeeSizes? = null

    private val viewModel: CoffeeVM by viewModels()

    companion object {
        @JvmStatic
        fun newInstance(size: CoffeeSizes) =
            AddCoffeeDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable(Constants.ADD_COFFEE_ARGS_KEY, size)
                }
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        arguments?.let {
            size = it.customGetSerializable<CoffeeSizes>(Constants.ADD_COFFEE_ARGS_KEY)
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_fragment_add_coffee, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialog?.setCancelable(true)

        val context = requireContext()

        viewModel.init(context)

        var titleString: String
        var imageResource = 0
        var messageString = ""

        size?.let {
            when (it) {
                CoffeeSizes.SMALL -> {
                    titleString = "Add a small cup"
                    imageResource = R.drawable.small_cup
                    messageString = "Press the button to add a small coffee cup"
                }
                CoffeeSizes.MEDIUM -> {
                    titleString = "Add a medium cup"
                    imageResource = R.drawable.grande_cup
                    messageString = "Press the button to add a medium coffee cup"
                }
                CoffeeSizes.GRANDE -> {
                    titleString = "Add a grande cup"
                    imageResource = R.drawable.medium_cup
                    messageString = "Press the button to add a grande coffee cup"
                }
            }

            with (view) {
                findViewById<TextView>(R.id.dialog_title).apply {
                    text = titleString
                }

                findViewById<ImageView>(R.id.dialog_image).apply {
                    setImageResource(imageResource)
                }

                findViewById<TextView>(R.id.dialog_message).apply {
                    text = messageString
                }

                findViewById<AppCompatButton>(R.id.dialog_button).setOnClickListener {
                    size?.let { s ->
                        viewModel.addCoffee(context, s)

                        dialog?.cancel()
                    }
                }
            }
        }
    }

    override fun onCancel(dialog: DialogInterface) {
        (activity as MainActivity).apply {
            showingDialog = false
            resume()
        }
        super.onCancel(dialog)
    }
}