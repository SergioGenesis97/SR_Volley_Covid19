package mx.udg.alumnos.sr_volley

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.Volley
import mx.udg.alumnos.sr_volley.Adapter.PaisesAdapter
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    lateinit var AdaptadorPaises:PaisesAdapter
    lateinit var miRecyclerCovid:RecyclerView
    lateinit var arrayListaPaises:ArrayList<Pais>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        miRecyclerCovid = findViewById(R.id.miRecyclerCovid)

        arrayListaPaises = ArrayList<Pais>()

        AdaptadorPaises = PaisesAdapter(arrayListaPaises!!, this)

        //listaPaises.add(Pais("Mexico", 23, 0, 1))

        miRecyclerCovid.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        miRecyclerCovid.adapter = PaisesAdapter(arrayListaPaises, this)

        obtenerDatos()

    }

    fun obtenerDatos(){


        val queue = Volley.newRequestQueue(this)
        val url = "https://wuhan-coronavirus-api.laeyoung.endpoint.ainize.ai/jhu-edu/latest"

        val peticionDatosCovid = JsonArrayRequest(Request.Method.GET,
            url,
            null,
        Response.Listener { response ->
            Log.d("  Respuesta: ", response.toString())

            try {
                for (index in 0..response.length()-1){
                    val paisJSON = response.getJSONObject(index)

                    val nombrePais = paisJSON.getString("country")
                    val numConfirmados = paisJSON.getInt("confirmed")
                    val numMuertos = paisJSON.getInt("deaths")
                    val numRecuperados = paisJSON.getInt("recovered")
                    val countryCodeJson = paisJSON.getJSONObject("countrycode")
                    val codigoPais = countryCodeJson.getString("iso2")

                    val paisIndividual = Pais(nombrePais, numConfirmados, numMuertos, numRecuperados, codigoPais)
                    arrayListaPaises!!.add(paisIndividual)
                }
            }
            catch (e:JSONException){
                Log.wtf("   Error JSON: ", e.localizedMessage)
            }

            arrayListaPaises!!.sortByDescending { it.confirmados }

            AdaptadorPaises!!.notifyDataSetChanged()

        },
        Response.ErrorListener { error ->  
            Log.d("  Error_Vollye: ", error.localizedMessage)
        })

        queue.add(peticionDatosCovid)
    }


}