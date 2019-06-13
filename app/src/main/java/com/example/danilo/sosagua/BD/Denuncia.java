package com.example.danilo.sosagua.BD;

public class Denuncia {
    private String etaria;
    private String categoria;
    private String imovel;
    private String situacao;
    private String descricao;
    public String data;
    private int codigo;
    private double latitude;
    private double longitude;
    public String data2;


    public Denuncia(String categoria,String etaria,String imovel,String situacao,String descricao,int codigo,
                    double latitude,double longitude,String data,String data2){
        this.etaria = etaria;
        this.categoria = categoria;
        this.imovel = imovel;
        this.situacao = situacao;
        this.descricao = descricao;
        this.codigo = codigo;
        this.latitude = latitude;
        this.longitude = longitude;
        this.data = data;
        this.data2 = data2;
    }

    public String getEtaria() {
        return etaria;
    }

    public String getCategoria() {
        return categoria;
    }

    public String getImovel() {
        return imovel;
    }

    public String getSituacao() {
        return situacao;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCodigo() {
        return codigo;
    }

    public double getLatitude() {
        return latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public String getData() {
        return data;
    }

    public String getData2() {
        return data2;
    }
}
