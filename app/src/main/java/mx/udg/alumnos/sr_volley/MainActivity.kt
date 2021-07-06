package mx.udg.alumnos.sr_volley

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import mx.udg.alumnos.sr_volley.Adapter.PaisesAdapter

class MainActivity : AppCompatActivity() {

    lateinit var miRecyclerCovid:RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        miRecyclerCovid = findViewById(R.id.miRecyclerCovid)

        val queue = Volley.newRequestQueue(this)
        val url = "https://wuhan-coronavirus-api.laeyoung.endpoint.ainize.ai/jhu-edu/latest"

        val listaPaises = ArrayList<Pais>()

        listaPaises.add(Pais("Mexico", 23, 0, 1))

        miRecyclerCovid.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        miRecyclerCovid.adapter = PaisesAdapter(listaPaises, this)


    }
}