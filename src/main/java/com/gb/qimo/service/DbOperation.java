package com.gb.qimo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.dynamic.datasource.toolkit.DynamicDataSourceContextHolder;
import com.gb.qimo.entity.PotentialCustomer;
import com.gb.rpc.CustomerRpc;
import com.gb.utils.Json;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Java中使用JDBC连接数据库
 * 1） 加载驱动 2） 创建数据库连接
 * 3） 创建执行sql的语句 4） 执行语句 5） 处理执行结果 6） 释放资源
 *
 * @author lijh
 * @Date 2021/7/7
 */
@Slf4j
@Service
public class DbOperation {

    @Resource
    private CustomerRpc customerRpc;

    private static DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    /**
     * PreparedStatement继承自Statement,都是接口
     * PreparedStatement可以使用占位符，批处理比Statement效率高
     */
    public Boolean conn() {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        // 1.加载驱动程序
        try {
            //获取已拉取最近时间的数据
            Map<String, Object> map = getRecentlyPotentialCustomer();
            log.debug("需要拉取的时间段={}", map);
            // 2.获得数据库链接
            conn = getConn();
            log.debug("数据库链接建立成功={}", conn);
            // 3.通过数据库的连接操作数据库，实现增删改查（使用Statement类）
            int count = getCount(map, conn);
            log.debug("共获取到BI数量为={}", count);
            //每次查询个数
            int pageSize = 2000;
            //计算页数
            int page = (count - 1) / pageSize + 1;
            log.debug("一共分为" + page + "页");
            for (int i = 1; i <= page; i++) {
                List<PotentialCustomer> customerList = new ArrayList<>();
                //分页查询需要拉取bi数据的线索，去除手机号重复的。相同手机号优先取最近时间的一条
                String sql = "SELECT pc1.id,pc1.name,pc1.potential_customer_source_id,pc1.danger_planted_category_name,pc1.create_date_time,pc1.modify_date_time,pc1.address,pc1.enterprise_name,pc1.mobile FROM\n" +
                        "\t( SELECT mobile, max( create_date_time ) AS create_date_time, max( id ) AS id FROM potential_customer where create_date_time BETWEEN ? and ?  GROUP BY mobile limit ?,?) pc\n" +
                        "\tLEFT JOIN potential_customer pc1 ON pc.id = pc1.id";
                ps = conn.prepareStatement(sql);
                ps.setObject(1, map.get("startTime"));
                ps.setObject(2, map.get("endTime"));
                ps.setInt(3, (i - 1) * pageSize);
                ps.setInt(4, pageSize);
                rs = ps.executeQuery();
                log.debug("查到的BI数据={}", rs);
                while (rs.next()) {
                    PotentialCustomer potentialCustomer = new PotentialCustomer();
                    potentialCustomer.setId(rs.getString("id"));
                    potentialCustomer.setName(rs.getString("name"));
                    potentialCustomer.setPotentialCustomerSourceId(rs.getString("potential_customer_source_id"));
                    potentialCustomer.setDangerPlantedCategoryName(rs.getString("danger_planted_category_name"));
                    potentialCustomer.setSubmitTime(LocalDateTime.parse(rs.getString("create_date_time"), df));
                    potentialCustomer.setModifyDateTime(LocalDateTime.parse(rs.getString("modify_date_time"), df));
                    potentialCustomer.setAddress(rs.getString("address"));
                    potentialCustomer.setEnterpriseName(rs.getString("enterprise_name"));
                    potentialCustomer.setMobile(rs.getString("mobile"));
                    customerList.add(potentialCustomer);
                }
                log.debug("保存第" + i + "次开始,保存BI数据数量" + customerList.size());
                for (PotentialCustomer customer : customerList) {
                    List<PotentialCustomer> saveList = new ArrayList<>();
                    saveList.add(customer);
                    Json json = customerRpc.saveAllBiInfo(saveList);
                    if (!json.getSuccess()) {
                        log.error("保存BI数据异常" + json);
                        break;
                    }
                }
                log.debug("保存第" + i + "次结束");
            }
            return true;
        } catch (Exception e) {
            log.error("BI数据拉取异常:" + e);
            e.printStackTrace();
        } finally {
            // 关闭资源
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 获取总数
     *
     * @return
     */
    public static int getCount(Map<String, Object> map, Connection dbConn) {
        String query1 = "SELECT count(*) as pcCount from potential_customer where create_date_time BETWEEN ? and ?";
        String query = "SELECT count(pc1.id) as pcCount FROM\n" +
                "\t( SELECT mobile, max( create_date_time ) AS create_date_time,max( id ) AS id FROM potential_customer where create_date_time BETWEEN ? and ? GROUP BY mobile ) pc\n" +
                "\tLEFT JOIN potential_customer pc1 ON pc.id = pc1.id";
        int i = 0;
        try {
            PreparedStatement statement = dbConn.prepareStatement(query);
            statement.setObject(1, map.get("startTime"));
            statement.setObject(2, map.get("endTime"));
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                i = rs.getInt("pcCount");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 获取数据库链接
     *
     * @return
     * @throws SQLException
     */
    public static Connection getConn() throws SQLException {
        String url = "jdbc:mysql://172.16.76.109:3306/gbj-useinfo?characterEncoding=utf-8";
        String userName = "gbw";
        String password = "5K8I8fC1";
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        Connection conn = DriverManager.getConnection(url, userName, password);
        return conn;
    }

    /**
     * 获取BI数据已存在线索时间最近的一条
     *
     * @return
     */
    public Map<String, Object> getRecentlyPotentialCustomer() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<>(2);
        Map<String, Object> queryMap = new HashMap<>(1);
        queryMap.put("sourceQuery", "16");
        Json json = customerRpc.selectOneRecentlyClues(queryMap);
        if (!json.getSuccess()) {
            log.error("RPC获取BI线索最近一条数据信息失败");
        }
        DynamicDataSourceContextHolder.clear();
        LocalDateTime startTime = LocalDateTime.parse("2019-01-01 00:00:00", dtf);
        LocalDateTime endTime = LocalDateTime.now();
        //查询到BI数据，就根据最近一条数据的时间，拉取BI数据，未查询到拉取所有
        if (Objects.nonNull(json.getObj())) {
            PotentialCustomer potentialCustomer = JSONObject.parseObject(JSON.toJSONString(json.getObj()), PotentialCustomer.class);
            log.debug("BI已存在数据时间最近的一条" + potentialCustomer);
            if (Objects.nonNull(potentialCustomer)) {
                startTime = potentialCustomer.getCreateDateTime();
            }
        }
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        return map;
    }

    public static void main(String[] args) throws Exception {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        Map<String, Object> map = new HashMap<>(3);
        LocalDateTime startTime = LocalDateTime.parse("2021-09-16 00:00:00", dtf);
        LocalDateTime endTime = LocalDateTime.now();
        map.put("startTime", startTime);
        map.put("endTime", endTime);
        System.out.println(DbOperation.getCount(map, getConn()));
    }

}
