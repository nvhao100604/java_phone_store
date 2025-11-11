package app.BUS;

import app.DAO.ImeiDAO;

public class ImeiBUS {
    private ImeiDAO imeiDAO;

    public ImeiBUS() {
        imeiDAO = new ImeiDAO();
    }

    public int updateImeiOrder(String imei, int newIdPN) {
        return imeiDAO.updateImeiOrder(imei, newIdPN);
    }

    public int AddImei(app.DTO.Imei imei) {
        return imeiDAO.AddImei(imei);
    }
}
