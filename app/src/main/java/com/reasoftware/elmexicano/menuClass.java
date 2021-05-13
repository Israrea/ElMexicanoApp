package com.reasoftware.elmexicano;

public class menuClass {



        String nombre_platillo,
                descripcion_platillo,
                img_platillo;

    int id_platillo;
    int precio_platillo;

    public menuClass(String nombre_platillo, String descripcion_platillo, String img_platillo, int id_platillo,int precio_platillo) {
        this.nombre_platillo = nombre_platillo;
        this.descripcion_platillo = descripcion_platillo;
        this.img_platillo = img_platillo;
        this.id_platillo = id_platillo;
        this.precio_platillo = precio_platillo;
    }

    public int getPrecio_platillo() {
        return precio_platillo;
    }

    public void setPrecio_platillo(int precio_platillo) {
        this.precio_platillo = precio_platillo;
    }


    public String getNombre_platillo() {
        return nombre_platillo;
    }

    public void setNombre_platillo(String nombre_platillo) {
        this.nombre_platillo = nombre_platillo;
    }

    public String getDescripcion_platillo() {
        return descripcion_platillo;
    }

    public void setDescripcion_platillo(String descripcion_platillo) {
        this.descripcion_platillo = descripcion_platillo;
    }

    public String getImg_platillo() {
        return img_platillo;
    }

    public void setImg_platillo(String img_platillo) {
        this.img_platillo = img_platillo;
    }

    public int getId_platillo() {
        return id_platillo;
    }

    public void setId_platillo(int id_platillo) {
        this.id_platillo = id_platillo;
    }
}
