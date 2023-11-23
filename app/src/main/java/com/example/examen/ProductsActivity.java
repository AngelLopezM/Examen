package com.example.examen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductsActivity extends AppCompatActivity {

    private ListView listView;
    private ItemAdapter itemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products);

        listView = findViewById(R.id.productList);

        // Obtener datos desde la API usando Retrofit
        ApiService apiService = Api.getApiService();
        Call<List<ProductModel>> call = apiService.obtenerProductos();

        call.enqueue(new Callback<List<ProductModel>>() {
            @Override
            public void onResponse(Call<List<ProductModel>> call, Response<List<ProductModel>> response) {
                if (response.isSuccessful()) {
                    List<ProductModel> products = response.body();

                    // Actualizar la lista de productos en el adaptador existente
                    if (itemAdapter == null) {
                        // Si el adaptador aún no ha sido creado, créalo
                        itemAdapter = new ItemAdapter(products, ProductsActivity.this);
                        listView.setAdapter(itemAdapter);
                    } else {
                        // Si el adaptador ya existe, actualiza la lista de productos
                        itemAdapter.setProducts(products);
                        itemAdapter.notifyDataSetChanged();
                    }
                } else {
                    // Manejar error
                    Toast.makeText(ProductsActivity.this, "Error al obtener productos", Toast.LENGTH_SHORT).show();
                }
            }


            @Override
            public void onFailure(Call<List<ProductModel>> call, Throwable t) {
                // Manejar error de conexión
                Toast.makeText(ProductsActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // Método para eliminar un producto
    public void eliminarProducto(int productId) {
        // Realizar la llamada a la API para eliminar el producto con el ID proporcionado
        ApiService apiService = Api.getApiService();
        Call<Void> call = apiService.eliminarProducto(productId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Eliminar el producto localmente de la lista y notificar al adaptador
                    if (itemAdapter != null) {
                        itemAdapter.eliminarProducto(productId);
                        itemAdapter.notifyDataSetChanged();
                        Toast.makeText(ProductsActivity.this, "Producto eliminado", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Manejar error
                    Toast.makeText(ProductsActivity.this, "Error al eliminar producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Manejar error de conexión
                Toast.makeText(ProductsActivity.this, "Error de conexión", Toast.LENGTH_SHORT).show();
            }
        });
    }
}