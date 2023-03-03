package com.vladimirorlov9.cryptocurrency.utils.customview

import android.content.Context
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.vladimirorlov9.cryptocurrency.R

class NumpadView : ConstraintLayout {

    private val _textLD = MutableLiveData("0.0")
    val textLD: LiveData<String> = _textLD

    private var isDotPrinted: Boolean = false

    private lateinit var num1Button: AppCompatButton
    private lateinit var num2Button: AppCompatButton
    private lateinit var num3Button: AppCompatButton
    private lateinit var num4Button: AppCompatButton
    private lateinit var num5Button: AppCompatButton
    private lateinit var num6Button: AppCompatButton
    private lateinit var num7Button: AppCompatButton
    private lateinit var num8Button: AppCompatButton
    private lateinit var num9Button: AppCompatButton
    private lateinit var num0Button: AppCompatButton
    private lateinit var dotButton: AppCompatButton
    private lateinit var backspaceButton: AppCompatImageButton

    constructor(context: Context) : super(context)
    constructor(context: Context, attrSet: AttributeSet) : super(context, attrSet)
    constructor(context: Context, attrSet: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrSet,
        defStyleAttr
    )


    init {
        val view = View.inflate(context, R.layout.layout_numpad, this)

        initButtons(view)
        num0Button.setOnClickListener {
            printNewNum(0)
        }
        num1Button.setOnClickListener {
            printNewNum(1)
        }
        num2Button.setOnClickListener {
            printNewNum(2)
        }
        num3Button.setOnClickListener {
            printNewNum(3)
        }
        num4Button.setOnClickListener {
            printNewNum(4)
        }
        num5Button.setOnClickListener {
            printNewNum(5)
        }
        num6Button.setOnClickListener {
            printNewNum(6)
        }
        num7Button.setOnClickListener {
            printNewNum(7)
        }
        num8Button.setOnClickListener {
            printNewNum(8)
        }
        num9Button.setOnClickListener {
            printNewNum(9)
        }
        dotButton.setOnClickListener {
            isDotPrinted = true
        }
        backspaceButton.setOnClickListener {
            removeSymbol()
        }
    }

    private var lastNumZero: Boolean = false

    private fun removeSymbol() {
        val text = textLD.value!!
        val leftPart = text.substringBefore(".")
        val rightPart = text.substringAfter(".")

        if (isDotPrinted) {
            if (rightPart == "0" && lastNumZero)
                lastNumZero = false
            else if (rightPart == "0")
                isDotPrinted = false
            else {
                val rightPartEdited = if (rightPart.length > 1) rightPart.dropLast(1) else "0"
                _textLD.value = "$leftPart.$rightPartEdited"
            }
        } else {
            val leftPartEdited = if (leftPart.length > 1) leftPart.dropLast(1) else "0"
            _textLD.value = "$leftPartEdited.$rightPart"
        }
    }


    private fun printNewNum(num: Int) {
        val text = textLD.value!!
        val leftPart = text.substringBefore(".")
        val rightPart = text.substringAfter(".")

        _textLD.value = if (!isDotPrinted) {
            val leftPartStr = if (leftPart == "0") "" else leftPart
            val newLeftPartStr = leftPartStr + num
            "$newLeftPartStr.$rightPart"
        } else {
            val rightPartStr =
                if (rightPart == "0" && lastNumZero) "0" else if (rightPart == "0") {
                    if (num == 0)
                        lastNumZero = true
                    ""
                } else rightPart
            val newRightPartStr = rightPartStr + num
            "$leftPart.$newRightPartStr"
        }
    }

    private fun initButtons(view: View) {
        num0Button = view.findViewById(R.id.num_0)
        num1Button = view.findViewById(R.id.num_1)
        num2Button = view.findViewById(R.id.num_2)
        num3Button = view.findViewById(R.id.num_3)
        num4Button = view.findViewById(R.id.num_4)
        num5Button = view.findViewById(R.id.num_5)
        num6Button = view.findViewById(R.id.num_6)
        num7Button = view.findViewById(R.id.num_7)
        num8Button = view.findViewById(R.id.num_8)
        num9Button = view.findViewById(R.id.num_9)
        dotButton = view.findViewById(R.id.num_dot)
        backspaceButton = view.findViewById(R.id.remove_btn)
    }

}