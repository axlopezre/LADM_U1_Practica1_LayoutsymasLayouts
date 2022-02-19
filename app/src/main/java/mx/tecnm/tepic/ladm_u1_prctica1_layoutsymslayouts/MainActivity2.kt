package mx.tecnm.tepic.ladm_u1_prctica1_layoutsymslayouts

import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main2.*
import mx.tecnm.tepic.ladm_u1_prctica1_layoutsymslayouts.databinding.ActivityMain2Binding
import java.io.*

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    var vector = ArrayList<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main2)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)



        binding.insertar.setOnClickListener {
            if(ValidaCampo(valor)) return@setOnClickListener
            else{
                vector.add(binding.valor.text.toString())
                binding.valor.setText("")
                mostrarlista()
            }
        }
        binding.insertarobservaciones.setOnClickListener {
            if(ValidaCampo2(valor2)) return@setOnClickListener
            guardarDatos()
        }
        binding.regresar.setOnClickListener {
            finish()
        }
        binding.leer.setOnClickListener {
            leerDatos()
        }
    }
    private fun mostrarlista(){
        binding.lista.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, vector)

        binding.lista.setOnItemClickListener { adapterView, view, position, id ->
            val posición = position
            AlertDialog.Builder(this)
                .setTitle("ATENCIÓN")
                .setMessage("ESTAS SEGURO QUE DESEAS BORRAR INDICE")
                .setPositiveButton("OK", {d, i->
                    vector.removeAt(posición)
                    mostrarlista()
                    d.dismiss()
                })
                .setNeutralButton("CANCEL", {d, i->d.cancel()})
                .show()
        }
    }

    private fun guardarDatos(){
        try {
                val datos= OutputStreamWriter(openFileOutput("archivo.txt", MODE_PRIVATE))
                var cadena= binding.valor2.text.toString()+"&&"
                datos.write(cadena)
                datos.flush()
                datos.close()
                binding.valor2.setText("")

                AlertDialog.Builder(this).setMessage("Se enviaron los datos").show()
            }catch (e:Exception){
                AlertDialog.Builder(this).setMessage(e.message).show()
            }
    }

    fun ValidaCampo(text: EditText): Boolean{
        if(valor.text.isEmpty()){
            AlertDialog.Builder(this)
                .setTitle("ERROR")
                .setMessage("RELLENE EL CAMPO CORRESPONDIENTE")
                .setNeutralButton("ACEPTAR", {d,i->d.dismiss()})
                .show()
            return true
        }
        return false
    }
    fun ValidaCampo2(text: EditText): Boolean{
        if(valor2.text.isEmpty()){
            AlertDialog.Builder(this)
                .setTitle("ERROR")
                .setMessage("RELLENE EL CAMPO CORRESPONDIENTE")
                .setNeutralButton("ACEPTAR", {d,i->d.dismiss()})
                .show()
            return true
        }
        return false
    }

    private fun leerDatos(){
        try {
            val archivo= BufferedReader(InputStreamReader(openFileInput("archivo.txt")))
            var cadena =archivo.readLine()

            cadena = cadena.replace("&&", "\n")
            archivo.close()

            AlertDialog.Builder(this).setMessage(cadena).show()
        }catch (e:Exception){
            AlertDialog.Builder(this).setMessage(e.message).show()
        }
    }
}