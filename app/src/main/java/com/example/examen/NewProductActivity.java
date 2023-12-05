package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewProductActivity extends AppCompatActivity {

    private EditText nombreEditText;
    private EditText existenciasEditText;
    private EditText precioEditText;
    private EditText descripcionEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_product);

        nombreEditText = findViewById(R.id.campoTextoNombre);
        existenciasEditText = findViewById(R.id.campoTextoExistencias);
        precioEditText = findViewById(R.id.campoTextoPrecio);
        descripcionEditText = findViewById(R.id.campoTextoDescripcion);

        Button btnGuardar = findViewById(R.id.buttonGuardarNuevo);

        // Asigna un click listener al botón de guardar
        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtiene los textos de los campos de texto
                String nombre = nombreEditText.getText().toString();
                int existencias = Integer.parseInt(existenciasEditText.getText().toString());
                double precio = Double.parseDouble(precioEditText.getText().toString());
                String descripcion = descripcionEditText.getText().toString();

                // Crea un nuevo objeto ProductModel con los datos ingresados
                ProductModel nuevoProducto = new ProductModel(0, nombre, existencias, precio, descripcion);

                // Llama a la API para crear el nuevo producto
                crearProductoEnAPI(nuevoProducto);
            }
        });
    }

    // Método para llamar a la API y crear un nuevo producto
    private void crearProductoEnAPI(ProductModel nuevoProducto) {
        ApiService apiService = Api.getApiService();

        Gson gson = new Gson();
        String jsonBody = gson.toJson(nuevoProducto);
        Log.d("JSON Body", jsonBody);

        CreateProductRequest createProductRequest = new CreateProductRequest();
        createProductRequest.setNombre(nuevoProducto.getNombre());
        createProductRequest.setExistencias(nuevoProducto.getExistencias());
        createProductRequest.setPrecio(nuevoProducto.getPrecio());
        createProductRequest.setDescripcion(nuevoProducto.getDescripcion());

        Call<Void> call = apiService.crearProducto(createProductRequest); // Ajusta el nombre del método según tu interfaz

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // La operación de creación fue exitosa
                    // Puedes manejar el éxito de alguna manera, por ejemplo, mostrar un mensaje
                    Toast.makeText(NewProductActivity.this, "Producto creado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    // La operación de creación falló
                    // Puedes manejar el fallo de alguna manera, por ejemplo, mostrar un mensaje de error
                    System.out.println(response.code());
                    System.out.println(response.body());
                    System.out.println(response.message());
                    Toast.makeText(NewProductActivity.this, "Error al crear el producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Manejar el fallo de la llamada a la API, por ejemplo, mostrar un mensaje de error
                Toast.makeText(NewProductActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }
}

