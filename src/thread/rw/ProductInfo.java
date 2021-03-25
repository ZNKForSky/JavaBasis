package thread.rw;

/**
 * @author Luffy
 * @Classname ProductInfo
 * @Description 产品信息：产品名、库存、销售额
 * @Date 2020/12/21 14:11
 */
public class ProductInfo {
    /*产品名*/
    private String name;
    /*产品库存*/
    private int stock;
    /*产品销售额*/
    private double sales;

    public ProductInfo(String name, int stock, double sales) {
        this.name = name;
        this.stock = stock;
        this.sales = sales;
    }

    void changeStock(int number) {
        stock -= number;
        sales += 68.66D * number;
    }
}
