package com.example.calcnumeric.presenter.fragment.home

import android.animation.LayoutTransition
import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import com.example.calcnumeric.R
import com.example.calcnumeric.databinding.FragmentHomeBinding
import com.example.calcnumeric.domain.entity.Results
import com.example.calcnumeric.domain.entity.Results.Companion.anyData
import com.example.calcnumeric.presenter.fragment.BaseViewModelFragment
import com.example.calcnumeric.presenter.fragment.home.HomeViewModel.ViewData
import com.example.calcnumeric.presenter.utils.NumberFormatter
import com.google.android.material.button.MaterialButton
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseViewModelFragment<FragmentHomeBinding, ViewData, HomeViewModel>() {

    override val viewModel: HomeViewModel by viewModels()

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentHomeBinding
        get() = FragmentHomeBinding::inflate

    private var result: String
        get() = binding.resultDisplay.text.toString()
        set(value) {
            binding.resultDisplay.setText(value)
        }

    private var input: String
        get() = binding.input.text.toString()
        set(value) {
            binding.input.setText(value)
        }

    private var lastPressedView: View? = null
    private var lastCommand: String = ""

    @SuppressLint("SetTextI18n")
    override fun initializeView() {
        initSwitchMenu()

        with(binding) {
            mapOf(
                btn0 to "0",
                btn1 to "1",
                btn2 to "2",
                btn3 to "3",
                btn4 to "4",
                btn5 to "5",
                btn6 to "6",
                btn7 to "7",
                btn8 to "8",
                btn9 to "9",
                btnSeparator to ".",
                btnAdd to "+",
                btnSubtract to "-",
                btnMultiply to "*",
                btnDivide to "/",
                btnParenthesesOpen to "(",
                btnParenthesesClose to ")",
                btnPercent to "%",
                btnSin to "sin(",
                btnCos to "cos(",
                btnTan to "tan(",
                btnLn to "ln(",
                btnLog to "log(",
                btnPow to "^",
                btnSquare to "sqrt(",
                btnPi to "pi",
                btnFactorial to "fact(",
                btnE to "e",
            )
        }.forEach { (view, value) ->
            view.setOnClickListener {
                lastCommand = value
                val expression =
                    if (lastPressedView == binding.btnEqual && value.last().isDigit()) value
                    else input + value
                input = expression
                viewModel.calculate(expression)
                lastPressedView = it
            }
        }

        binding.btnBackspace.setOnClickListener {
            lastPressedView = it
            val lastCommand = lastCommand
            val expression = input.dropLast(lastCommand.length)
            input = expression
            viewModel.calculate(expression)
        }

        binding.btnBackspace.setOnLongClickListener {
            lastPressedView = it
            input = ""
            viewModel.calculate("")
            true
        }

        binding.btnEqual.setOnClickListener { it ->
            lastPressedView = it
            if (result.isNotEmpty()) {
                viewModel.calculate(input, true)
                input = result
                result = ""
            }
        }
    }

    override fun render(data: ViewData) {
        if (data.result is Results.Failure && lastPressedView == binding.btnEqual) {
            input = data.expression
            result = Double.NaN.toString()
            Toast.makeText(
                context,
                data.result.throwable.message,
                Toast.LENGTH_SHORT
            ).show()
        } else {
            data.result.anyData()?.let { updateResult(it) }
        }
    }

    private fun updateResult(result: Number) {
        this.result = NumberFormatter.format(result)
    }

    private fun initSwitchMenu() {
        binding.tableLayout.layoutTransition = LayoutTransition().apply {
            disableTransitionType(LayoutTransition.DISAPPEARING)
        }

        binding.btnSwitchAdditional.setOnClickListener {
            if (it is MaterialButton) {
                if (binding.menuAdditional.isVisible) {
                    binding.menuAdditional.visibility = View.GONE
                    it.setIconResource(R.drawable.ic_arrow_up_24dp)
                } else {
                    binding.menuAdditional.visibility = View.VISIBLE
                    it.setIconResource(R.drawable.ic_arrow_down_24dp)
                }
            }
        }
    }
}