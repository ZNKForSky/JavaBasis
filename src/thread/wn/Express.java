package thread.wn;

/**
 * @author Luffy
 * @Classname Express
 * @Description 以送快递的案例演示 wait()和notify/notifyAll()方法。
 * @Date 2020/12/16 13:37
 */
public class Express {
    /*发货地点*/
    private static final String CITY = "深圳";
    /*目标地点*/
    private String site = "上海";
    /*快递包裹与目标地点的公里数*/
    private int km = 1500;

    public Express(String site, int km) {
        this.site = site;
        this.km = km;
    }

    /**
     * 改变地点
     */
    synchronized void changeSite() {
        this.site = "上海";
        /*当地点发生改变时通知调用改变地点的线程*/
        notifyAll();
    }

    /**
     * 改变距离
     */
    synchronized void changeDistance() {
        while (km > 0) {
            km -= 300;
            try {
                System.out.println(Thread.currentThread().getName() + ":距离目的地还有" + km + "公里，请耐心等待...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        km = Math.max(km, 0);
        /*当距离发生改变时通知调用改变距离的线程*/
        notifyAll();
    }

    /*当快递还没到目的地的时候，就耐心等着*/
    synchronized void waitBringUp() {
        while (CITY.equals(site)) {
            try {
                System.out.println(Thread.currentThread().getName() + ":您的快递即将发出！");
                wait();
                /*当抵达目的地后被其他线程唤醒时，通知取快递*/
                System.out.println(Thread.currentThread().getName() + ":您的快递已到达" + site + "，到您楼下会通知您取件。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + ":客户包裹已抵达" + site + "，通知后台改变数据库数据。");
    }

    /*当包裹还没有到客户楼下时，通知客户耐心等待*/
    synchronized void waitKM() {
        while (km > 0) {
            try {
                System.out.println(Thread.currentThread().getName() + ":您的快递即将发出，距离您" + km + "公里，请耐心等待！");
                wait();
                /*当抵达目的地后被其他线程唤醒时，通知取快递*/
                System.out.println(Thread.currentThread().getName() + ":您的快递已到，请开门取件。");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(Thread.currentThread().getName() + ":客户包裹已签收，通知后台改变数据库数据。");
    }

}
