package mx.tecnm.tepic.ladm_u1_prctica1_layoutsymslayouts

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import java.io.OutputStreamWriter
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        listamenu.setOnItemClickListener { adapterView, view, posicion, l ->
            //Posición = el que contiene el índice cuando hago click
            when(posicion){
                0->{llamarPrimeraActivity()}
                1->{llamarSegundaActivity()
                }
            }
        }

        salir.setOnClickListener {
            finish()
        }

        guardar3.setOnClickListener {
            if(ValidaCampo(telefono) || ValidaCampo(nombre2)) return@setOnClickListener
            guardarArchivo()
        }
    }
    private fun guardarArchivo() {
        try {
            val archivo = OutputStreamWriter(openFileOutput("archivo.txt", MODE_PRIVATE))
            var cadena = nombre2.text.toString()+"&&"+
                    telefono.text.toString()

            archivo.write(cadena)
            archivo.flush()
            archivo.close()
            telefono.setText("")
            nombre2.setText("")

            AlertDialog.Builder(this).setMessage("SE GUARDARON LOS DATOS").show()
        }catch (e: Exception){
            AlertDialog.Builder(this).setMessage(e.message).show()
        }
    }

    fun llamarPrimeraActivity(){
        val Ventana1 = Intent(this, MainActivity2::class.java)
        startActivity(Ventana1)
    }

    fun llamarSegundaActivity(){
        val Ventana2 = Intent(this, FullscreenActivity::class.java)
        startActivity(Ventana2)
    }

    fun ValidaCampo(text: EditText): Boolean{
        if(telefono.text.isEmpty() || nombre2.text.isEmpty()){
            AlertDialog.Builder(this)
                .setTitle("ERROR")
                .setMessage("RELLENE EL O LOS CAMPOS CORRESPONDIENTES")
                .setNeutralButton("ACEPTAR", {d,i->d.dismiss()})
                .show()
            return true
        }
        return false
    }
}