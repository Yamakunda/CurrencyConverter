import java.util.*;

class Product {
    String name;
    double price;
    int quantity;

    public Product(String name, double price, int quantity) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Product{name='" + name + "', price=" + price + ", quantity=" + quantity + "}";
    }
}

public class Q1 {
    public static void main(String[] args) {
        // Danh sách sản phẩm
        List<Product> products = new ArrayList<>();
        products.add(new Product("Laptop", 999.99, 5));
        products.add(new Product("Smartphone", 499.99, 10));
        products.add(new Product("Tablet", 299.99, 0));
        products.add(new Product("Smartwatch", 199.99, 3));

        // Tính tổng giá trị kho
        double totalValue = calculateTotalInventoryValue(products);
        System.out.printf("Total inventory value: %.2f\n", totalValue);

        // Tìm sản phẩm đắt nhất
        String mostExpensiveProduct = findMostExpensiveProduct(products);
        System.out.println("Most expensive product: " + mostExpensiveProduct);

        // Kiểm tra sản phẩm có trong kho hay không
        String checkProduct = "Headphones";
        boolean InStock = isProductInStock(products, checkProduct);
        System.out.println("Is " + checkProduct + " in stock: " + InStock);

        // Sắp xếp sản phẩm theo tiêu chí. Ví dụ: quantity, desc
        String criteria = "quantity";
        String order = "DESC";
        sortProducts(products, criteria, order);

        // In danh sách sản phẩm sau khi sắp xếp
        System.out.println("Sorted products:");
        for (Product product : products) {
            System.out.println(product);
        }
    }

    // Tính tổng giá trị kho
    public static double calculateTotalInventoryValue(List<Product> products) {
        double totalValue = 0;
        for (Product product : products) {
            totalValue += product.price * product.quantity;
        }
        return totalValue;
    }

    // Tìm sản phẩm đắt nhất
    public static String findMostExpensiveProduct(List<Product> products) {
        String mostExpensiveProduct = "";
        double highestPrice = 0;
        for (Product product : products) {
            if (product.price > highestPrice) {
                highestPrice = product.price;
                mostExpensiveProduct = product.name;
            }
        }
        return mostExpensiveProduct;
    }

    // Kiểm tra xem sản phẩm có trong kho không
    public static boolean isProductInStock(List<Product> products, String productName) {
        for (Product product : products) {
            if (product.name.equalsIgnoreCase(productName) && product.quantity > 0) {
                return true;
            }
        }
        return false;
    }

    // Hàm sắp xếp danh sách sản phẩm
    public static void sortProducts(List<Product> products, String criteria, String order) {
        Comparator<Product> comparator = null;
        // Xác định tiêu chí sắp xếp
        if (criteria.equalsIgnoreCase("price")) {
            comparator = Comparator.comparingDouble(p -> p.price);
        } else if (criteria.equalsIgnoreCase("quantity")) {
            comparator = Comparator.comparingInt(p -> p.quantity);
        } else {
            // Mặc định sắp xếp theo giá
            comparator = Comparator.comparingDouble(p -> p.price);
        }
        // Xác định thứ tự sắp xếp
        if (order.equalsIgnoreCase("desc")) {
            comparator = comparator.reversed();
        }
        // Thực hiện sắp xếp
        Collections.sort(products, comparator);
    }
}
