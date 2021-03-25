package thread.rw;

/**
 * @author Luffy
 * @Classname GoodsService
 * @Description 产品服务
 * @Date 2020/12/21 14:12
 */
public interface ProductsService {
    /**
     * 设置产品库存
     *
     * @param number 库存数量
     */
    void setProducStock(int number);

    /**
     * 获取产品信息
     *
     * @return 产品信息
     */
    ProductInfo getProductInfo();
}
