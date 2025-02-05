package com.example.starwarsapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class Seleccion extends AppCompatActivity {

    ImageView btnPersonajes;
    ImageView btnNaves;
    ImageView btnPlanetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_principal);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        btnPersonajes = findViewById(R.id.btnPersonajes);
        btnNaves = findViewById(R.id.btnNaves);
        btnPlanetas = findViewById(R.id.btnPlanetas);

        //Muestra a los personajes
        btnPersonajes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), ElementosRecycler.class);
                intent.putExtra("busqueda", "people");
                intent.putExtra("elementos", "name_height_gender");//Campos a buscar en el json
                startActivity(intent);
            }
        });

        //Muestra a las naves
        btnNaves.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), ElementosRecycler.class);
                intent.putExtra("busqueda", "starships");
                intent.putExtra("elementos", "name_model_manufacturer");
                startActivity(intent);
            }
        });

        //Muestra a los planetas
        btnPlanetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getBaseContext(), ElementosRecycler.class);
                intent.putExtra("busqueda", "planets");
                intent.putExtra("elementos", "name_diameter_climate");
                startActivity(intent);
            }
        });
    }
}
