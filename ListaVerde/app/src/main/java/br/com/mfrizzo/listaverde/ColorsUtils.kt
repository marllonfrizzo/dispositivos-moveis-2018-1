package br.com.mfrizzo.listaverde

import android.content.Context
import android.graphics.Color
import android.support.v4.content.ContextCompat

class ColorUtils {

    companion object {

        fun getViewHolderBackgroundColor(context: Context, instanceNum: Int): Int {
            when (instanceNum) {
                0 -> return ContextCompat.getColor(context, R.color.material50Green)
                1 -> return ContextCompat.getColor(context, R.color.material100Green)
                2 -> return ContextCompat.getColor(context, R.color.material150Green)
                3 -> return ContextCompat.getColor(context, R.color.material200Green)
                4 -> return ContextCompat.getColor(context, R.color.material250Green)
                5 -> return ContextCompat.getColor(context, R.color.material300Green)
                6 -> return ContextCompat.getColor(context, R.color.material350Green)
                7 -> return ContextCompat.getColor(context, R.color.material400Green)
                8 -> return ContextCompat.getColor(context, R.color.material450Green)
                9 -> return ContextCompat.getColor(context, R.color.material500Green)
                10 -> return ContextCompat.getColor(context, R.color.material550Green)
                11 -> return ContextCompat.getColor(context, R.color.material600Green)
                12 -> return ContextCompat.getColor(context, R.color.material650Green)
                13 -> return ContextCompat.getColor(context, R.color.material700Green)
                14 -> return ContextCompat.getColor(context, R.color.material750Green)
                15 -> return ContextCompat.getColor(context, R.color.material800Green)
                16 -> return ContextCompat.getColor(context, R.color.material850Green)
                17 -> return ContextCompat.getColor(context, R.color.material900Green)

                else -> return Color.WHITE
            }
        }

    }
}