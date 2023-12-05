package com.example.examen;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ItemAdapter extends BaseAdapter {
    private List<ProductModel> listaProductos;
    private Context context;

    public ItemAdapter(List<ProductModel> listaProductos, Context context) {
        this.listaProductos = listaProductos;
        this.context = context;
    }

    public void setProducts(List<ProductModel> products) {
        this.listaProductos = products;
    }

    @Override
    public int getCount() {
        return listaProductos.size();
    }

    @Override
    public Object getItem(int position) {
        return listaProductos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_producto, parent, false);
        }

        // Obtener el producto en la posición actual
        ProductModel producto = listaProductos.get(position);

        // Enlazar los datos del producto a la vista
        TextView nombreTextView = convertView.findViewById(R.id.nombreTextView);
        TextView existenciasTextView = convertView.findViewById(R.id.existenciasTextView);
        TextView precioTextView = convertView.findViewById(R.id.precioTextView);
        TextView descripcionTextView = convertView.findViewById(R.id.descripcionTextView);
        Button btnEliminar = convertView.findViewById(R.id.btnEliminar);
        Button btnEditar = convertView.findViewById(R.id.btnActualizar);

        nombreTextView.setText(producto.getNombre());
        existenciasTextView.setText("Existencias: " + producto.getExistencias());
        precioTextView.setText("Precio: $" + producto.getPrecio());
        descripcionTextView.setText(producto.getDescripcion());

        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminarProducto(producto.getIdProducto()); // Llamada al método para eliminar el producto
            }
        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ProductModel productoSeleccionado = listaProductos.get(position);

                Intent intent = new Intent(context, EditProductActivity.class);

                intent.putExtra("clave_id", productoSeleccionado.getIdProducto());
                intent.putExtra("clave_nombre", productoSeleccionado.getNombre());
                intent.putExtra("clave_precio", productoSeleccionado.getPrecio());
                intent.putExtra("clave_existencias", productoSeleccionado.getExistencias());
                intent.putExtra("clave_descripcion", productoSeleccionado.getDescripcion());


                // Iniciar la ActivityB con el Intent
                context.startActivity(intent);

            }
        });

        return convertView;
    }

    public void eliminarProducto(int productId) {

        ApiService apiService = Api.getApiService();

        Call<Void> call = apiService.eliminarProducto(productId);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // La operación de eliminación fue exitosa
                    // Puedes manejar el éxito de alguna manera, por ejemplo, mostrar un mensaje
                    Toast.makeText(context, "Producto eliminado exitosamente", Toast.LENGTH_SHORT).show();
                    // Además, puedes actualizar tu lista de productos localmente
                    listaProductos.removeIf(product -> product.getIdProducto() == productId);
                    notifyDataSetChanged();
                } else {
                    // La operación de eliminación falló
                    // Puedes manejar el fallo de alguna manera, por ejemplo, mostrar un mensaje de error
                    Toast.makeText(context, "Error al eliminar el producto", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                // Manejar el fallo de la llamada a la API, por ejemplo, mostrar un mensaje de error
                Toast.makeText(context, "Error de red", Toast.LENGTH_SHORT).show();
            }
        });
    }


}