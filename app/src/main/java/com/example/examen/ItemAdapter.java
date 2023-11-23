package com.example.examen;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import java.util.List;

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

        return convertView;
    }

    // Método para eliminar un producto
    public void eliminarProducto(int productId) {
        // Implementa la lógica para eliminar el producto localmente
        listaProductos.removeIf(product -> product.getIdProducto() == productId);
        notifyDataSetChanged();
    }

}