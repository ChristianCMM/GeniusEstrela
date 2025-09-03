package com.christiancm.geniusestrela


import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.christiancm.geniusestrela.databinding.ActivityMainBinding
import com.christiancm.geniusestrela.utils.Utilitarios
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlin.text.get

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private val utilitarios = Utilitarios()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding.buttonStart.setOnClickListener(this)
    }

    var ligaDesliga = true
    // altera o
    override fun onClick(v: View?) {
        if (binding.buttonStart.text == "Start"){
            ligaDesliga = true
            lifecycleScope.launch {
                ligarBotoes()
            }
            binding.buttonStart.text = "Stop"
        }
        else{
            ligaDesliga = false
            binding.buttonStart.text = "Start"
        }
    }

    private suspend fun ligarBotoes() {
        val tempoLigado = 1400L
        // gera um numero aleatorio entre 0 e 3, onde cada numero é uma cor a ser alterada
        while (ligaDesliga) {
            val numero = utilitarios.gerarNumeroAleatorio() // 0..3
            when (numero) {
                0 -> {
                    binding.viewRed.setBackgroundColor(Utilitarios.whiteRed(this))
                    delay(tempoLigado)
                    binding.viewRed.setBackgroundColor(Utilitarios.redColor(this))
                }

                1 -> {
                    binding.viewBlue.setBackgroundColor(Utilitarios.whiteBlue(this))
                    delay(tempoLigado)
                    binding.viewBlue.setBackgroundColor(Utilitarios.blueColor(this))
                }

                2 -> {
                    binding.viewYellow.setBackgroundColor(Utilitarios.whiteYellow(this))
                    delay(tempoLigado)
                    binding.viewYellow.setBackgroundColor(Utilitarios.yellowColor(this))
                }

                3 -> {
                    binding.viewGreen.setBackgroundColor(Utilitarios.whiteGreen(this))
                    delay(tempoLigado)
                    binding.viewGreen.setBackgroundColor(Utilitarios.greenColor(this))
                }
            }
        }
    }
}