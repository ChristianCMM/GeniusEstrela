package com.christiancm.geniusestrela


import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
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

    val possibilidadesLista by lazy {
        listOf(redButton, blueButton, yellowButton, greenButton)
    }
    private val utilitarios = Utilitarios()

    var sequenciaGerada = mutableListOf<Any>()
    var usuarioLista = mutableListOf<Any>()

    lateinit var redButton: Button
    lateinit var greenButton: Button
    lateinit var blueButton: Button
    lateinit var yellowButton: Button

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
        // botão iniciar
        binding.buttonStart.setOnClickListener(this)
        // botões de ação
        binding.buttonBlue.setOnClickListener(this)
        binding.buttonRed.setOnClickListener(this)
        binding.buttonYellow.setOnClickListener(this)
        binding.buttonGreen.setOnClickListener(this)

        yellowButton = binding.buttonYellow
        redButton = binding.buttonRed
        blueButton = binding.buttonBlue
        greenButton = binding.buttonGreen
    }

    override fun onClick(v: View?) {
        // inicia a brilhar as cores
        when (v?.id) {
            R.id.button_start -> {
                if (binding.buttonStart.text == Utilitarios.iniciar) {
                    Utilitarios.ligaDesliga = true
                    sequenciaGerada.clear()
                    usuarioLista.clear()
                    lifecycleScope.launch { ligarBotoes() }
                    binding.buttonStart.text = Utilitarios.parar
                }
                // para de brilhar as cores e chama a função que compara as listas, depois de 5 Sec
                else {
                    Utilitarios.ligaDesliga = false
                    binding.buttonStart.text = Utilitarios.iniciar
                    lifecycleScope.launch {
                        delay(5000L)
                        compararListas()
                    }
                }
            }

            R.id.button_green -> usuarioLista.add(greenButton)
            R.id.button_red -> usuarioLista.add(redButton)
            R.id.button_yellow -> usuarioLista.add(yellowButton)
            R.id.button_blue -> usuarioLista.add(blueButton)
        }
    }

    private suspend fun ligarBotoes() {
        val tempoLigado = 1400L
        // gera um numero aleatorio entre 0 e 3, onde cada numero é uma cor a ser alterada
        while (Utilitarios.ligaDesliga) {
            val numero = Utilitarios.gerarNumeroAleatorio() // 0..3
            when (numero) {
                0 -> {
                    binding.buttonRed.setBackgroundColor(Utilitarios.whiteRed(this))
                    delay(tempoLigado)
                    binding.buttonRed.setBackgroundColor(Utilitarios.redColor(this))
                }

                1 -> {
                    binding.buttonBlue.setBackgroundColor(Utilitarios.whiteBlue(this))
                    delay(tempoLigado)
                    binding.buttonBlue.setBackgroundColor(Utilitarios.blueColor(this))
                }

                2 -> {
                    binding.buttonYellow.setBackgroundColor(Utilitarios.whiteYellow(this))
                    delay(tempoLigado)
                    binding.buttonYellow.setBackgroundColor(Utilitarios.yellowColor(this))
                }

                3 -> {
                    binding.buttonGreen.setBackgroundColor(Utilitarios.whiteGreen(this))
                    delay(tempoLigado)
                    binding.buttonGreen.setBackgroundColor(Utilitarios.greenColor(this))
                }
            }
            sequenciaGerada.add(possibilidadesLista[numero])
        }
    }

    private fun compararListas() {
        if (sequenciaGerada == usuarioLista) {
            Toast.makeText(this, "Sucesso!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Sequência Inválida!", Toast.LENGTH_SHORT).show()
        }
    }
}