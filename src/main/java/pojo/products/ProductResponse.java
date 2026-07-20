package pojo.products;

public class ProductResponse {

	private Integer id;
	private String category;
	private String name;
	private Boolean inStock;

	public ProductResponse() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Boolean getInStock() {
		return inStock;
	}

	public void setInStock(Boolean inStock) {
		this.inStock = inStock;
	}

	@Override
	public String toString() {
		return "ProductResponse{" + "id=" + id + ", category='" + category + '\'' + ", name='" + name + '\''
				+ ", inStock=" + inStock + '}';
	}
}