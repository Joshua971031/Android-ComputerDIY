package DAO;
import android.util.Log;

import com.example.computerdiy.DBUtil;
import com.example.computerdiy.HHD;
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
public class HhdDAO{

    List<HHD> HHDList = new ArrayList<HHD>();
    public List<HHD> list() {
        //创建一个定长的核心线程和最大线程数都是1的FixedThreadPool线程池,使用callable和future.get()方法从线程返回值，从而获取数据库的值
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Callable<List<HHD>> callable = new Callable<List<HHD>>() {

            @Override
            public List<HHD> call() {
                List<HHD> HHDList = new ArrayList<HHD>();
                try {
                    Connection cn = DBUtil.getConn();
                    String sql = "select * from hhd";
                    Statement st = (Statement) cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);

                    int[] images = {R.drawable.i9_9900k, R.drawable.i9_9900k, R.drawable.i9_9900k};
                    while (rs.next()) {
                        HHD u = new HHD();
                        u.setModel(rs.getString("型号"));
                        Log.i("Mainactivity",u.getModel());
                        u.setBrand(rs.getString("品牌"));
                        u.setCapacity(rs.getString("容量"));
                        u.setPrice(rs.getInt("价格"));
                        u.setImage(images[0]);
                        HHDList.add(u);
                    }
                    DBUtil.closeConn(cn);//关闭连接
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return HHDList;
            }
        };
        Future<List<HHD>> future = executorService.submit(callable);

        try{
            HHDList = future.get();

        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (ExecutionException e) {
            e.printStackTrace();
        }
        return HHDList;
    }

}
