package DAO;

import android.util.Log;

import com.example.computerdiy.DBUtil;
import objects.POWER;
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
public class PowerDAO{

    List<POWER> POWERList = new ArrayList<POWER>();
    public List<POWER> list() {
        //创建一个定长的核心线程和最大线程数都是1的FixedThreadPool线程池,使用callable和future.get()方法从线程返回值，从而获取数据库的值
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Callable<List<POWER>> callable = new Callable<List<POWER>>() {

            @Override
            public List<POWER> call() {
                List<POWER> HHDList = new ArrayList<POWER>();
                try {
                    Connection cn = DBUtil.getConn();
                    String sql = "select * from table_power";
                    Statement st = (Statement) cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);

                    int[] images = {R.drawable.i9_9900k, R.drawable.i9_9900k, R.drawable.i9_9900k};
                    while (rs.next()) {
                        POWER u = new POWER();
                        u.setModel(rs.getString("型号"));
                        //  Log.i("Mainactivity",u.getModel());
                        u.setBrand(rs.getString("品牌"));
                        u.setPower(rs.getString("功率"));
                        u.setSize(rs.getString("尺寸"));
                        u.setPrice(rs.getInt("价格"));
                        u.setImage(images[0]);
                        POWERList.add(u);
                    }
                    DBUtil.closeConn(cn);//关闭连接
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return POWERList;
            }
        };
        Future<List<POWER>> future = executorService.submit(callable);

        try{
            POWERList = future.get();

        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (ExecutionException e) {
            e.printStackTrace();
        }
        return POWERList;
    }

}
