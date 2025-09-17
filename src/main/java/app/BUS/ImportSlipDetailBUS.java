package app.BUS;

import java.util.List;

import app.DAO.ImportSlipDetailDAO;
import app.DTO.Product;
import app.DTO.ProductDetail;

public class ImportSlipDetailBUS {

	private ImportSlipDetailDAO dao;

	public ImportSlipDetailBUS() {
		dao = new ImportSlipDetailDAO();
	}

	public List<Product> getProductsByImportSlipId(int importSlipId) {
		return dao.getProductsByImportSlipId(importSlipId);
	}

	public static void main(String[] args) {
		ImportSlipDetailBUS bus = new ImportSlipDetailBUS();
		List<Product> products = bus.getProductsByImportSlipId(1);
		for (Product product : products) {
			System.out.println("Product ID: " + product.getProductId());
			System.out.println("Product Name: " + product.getProductName());
			System.out.printf("Import Price: %.2f\n", product.getImportPrice());
			for (ProductDetail detail : product.getProductDetails()) {
				System.out.println("  Product Detail ID: " + detail.getProductDetailId());
				System.out.println("  Color: " + detail.getColor());
				System.out.println("  Capacity: " + detail.getCapacity());
				System.out.println("  Stock: " + detail.getStock());
			}
			System.out.println("---------------------------");
		}
	}
}
