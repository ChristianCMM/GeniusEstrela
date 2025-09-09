package com.christiancm.geniusestrela.ui

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CompoundButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.christiancm.geniusestrela.R
import com.christiancm.geniusestrela.databinding.ActivityMainBinding
import com.christiancm.geniusestrela.utils.Utilitarios
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds
import kotlin.time.Duration.Companion.seconds

class MainActivity : AppCompatActivity(),
    View.OnClickListener,
    CompoundButton.OnCheckedChangeListener {
    lateinit var binding: ActivityMainBinding

    var contador = 0
    var repeticoes = 0

    var dificuldade: Duration = 10.seconds
    lateinit var redButton: Button
    lateinit var greenButton: Button
    lateinit var blueButton: Button
    lateinit var yellowButton: Button
    val possibilidadesLista by lazy {
        listOf(redButton,blueButton,yellowButton,greenButton)
    }
    var sequenciaGerada = mutableListOf<Int>()
    var usuarioLista = mutableListOf<Int>()



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        enableEdgeToEdge()
        setContentView(binding.root)
        redButton = binding.buttonRed
        greenButton = binding.buttonGreen
        blueButton = binding.buttonBlue
        yellowButton = binding.buttonYellow
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
        // Checkbox de nivel
        binding.checkboxLvl1.setOnCheckedChangeListener(this)
        binding.checkboxLvl2.setOnCheckedChangeListener(this)
        binding.checkboxLvl3.setOnCheckedChangeListener(this)


    }


    override fun onClick(v: View?) {
        // inicia a brilhar as cores
        when (v?.id) {
            R.id.button_start -> {
                if (binding.buttonStart.text == Utilitarios.Companion.iniciar) {
                    Utilitarios.ligaDesliga = true
                    lifecycleScope.launch {
                        ligarBotoes()
                        sequenciaGerada.clear()
                        usuarioLista.clear()
                        withTimeoutOrNull(dificuldade) {
                            while (usuarioLista.size < sequenciaGerada.size && isActive) {
                                delay(50.milliseconds)
                            }
                        }
                        compararListas()
                    }
                    binding.buttonStart.text = Utilitarios.Companion.parar
                }
                // para de brilhar as cores e chama a função que compara as listas, depois de 5 Sec
                else {
                    Utilitarios.Companion.ligaDesliga = false
                    binding.buttonStart.text = Utilitarios.Companion.iniciar
                    //lifecycleScope.launch {}
                }
            }

            R.id.button_green -> usuarioLista.add(greenButton.id)
            R.id.button_red -> usuarioLista.add(redButton.id)
            R.id.button_yellow -> usuarioLista.add(yellowButton.id)
            R.id.button_blue -> usuarioLista.add(blueButton.id)
        }
    }

    private suspend fun ligarBotoes() {
        val tempoLigado: Duration = 1.seconds
        // gera um numero aleatorio entre 0 e 3, onde cada numero é uma cor a ser alterada
        while (usuarioLista.size < sequenciaGerada.size && Utilitarios.ligaDesliga) {
            val numero = Utilitarios.Companion.gerarNumeroAleatorio() // 0..3
            when (numero) {
                0 -> {
                    binding.buttonRed.setBackgroundColor(Utilitarios.Companion.whiteRed(this))
                    delay(tempoLigado)
                    binding.buttonRed.setBackgroundColor(Utilitarios.Companion.redColor(this))
                }

                1 -> {
                    binding.buttonBlue.setBackgroundColor(Utilitarios.Companion.whiteBlue(this))
                    delay(tempoLigado)
                    binding.buttonBlue.setBackgroundColor(Utilitarios.Companion.blueColor(this))
                }

                2 -> {
                    binding.buttonYellow.setBackgroundColor(Utilitarios.Companion.whiteYellow(this))
                    delay(tempoLigado)
                    binding.buttonYellow.setBackgroundColor(Utilitarios.Companion.yellowColor(this))
                }

                3 -> {
                    binding.buttonGreen.setBackgroundColor(Utilitarios.Companion.whiteGreen(this))
                    delay(tempoLigado)
                    binding.buttonGreen.setBackgroundColor(Utilitarios.Companion.greenColor(this))
                }
            }
            sequenciaGerada.add(possibilidadesLista[numero].id)
            contador++
        }
    }

    private suspend fun compararListas() {
        if (sequenciaGerada == usuarioLista) {
            Toast.makeText(this, "Sequência Correta!", Toast.LENGTH_SHORT).show()
            repeticoes++
            ligarBotoes()
        } else {
            Toast.makeText(this, "Sequência Inválida!", Toast.LENGTH_SHORT).show()
            Utilitarios.ligaDesliga = false
        }
    }

    override fun onCheckedChanged(
        buttonView: CompoundButton,
        isChecked: Boolean
    ) {
        when (buttonView.id) {
            binding.checkboxLvl1.id -> {
                if (isChecked) {
                    dificuldade = 10.seconds
                    binding.checkboxLvl2.isChecked = false
                    binding.checkboxLvl3.isChecked = false
                }
            }

            binding.checkboxLvl2.id -> {
                if (isChecked) {
                    dificuldade = 8.seconds
                    binding.checkboxLvl1.isChecked = false
                    binding.checkboxLvl3.isChecked = false
                }
            }

            binding.checkboxLvl3.id -> {
                if (isChecked) {
                    dificuldade = 5.seconds
                    binding.checkboxLvl1.isChecked = false
                    binding.checkboxLvl2.isChecked = false
                }
            }
        }
    }
}