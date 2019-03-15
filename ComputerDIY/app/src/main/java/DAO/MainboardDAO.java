package DAO;
import android.util.Log;

import com.example.computerdiy.DBUtil;
import com.example.computerdiy.MAINBOARD;
import com.example.computerdiy.R;


import java.util.ArrayList;
import java.util.List;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

//hhd数据导入
public class MainboardDAO{

    List<MAINBOARD> MAINBOARDList = new ArrayList<MAINBOARD>();
    public List<MAINBOARD> list() {
        //创建一个定长的核心线程和最大线程数都是1的FixedThreadPool线程池,使用callable和future.get()方法从线程返回值，从而获取数据库的值
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Callable<List<MAINBOARD>> callable = new Callable<List<MAINBOARD>>() {

            @Override
            public List<MAINBOARD> call() {
                List<MAINBOARD> HHDList = new ArrayList<MAINBOARD>();
                try {
                    Connection cn = DBUtil.getConn();
                    String sql = "select * from hhd";
                    Statement st = (Statement) cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);

                    int[] images = {R.drawable.i9_9900k, R.drawable.i9_9900k, R.drawable.i9_9900k};
                    while (rs.next()) {
                        MAINBOARD u = new MAINBOARD();
                        u.setModel(rs.getString("型号"));
                        Log.i("Mainactivity",u.getModel());
                        u.setBrand(rs.getString("品牌"));
                        u.setPlatform(rs.getString("平台"));
                        u.setSize(rs.getString("尺寸"));
                        u.setPrice(rs.getInt("价格"));
                        u.setImage(images[0]);
                        HHDList.add(u);
                    }
                    DBUtil.closeConn(cn);//关闭连接
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return MAINBOARDList;
            }
        };
        Future<List<MAINBOARD>> future = executorService.submit(callable);

        try{
            MAINBOARDList = future.get();

        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (ExecutionException e) {
            e.printStackTrace();
        }
        return MAINBOARDList;
    }

}
