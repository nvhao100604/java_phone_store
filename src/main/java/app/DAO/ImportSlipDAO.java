package app.DAO;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import app.DTO.ImportSlip;
import app.database.DBConnect;

public class ImportSlipDAO {

    public List<ImportSlip> getAllImportSlips() {
        List<ImportSlip> list = new ArrayList<>();
        String sql = "SELECT * from phieunhap WHERE 1";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ImportSlip importSlip = new ImportSlip(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getDate(4),
                        rs.getBigDecimal(5),
                        rs.getInt(6),
                        rs.getInt(7));
                list.add(importSlip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ImportSlip> getAllActiveImportSlips() {
        List<ImportSlip> list = new ArrayList<>();
        String sql = "SELECT * from phieunhap WHERE TRANGTHAI=1";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ImportSlip importSlip = new ImportSlip(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getDate(4),
                        rs.getBigDecimal(5),
                        rs.getInt(6),
                        rs.getInt(7));
                list.add(importSlip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public int addImportSlip(ImportSlip importSlip) {
        String sql = "INSERT INTO phieunhap (idNCC, idTK, THANHTIEN, LOINHUAN) VALUES (?, ?, ?, ?)";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);) {
            stmt.setInt(1, importSlip.getSupplierId());
            stmt.setInt(2, importSlip.getEmployeeId());
            stmt.setBigDecimal(3, importSlip.getTotalAmount());
            stmt.setInt(4, importSlip.getProfit());
            stmt.executeUpdate();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    int generatedId = rs.getInt(1);
                    System.out.println("Generated ID: " + generatedId);
                    return generatedId;
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return -1;
    }

    public int updateImportSlip(ImportSlip importSlip) {
        if (importSlip.getImportSlipId() == 0) {
            return -1;
        }

        String sql = "UPDATE phieunhap SET idNCC=?, idTK = ?, NGAYNHAP=?, THANHTIEN=?, LOINHUAN=?, TRANGTHAI=? WHERE idPN=?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, importSlip.getSupplierId());
            stmt.setInt(2, importSlip.getEmployeeId());
            stmt.setDate(3, importSlip.getImportDate());
            stmt.setBigDecimal(4, importSlip.getTotalAmount());
            stmt.setInt(5, importSlip.getProfit());
            stmt.setInt(6, importSlip.getStatus());
            stmt.setInt(7, importSlip.getImportSlipId());
            return stmt.executeUpdate();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return -1;
    }

    public int deleteImportSlip(int importSlipId) {
        String sql = "DELETE FROM phieunhap WHERE idPN=?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, importSlipId);
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int softDeleteImportSlip(int importSlipId) {
        String sql = "UPDATE phieunhap SET TRANGTHAI=0 WHERE idPN=?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, importSlipId);
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public int restoreImportSlip(int importSlipId) {
        String sql = "UPDATE phieunhap SET TRANGTHAI=1 WHERE idPN=?";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement stmt = con.prepareStatement(sql)) {
            stmt.setInt(1, importSlipId);
            return stmt.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    public List<ImportSlip> searchImportSlips(String keyword) {
        List<ImportSlip> list = new ArrayList<>();
        String sql = "SELECT * FROM phieunhap WHERE idPN LIKE ? OR idNCC LIKE ? OR NGAYNHAP LIKE ? OR THANHTIEN LIKE ? OR LOINHUAN LIKE ? OR TRANGTHAI LIKE ? AND TRANGTHAI=1";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            String likeKeyword = "%" + keyword + "%";
            for (int i = 1; i <= 6; i++) {
                ps.setString(i, likeKeyword);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ImportSlip importSlip = new ImportSlip(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getInt(3),
                            rs.getDate(4),
                            rs.getBigDecimal(5),
                            rs.getInt(6),
                            rs.getInt(7));
                    list.add(importSlip);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ImportSlip> filterImportSlipsByDate(Date startDate, Date endDate) {
        List<ImportSlip> list = new ArrayList<>();
        String sql = "SELECT * FROM phieunhap WHERE NGAYNHAP BETWEEN ? AND ? AND TRANGTHAI=1";
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setDate(1, startDate);
            ps.setDate(2, endDate);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    ImportSlip importSlip = new ImportSlip(
                            rs.getInt(1),
                            rs.getInt(2),
                            rs.getInt(3),
                            rs.getDate(4),
                            rs.getBigDecimal(5),
                            rs.getInt(6),
                            rs.getInt(7));
                    list.add(importSlip);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<ImportSlip> sortImportSlips(String sortBy, boolean ascending) {
        List<ImportSlip> list = new ArrayList<>();
        String order = ascending ? "ASC" : "DESC";
        String sql = "SELECT * FROM phieunhap WHERE TRANGTHAI=1 ORDER BY " + sortBy + " " + order;
        try (Connection con = DBConnect.getConnection();
                PreparedStatement ps = con.prepareStatement(sql);
                ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                ImportSlip importSlip = new ImportSlip(
                        rs.getInt(1),
                        rs.getInt(2),
                        rs.getInt(3),
                        rs.getDate(4),
                        rs.getBigDecimal(5),
                        rs.getInt(6),
                        rs.getInt(7));
                list.add(importSlip);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
