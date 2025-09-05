package com.christiancm.geniusestrela.utils

import android.content.Context
import androidx.core.content.ContextCompat
import com.christiancm.geniusestrela.R
import kotlinx.coroutines.delay

class Utilitarios {



    companion object {

        var ligaDesliga = false

        val iniciar = "Start"
        val parar = "Stop"

        suspend fun gerarNumeroAleatorio():Int{
            delay(1300L)
            val numero = (0..3).random()
            return numero
        }

        fun whiteRed(context: Context): Int {
            return ContextCompat.getColor(context, R.color.white_red)
        }

        fun whiteBlue(context: Context): Int {
            return ContextCompat.getColor(context, R.color.white_blue)
        }

        fun whiteGreen(context: Context): Int {
            return ContextCompat.getColor(context, R.color.white_green)
        }

        fun whiteYellow(context: Context):Int{
            return ContextCompat.getColor(context, R.color.white_yellow)
        }


        fun redColor(context: Context): Int {
            return ContextCompat.getColor(context, R.color.red)
        }

        fun blueColor(context: Context): Int {
            return ContextCompat.getColor(context, R.color.blue)
        }

        fun greenColor(context: Context): Int {
            return ContextCompat.getColor(context, R.color.green)
        }

        fun yellowColor(context: Context):Int{
            return ContextCompat.getColor(context, R.color.yellow)
        }
    }
}
