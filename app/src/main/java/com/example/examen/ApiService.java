package com.example.examen;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiService {

    @GET("/productos")
    Call<List<ProductModel>> obtenerProductos();

    @DELETE("/productos/{id}")  // Ajusta la ruta según tu API
    Call<Void> eliminarProducto(@Path("id") int productId);

    @POST("/productos")  // Ajusta la ruta según tu API
    Call<Void> crearProducto(@Body CreateProductRequest nuevoProducto);

    @PUT("/productos/{id}")
    Call<Void> actualizarProducto(@Path("id") int productId, @Body CreateProductRequest productoActualizado);

}
