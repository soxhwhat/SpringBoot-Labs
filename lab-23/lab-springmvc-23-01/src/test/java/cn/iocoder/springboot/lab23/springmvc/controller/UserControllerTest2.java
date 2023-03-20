package cn.iocoder.springboot.lab23.springmvc.controller;

import cn.iocoder.springboot.lab23.springmvc.service.UserService;
import cn.iocoder.springboot.lab23.springmvc.vo.UserVO;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

/**
 * UserController 单元测试
 *
 * 参考 https://spring.io/guides/gs/testing-web/ 文章
 */
@RunWith(SpringRunner.class)
/**
 * 在类上添加 @WebMvcTest 注解，并且传入的是 UserController 类，表示我们要对 UserController 进行单元测试。
 * @WebMvcTest 注解，是包含了 @AutoConfigureMockMvc 的组合注解，所以它会自动化配置我们稍后注入的 MockMvc Bean 对象 mvc 。在后续的测试中，我们会看到都是通过 mvc 调用后端 API 接口。但是！每一次调用后端 API 接口，并不会执行真正的后端逻辑，而是走的 Mock 逻辑。也就是说，整个逻辑，走的是单元测试，只会启动一个 Mock 的 Spring 环境。
 */
@WebMvcTest(UserController.class)
public class UserControllerTest2 {

    @Autowired
    private MockMvc mvc;

    @MockBean
    //实际这里注入的是一个使用 Mockito 创建的 UserService Mock 代理对象。
    private UserService userService;

    @Test
    public void testGet2() throws Exception {
        // Mock UserService 的 get 方法
        // 结果竟然返回的是 null 空。理论来说，此时应该返回一个 id = 1 的 UserVO 对象。实际上，因为此时的 userService 是通过 Mockito 来 Mock 出来的对象，其所有调用它的方法，返回的都是空。
        System.out.println("before mock:" + userService.get(1));
        Mockito.when(userService.get(1)).thenReturn(
                new UserVO().setId(1).setUsername("username:1"));
        System.out.println("after mock:" + userService.get(1));

        // 查询用户列表
        ResultActions resultActions = mvc.perform(MockMvcRequestBuilders.get("/users/v2/1"));
        // 校验结果
        resultActions.andExpect(MockMvcResultMatchers.status().isOk()); // 响应状态码 200
        resultActions.andExpect(MockMvcResultMatchers.content().json("{\n" +
                "    \"id\": 1,\n" +
                "    \"username\": \"username:1\"\n" +
                "}")); // 响应结果
    }

}
