package DAO;

import android.util.Log;

import com.example.computerdiy.DBUtil;
import objects.GPU;
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
public class GpuDAO{

    List<GPU> GPUList = new ArrayList<GPU>();
    public List<GPU> list() {
        //创建一个定长的核心线程和最大线程数都是1的FixedThreadPool线程池,使用callable和future.get()方法从线程返回值，从而获取数据库的值
        ExecutorService executorService = Executors.newFixedThreadPool(2);

        Callable<List<GPU>> callable = new Callable<List<GPU>>() {

            @Override
            public List<GPU> call() {
                List<GPU> GPUList = new ArrayList<GPU>();
                try {
                    Connection cn = DBUtil.getConn();
                    String sql = "select * from table_gpu";
                    Statement st = (Statement) cn.createStatement();
                    ResultSet rs = st.executeQuery(sql);

                    int[] images = {R.drawable.i9_9900k, R.drawable.i9_9900k, R.drawable.i9_9900k};
                    while (rs.next()) {
                        GPU u = new GPU();
                        u.setModel(rs.getString("型号"));
                        //  Log.i("Mainactivity",u.getModel());
                        u.setBrand(rs.getString("品牌"));
                        u.setGRAM(rs.getString("显存"));
                        u.setSize(rs.getString("尺寸"));
                        u.setPrice(rs.getInt("价格"));
                        u.setImage(images[0]);
                        GPUList.add(u);
                    }
                    DBUtil.closeConn(cn);//关闭连接
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return GPUList;
            }
        };
        Future<List<GPU>> future = executorService.submit(callable);

        try{
            GPUList = future.get();

        }catch (InterruptedException e) {
            e.printStackTrace();
        }catch (ExecutionException e) {
            e.printStackTrace();
        }
        return GPUList;
    }

}
