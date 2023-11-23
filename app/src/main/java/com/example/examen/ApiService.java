package com.example.examen;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiService {
    @GET("/productos")
    Call<List<ProductModel>> obtenerProductos();

    @DELETE("/productos/{id}")  // Ajusta la ruta según tu API
    Call<Void> eliminarProducto(@Path("id") int productId);


}
