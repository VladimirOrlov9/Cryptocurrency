package com.vladimirorlov9.cryptocurrency.ui.coinpage

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.vladimirorlov9.cryptocurrency.R

class GraphicView(context: Context,
                  attributeSet: AttributeSet
): View(context, attributeSet) {

    private val dataset = mutableListOf<Double>()

    private var yMin = 0.0
    private var yMax = 0.0

    private val dataPointLinePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = context.resources.getColor(R.color.graphic_color, null)
        strokeWidth = 6f
        strokeCap = Paint.Cap.ROUND
    }

    fun setData(newDataSet: List<Double>) {
        yMin = newDataSet.minOrNull() ?: 0.0
        yMax = newDataSet.maxOrNull() ?: 0.0
        dataset.clear()
        dataset.addAll(newDataSet)
        invalidate()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        if (dataset.isNotEmpty()) {
            val xPart = width.toDouble() / (dataset.size - 1)
            dataset.forEachIndexed { index, currentDataPoint ->
                if (index < dataset.size - 1) {
                    val nextDataPoint = dataset[index + 1]
                    val startX = (index * xPart).toFloat()
                    val startY = currentDataPoint.toRealY()
                    val endX = ((index + 1) * xPart).toFloat()
                    val endY = nextDataPoint.toRealY()
                    canvas.drawLine(startX, startY, endX, endY, dataPointLinePaint)
                }
            }
        }
    }

    private fun Double.toRealY() = (height + 7 - (this / yMax * height)).toFloat()
}