import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/ban")
    public void banUser(@RequestParam Integer userId, @RequestParam LocalDateTime banTime) {
        userService.banUser(userId, banTime);
    }

}