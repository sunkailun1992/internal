package com.gb.rpc;

import com.gb.utils.Json;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author sunkailun
 * @DateTime 2020/1/8  11:04 上午
 * @email 376253703@qq.com
 * @phone 13777579028
 * @explain
 */
@FeignClient(value = "user")
public interface UserRpc {
    /**
     * 新增
     *
     * @param map:
     * @return com.utils.Json
     * @author sunkailun
     * @DateTime 2018/5/8  下午4:58
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @RequestMapping(value = "/user/user/save", method = RequestMethod.POST)
    Json save(@RequestParam Map<String, String> map);
    /**
     * 修改
     *
     * @param map:
     * @return com.utils.Json
     * @author sunkailun
     * @DateTime 2018/5/8  下午4:58
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @RequestMapping(value = "/user/user/update", method = RequestMethod.PUT)
    Json update(@RequestParam Map<String, String> map);
    /**
     * 单条查询
     *
     * @param map:
     * @return com.utils.Json
     * @author sunkailun
     * @DateTime 2018/5/8  下午4:58
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @RequestMapping(value = "/user/user/selectOne", method = RequestMethod.GET)
    Json selectOne(@RequestParam Map<String, String> map);
    /**
     * 查询用户集合
     *
     * @param map:
     * @return com.utils.Json
     * @author sunkailun
     * @DateTime 2018/5/8  下午4:58
     * @email 376253703@qq.com
     * @phone 13777579028
     */
    @RequestMapping(value = "/user/user/select", method = RequestMethod.GET)
    Json select(@RequestParam Map<String, String> map);

}
