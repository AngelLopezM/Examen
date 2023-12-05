package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
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

public class EditProductActivity extends AppCompatActivity {

    // Obtener referencias a los EditText en tu diseño
    private EditText nombreEditText;
    private EditText precioEditText;
    private EditText existenciasEditText;
    private EditText descripcionEditText;

    private Button btnGuardar;

    int idProducto;
    String nombre;
    double precio;
    int existencias;
    String descripcion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        nombreEditText = findViewById(R.id.campoTextoEditarNombre);
        precioEditText = findViewById(R.id.campoTextoEditarPrecio);
        existenciasEditText = findViewById(R.id.campoTextoEditarExistencias);
        descripcionEditText = findViewById(R.id.campoTextoEditarDescripcion);

        btnGuardar = findViewById(R.id.buttonGuardarEditar);

        Intent intent = getIntent();
        if (intent != null) {
            idProducto = intent.getIntExtra("clave_id", 0);
            nombre = intent.getStringExtra("clave_nombre");
            precio = intent.getDoubleExtra("clave_precio", 0.0);
            existencias = intent.getIntExtra("clave_existencias", 0);
            descripcion = intent.getStringExtra("clave_descripcion");

            // Establecer los valores en los EditText
            nombreEditText.setText(nombre);
            precioEditText.setText(String.valueOf(precio));
            existenciasEditText.setText(String.valueOf(existencias));
            descripcionEditText.setText(descripcion);
        }

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
                actualizarProductoEnApi(idProducto, nuevoProducto);
            }
        });
    }

    private void actualizarProductoEnApi(int productId, ProductModel productoActualizado) {
        ApiService apiService = Api.getApiService();



        CreateProductRequest createProductRequest = new CreateProductRequest();
        createProductRequest.setNombre(productoActualizado.getNombre());
        createProductRequest.setExistencias(productoActualizado.getExistencias());
        createProductRequest.setPrecio(productoActualizado.getPrecio());
        createProductRequest.setDescripcion(productoActualizado.getDescripcion());

        Gson gson = new Gson();
        String jsonBody = gson.toJson(createProductRequest);
        Log.d("JSON Body", jsonBody);

        Call<Void> call = apiService.actualizarProducto(productId, createProductRequest);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(EditProductActivity.this, "Producto actualizado exitosamente", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditProductActivity.this, "Error al actualizar el producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(EditProductActivity.this, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }

    /*private void actualizarProductoLocalmente(int productId, ProductModel productoActualizado) {
        for (int i = 0; i < listaProductos.size(); i++) {
            if (listaProductos.get(i).getIdProducto() == productId) {
                listaProductos.set(i, productoActualizado);
                return;
            }
        }
    }*/
}