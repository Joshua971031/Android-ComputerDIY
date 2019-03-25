package DAO;

import android.util.Log;

import com.example.computerdiy.DBUtil;
import objects.SSD;
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
public class SsdDAO{

    List<SSD> SSDList = new ArrayList<SSD>();
    public List<SSD> list() {
        //创建一个定长的核心线程和最大线程数都是1的FixedThreadPool线程池,使用callable和future.get()方法从线程返回值，从而获取数据库的值
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Callable<List<SSD>> callable = new Callable<List<SSD>>() {

            @Override
            public List<SSD> call() {
                List<SSD> HHDList = new ArrayList<SSD>();
                try {
                    Connection cn = DBUtil.getConn();
                    String sql = "select * from table_ssd";
                    Statement st = (Statement) cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);

                    int[] images = {R.drawable.i9_9900k, R.drawable.i9_9900k, R.drawable.i9_9900k};
                    while (rs.next()) {
                        SSD u = new SSD();
                        u.setModel(rs.getString("型号"));
                        //  Log.i("Mainactivity",u.getModel());
                        u.setBrand(rs.getString("品牌"));
                        u.setCapacity(rs.getString("容量"));
                        u.setPrice(rs.getInt("价格"));
                        u.setImage(images[0]);
                        SSDList.add(u);
                    }
                    DBUtil.closeConn(cn);//关闭连接
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return SSDList;
            }
        };
        Future<List<SSD>> future = executorService.submit(callable);

        try{
            SSDList = future.get();

        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (ExecutionException e) {
            e.printStackTrace();
        }
        return SSDList;
    }

}
