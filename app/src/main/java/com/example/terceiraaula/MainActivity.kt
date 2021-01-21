package com.example.terceiraaula

import android.content.ClipData
import android.content.ClipboardManager
import android.content.SharedPreferences
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SeekBar
import android.widget.TextView
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // salvar os valores da ultima cor gerada para fechar o app e deixar a ultima cor em exibicao
        val R: SharedPreferences = getSharedPreferences("R",0)
        val G: SharedPreferences = getSharedPreferences("G",0)
        val B: SharedPreferences = getSharedPreferences("B",0)

        // usar os valores salvos pela sharedpreferences e reutilizar na abertura do app
        textRed.text = R.getString("R","0")
        textGreen.text = G.getString("G","0")
        textBlue.text = B.getString("B","0")
        paintRGB()

        // ao deslizar a barra vermelha, muda o valor no textview do vermelho
        seekBarRed.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    // pegar o valor da barra
                    var red = "$progress"

                    // passar para a tela em texto
                    textRed.text = red

                    // guardar o valor na sharedpreferences
                    R.edit().putString("R",red).apply()

                    // chamar a funcao para mostrar a cor
                    paintRGB()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            },
        )

        // ao deslizar a barra verde, muda o valor no textview do verde
        seekBarGreen.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    var green = "$progress"
                    textGreen.text = green
                    G.edit().putString("G",green).apply()
                    paintRGB()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            },
        )

        // ao deslizar a barra azul, muda o valor no textview do azul
        seekBarBlue.setOnSeekBarChangeListener(
            object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                    var blue = "$progress"
                    textBlue.text = blue
                    B.edit().putString("B",blue).apply()
                    paintRGB()
                }
                override fun onStartTrackingTouch(seekBar: SeekBar?) {}
                override fun onStopTrackingTouch(seekBar: SeekBar?) {}
            },
        )

        // botao para copiar o codigo RGB
        buttonRGB.setOnClickListener {
            var codRGB:String = "(" + textRed.text.toString() + "," + textGreen.text.toString() + "," + textBlue.text.toString() + ")"
            copiaCodigo(codRGB)
        }

        // botao para copiar o codigo hexadecimal
        buttonHEX.setOnClickListener {
            copiaCodigo(textHEX.text.toString())
        }
    }

    // nessa funcao, recebe-se os valores de RGB dos textviews
    // e depois exibe a cor pronta na tela
    private fun paintRGB(){

        //recebendo os valores dos textviews de RGB e passando para valor inteiro
        val getRed:TextView = findViewById(R.id.textRed) as TextView
        var red:Int = getRed.text.toString().toInt()

        val getGreen:TextView = findViewById(R.id.textGreen) as TextView
        var green:Int = getGreen.text.toString().toInt()

        val getBlue:TextView = findViewById(R.id.textBlue) as TextView
        var blue:Int = getBlue.text.toString().toInt()

        // realizar a conversao de inteiros para hexadecimal
        val colorHex = String.format("#%02x%02x%02x",red,green,blue)

        // exibir o codigo hexadecimal na tela
        textHEX.text = colorHex

        // pintar na tela a cor ja convertida para hexadecimal
        layout.setBackgroundColor(Color.parseColor(colorHex))
    }

    // funcao que copia o codigo para a area de transferencia
    private fun copiaCodigo(codigo:String){
        val clipboard = getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("codigo", codigo)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(this,"Codigo copiado!",Toast.LENGTH_SHORT).show()
    }
}