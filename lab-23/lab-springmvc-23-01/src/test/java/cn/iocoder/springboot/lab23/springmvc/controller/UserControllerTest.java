package cn.iocoder.springboot.lab23.springmvc.controller;

import cn.iocoder.springboot.lab23.springmvc.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * UserController 集成测试
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
@AutoConfigureMockMvc
/**
 * 用于自动化配置我们稍后注入的 MockMvc Bean 对象 mvc 。在后续的测试中，我们会看到都是通过 mvc 调用后端 API 接口。而每一次调用后端 API 接口，都会执行真正的后端逻辑。因此，整个逻辑，走的是集成测试，会启动一个真实的 Spring 环境。
 */
public class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    public void testList() throws Exception {
        // 查询用户列表
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/users"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().json("[\n" +
                "    {\n" +
                "        \"id\": 1,\n" +
                "        \"username\": \"yudaoyuanma\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 2,\n" +
                "        \"username\": \"woshiyutou\"\n" +
                "    },\n" +
                "    {\n" +
                "        \"id\": 3,\n" +
                "        \"username\": \"chifanshuijiao\"\n" +
                "    }\n" +
                "]")); // 响应结果
    }

    @Test
    public void testGet() throws Exception {
        // 获得指定用户编号的用户
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/users/1"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().json("{\n" +
                "\"id\": 1,\n" +
                "\"username\": \"username:1\"\n" +
                "}")); // 响应结果


        ResultActions perform = mvc.perform(MockMvcRequestBuilders.get("/users2/list"));
        perform.andExpect(MockMvcResultMatchers.status().isOk());
        perform.andExpect(MockMvcResultMatchers.content().json("[\n" +
                "  {\n" +
                "    \"id\": 1,\n" +
                "    \"username\": \"yudaoyuanma\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 2,\n" +
                "    \"username\": \"woshiyutou\"\n" +
                "  },\n" +
                "  {\n" +
                "    \"id\": 3,\n" +
                "    \"username\": \"chifanshuijiao\"\n" +
                "  }\n" +
                "]"));
    }

    @Test
    public void testGet2() throws Exception {
        // 获得指定用户编号的用户
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/users/1"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().json("{\n" +
                "\"id\": 1,\n" +
                "\"username\": \"username:1\"\n" +
                "}")); // 响应结果

        // 打印结果
        resultActions.andDo(MockMvcResultHandlers.print());

        // 获得 MvcResult ，后续执行各种自定义逻辑
        MvcResult mvcResult = resultActions.andReturn();
        System.out.println("拦截器数量：" + mvcResult.getInterceptors().length);
    }

    @Test
    public void testAdd() throws Exception {
        // 获得指定用户编号的用户
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.post("/users")
            .param("username", "yudaoyuanma")
            .param("passowrd", "nicai"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().string("1")); // 响应结果
    }

    @Test
    public void testUpdate() throws Exception {
        // 获得指定用户编号的用户
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.put("/users/1")
                .param("username", "yudaoyuanma")
                .param("passowrd", "nicai"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().string("true")); // 响应结果
    }

    @Test
    public void testDelete() throws Exception {
        // 获得指定用户编号的用户
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.delete("/users/1"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().string("false")); // 响应结果
    }

}
