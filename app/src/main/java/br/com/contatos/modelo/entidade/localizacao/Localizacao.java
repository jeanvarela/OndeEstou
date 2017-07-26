package br.com.contatos.modelo.entidade.localizacao;

/**
 * Created by jean on 26/07/2017.
 */

public class Localizacao {

    private long   id;
    private long   id_menber;
    private String ocurrency;
    private double latitude;
    private double logitude;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId_menber() {
        return id_menber;
    }

    public void setId_menber(long id_menber) {
        this.id_menber = id_menber;
    }

    public String getOcurrency() {
        return ocurrency;
    }

    public void setOcurrency(String ocurrency) {
        this.ocurrency = ocurrency;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLogitude() {
        return logitude;
    }

    public void setLogitude(double logitude) {
        this.logitude = logitude;
    }
}
