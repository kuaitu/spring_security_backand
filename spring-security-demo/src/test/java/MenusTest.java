import com.lmk.springsecuritydemo.sys.service.IPermissionService;
import com.lmk.springsecuritydemo.sys.vo.Menus;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@Slf4j
@SpringBootTest
public class MenusTest {

    @Autowired
    private IPermissionService service;

    @Test
    public void getMenus(){
        List<Menus> menus = service.findMenusByUserId(1);

        log.info("菜单树={}", menus.toString());
    }
}
