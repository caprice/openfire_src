package wetodo.manager;

import wetodo.dao.ProductDAO;
import wetodo.model.Product;

import java.util.List;

public class ProductManager {

    private static ProductManager instance;

    /**
     * 单例产生实例
     *
     * @return
     */
    public static ProductManager getInstance() {
        if (instance == null) {
            synchronized (ProductManager.class) {
                instance = new ProductManager();
            }
        }
        return instance;
    }

    public List<Product> list() {
        return ProductDAO.all();
    }
}
