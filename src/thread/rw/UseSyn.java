package thread.rw;

import tools.SleepTools;

/**
 * @author Luffy
 * @Classname UseSyn
 * @Description 使用内置锁模拟读写商品
 * @Date 2020/12/21 14:20
 */
public class UseSyn implements ProductsService {
    private ProductInfo mProductInfo;

    public UseSyn(ProductInfo productInfo) {
        mProductInfo = productInfo;
    }

    @Override
    public synchronized void setProducStock(int number) {
        SleepTools.ms(5);
        mProductInfo.changeStock(number);
    }

    @Override
    public synchronized ProductInfo getProductInfo() {
        SleepTools.ms(5);
        return mProductInfo;
    }
}
