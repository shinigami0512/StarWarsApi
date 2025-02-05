package com.example.starwarsapi;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class ElementosRecycler extends AppCompatActivity {

    private Adaptador adaptador;
    private ImageView btnSiguientePagina;
    private ImageView btnRetrocederPagina;

    private int contador = 2;//Contador para determinar la siguiente pagina

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_elementos_recycler);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        //Recibo los argumentos
        Intent intent = getIntent();
        String busqueda = intent.getStringExtra("busqueda");
        String elementos = intent.getStringExtra("elementos");

        TextView txtTitulo = findViewById(R.id.txtTitulo);
        txtTitulo.setText(busqueda.toUpperCase());

        btnSiguientePagina = findViewById(R.id.btnSiguientePagina);
        btnRetrocederPagina = findViewById(R.id.btnRetrocederPagina);

        btnSiguientePagina.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnRetrocederPagina.setVisibility(View.VISIBLE);//Muestro el botón de retroceder

                String url = "https://swapi.dev/api/" + busqueda + "/?page=" + contador;
                buscarElementos(url, elementos);
                contador++;//Aumento el contador para que vaya a la siguiente pagina
            }
        });

        btnRetrocederPagina.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                contador--;//Resta el contador para retroceder

                if(contador == 2){//Si el contador es igual a 2 significa que se está en la primera página

                    String url = "https://swapi.dev/api/" + busqueda + "/";
                    buscarElementos(url, elementos);
                    btnRetrocederPagina.setVisibility(View.INVISIBLE);//Oculto el botón de retroceder

                }else{

                    String url = "https://swapi.dev/api/" + busqueda + "/?page=" + contador;
                    buscarElementos(url, elementos);
                }
            }
        });

        //Muestra los elementos de la primera página al acceder a esta actividad
        String url = "https://swapi.dev/api/" + busqueda + "/";
        buscarElementos(url, elementos);
    }

    /**
     * Muestra los datos del campo results de la página específicada de la API
     * y los añade a l recyclerview
     *
     * @param url String de donde se van a buscar los datos
     * @param elementos String con los nombres de los campos a mostrar separados por guiones bajos
     */
    public void buscarElementos(String url, String elementos) {

        RecyclerView recyclerElementos = findViewById(R.id.recyclerView);

        RequestQueue cola = Volley.newRequestQueue(getBaseContext());

        //Creo la petición
        JsonObjectRequest peticion = new JsonObjectRequest(

                url,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        try {

                            JSONArray resultados = response.getJSONArray("results");

                            String next = response.getString("next");

                            //Si el campo next es igual a null significa que no hay página siguiente
                            if(next.equals("null")){

                                btnSiguientePagina.setVisibility(View.INVISIBLE);//Oculto el botón de siguiente

                            }else{

                                btnSiguientePagina.setVisibility(View.VISIBLE);
                            }

                            ArrayList<Elemento> listaElementos = new ArrayList<>();

                            for (int i = 0; i < resultados.length(); i++) {

                                JSONObject objetoVideo = resultados.getJSONObject(i);

                                //Separo el String de elementos en un array para poder acceder a los tres campos
                                String[] elementosArray = elementos.split("_");

                                String dato1 = objetoVideo.getString(elementosArray[0]);
                                String dato2 = elementosArray[1] +": "+ objetoVideo.getString(elementosArray[1]);
                                String dato3 = elementosArray[2] +": "+ objetoVideo.getString(elementosArray[2]);

                                Elemento elemento = new Elemento(dato1, dato2, dato3);
                                listaElementos.add(elemento);
                            }

                            adaptador = new Adaptador(listaElementos);
                            recyclerElementos.setLayoutManager(new LinearLayoutManager(getBaseContext()));
                            recyclerElementos.setAdapter(adaptador);

                        } catch (JSONException e) {

                            throw new RuntimeException(e);
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );

        //Se añade la peticion a la cola
        cola.add(peticion);
    }
}