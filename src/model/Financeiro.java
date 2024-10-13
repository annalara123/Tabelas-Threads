package model;

public class Financeiro {
    private String data;
    private double valor;
    private int tipo;

    public Financeiro(String data, double valor, int tipo) {
        this.data = data;
        this.valor = valor;
        this.tipo = tipo;
    }

    public String getData() {
        return data;
    }

    public double getValor() {
        return valor;
    }

    public int getTipo() {
        return tipo;
    }
}
