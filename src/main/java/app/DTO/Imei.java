package app.DTO;

public class Imei {
    private int idProductDetail;
    private String imei;
    private Integer idOrder;
    private int idImport;
    private boolean status;

    public Imei() {
    }

    public Imei(int idProductDetail, String imei, Integer idOrder, int idImport, boolean status) {
        this.idProductDetail = idProductDetail;
        this.imei = imei;
        this.idOrder = idOrder;
        this.idImport = idImport;
        this.status = status;
    }

    public int getIdProductDetail() {
        return idProductDetail;
    }

    public void setIdProductDetail(int idProductDetail) {
        this.idProductDetail = idProductDetail;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getIdOrder() {
        return idOrder;
    }

    public void setIdOrder(Integer idOrder) {
        this.idOrder = idOrder;
    }

    public int getIdImport() {
        return idImport;
    }

    public void setIdImport(int idImport) {
        this.idImport = idImport;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

}
