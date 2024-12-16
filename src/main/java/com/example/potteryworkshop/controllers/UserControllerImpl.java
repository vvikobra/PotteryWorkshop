package com.example.potteryworkshop.controllers;

import com.example.potteryworkshop.exceptions.user.PasswordsNotMatchException;
import com.example.potteryworkshop.exceptions.user.UserAlreadyExistsException;
import com.example.potteryworkshop.models.dtos.UserInputDTO;
import com.example.potteryworkshop.models.dtos.UserRegistrationDTO;
import com.example.potteryworkshop.services.Impl.AuthService;
import com.example.potteryworkshop.services.OrderService;
import com.example.potteryworkshop.services.UserService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.controllers.UserController;
import org.example.forms.EditProfileForm;
import org.example.forms.SignInForm;
import org.example.forms.SignUpForm;
import org.example.viewmodels.EditProfileViewModel;
import org.example.viewmodels.UserProfileViewModel;
import org.example.viewmodels.base.BaseViewModel;
import org.example.viewmodels.base.OrderViewModel;
import org.example.viewmodels.forms.AuthorizationViewModel;
import org.example.viewmodels.forms.RegistrationViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/users")
public class UserControllerImpl implements UserController {

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private UserService userService;
    private OrderService orderService;
    private AuthService authService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Override
    @GetMapping("/login")
    public String login(Model model) {
        LOG.log(Level.INFO, "Login");
        var viewModel = new AuthorizationViewModel(createBaseViewModel("Авторизация"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new SignInForm("", ""));
        return "login";
    }

    @PostMapping("/login-error")
    public String onFailedLogin(
            @ModelAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY) String username,
            RedirectAttributes redirectAttributes) {

        LOG.log(Level.INFO, "Failed login");
        redirectAttributes.addFlashAttribute(UsernamePasswordAuthenticationFilter.SPRING_SECURITY_FORM_USERNAME_KEY, username);
        redirectAttributes.addFlashAttribute("badCredentials", true);

        return "redirect:/users/login";
    }

    @Override
    @GetMapping("/register")
    public String registerForm(Model model) {
        LOG.log(Level.INFO, "Registration");
        var viewModel = new RegistrationViewModel(createBaseViewModel("Регистрация"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new SignUpForm("", "", "", ""));
        return "register";
    }

    @Override
    @PostMapping("/register")
    public String register(@Valid @ModelAttribute("form") SignUpForm signUpForm,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            LOG.log(Level.INFO, "Incorrect data for registration");
            var viewModel = new RegistrationViewModel(createBaseViewModel("Регистрация"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", signUpForm);
            return "register";
        }
        try {
            UserRegistrationDTO userRegistrationDTO = new UserRegistrationDTO(signUpForm.name(), signUpForm.email(), signUpForm.password(), signUpForm.confirmPassword());
            authService.register(userRegistrationDTO);
            LOG.log(Level.INFO, "Successfully registration");
            return "redirect:/users/login";
        } catch (UserAlreadyExistsException | PasswordsNotMatchException exception) {
            LOG.log(Level.INFO, "Error during registration: " + exception.getMessage());
            var viewModel = new RegistrationViewModel(createBaseViewModel("Регистрация"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", signUpForm);
            model.addAttribute("error", exception.getMessage());
            return "register";
        }
    }

    @Override
    @GetMapping("/edit")
    public String editForm(Principal principal, Model model) {
        LOG.log(Level.INFO, "User edit profile: " + principal.getName());
        var user = userService.findByEmail(principal.getName());
        var viewModel = new EditProfileViewModel(createBaseViewModel("Редактирование профиля"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new EditProfileForm(user.getName(),"", ""));
        return "user-profile-edit";
    }

    @Override
    @PostMapping("/edit")
    public String editProfile(Principal principal,
                              @Valid @ModelAttribute("form") EditProfileForm editProfileForm,
                              BindingResult bindingResult,
                              Model model) {
        var email = principal.getName();
        if (bindingResult.hasErrors()) {
            LOG.log(Level.INFO, "Incorrect data for profile edit");
            var viewModel = new EditProfileViewModel(createBaseViewModel("Редактирование профиля"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", editProfileForm);
            return "user-profile-edit";
        }
        try {
            UserInputDTO userInputDTO = new UserInputDTO(editProfileForm.password(), editProfileForm.confirmPassword(), editProfileForm.name(), email);
            userService.updateUser(userInputDTO);
            LOG.log(Level.INFO, "Successfully editing profile");
            return "redirect:/users/profile/actual";
        } catch (PasswordsNotMatchException exception) {
            LOG.log(Level.INFO, "Error during editing profile: " + exception.getMessage());
            var viewModel = new EditProfileViewModel(createBaseViewModel("Регистрация"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", editProfileForm);
            model.addAttribute("error", exception.getMessage());
            return "user-profile-edit";
        }
    }

    @Override
    @GetMapping("/profile/{status}")
    public String showUserProfile(Principal principal,
                                  @PathVariable String status,
                                  Model model) {
        LOG.log(Level.INFO, "Show user profile for: " + principal.getName());
        String email = principal.getName();
        var user = authService.getUser(email);
        List<OrderViewModel> orders = List.of();
        if (status.equals("actual"))
            orders = orderService.getActualOrders(user.getId()).stream().map(order -> new OrderViewModel(order.getId(), order.getOrderDate(), order.getEventDate(), order.getTicketQuantity(), order.getEventName(), order.getTotalCost(), order.getStatusName())).toList();
        else if (status.equals("completed"))
            orders = orderService.getPastOrders(user.getId()).stream().map(order -> new OrderViewModel(order.getId(), order.getOrderDate(), order.getEventDate(), order.getTicketQuantity(), order.getEventName(), order.getTotalCost(), order.getStatusName())).toList();
        else if (status.equals("canceled"))
            orders = orderService.getCanceledOrders(user.getId()).stream().map(order -> new OrderViewModel(order.getId(), order.getOrderDate(), order.getEventDate(), order.getTicketQuantity(), order.getEventName(), order.getTotalCost(), order.getStatusName())).toList();
        var viewModel = new UserProfileViewModel(createBaseViewModel("Профиль"), user.getName(), user.getEmail(), orders);
        model.addAttribute("model", viewModel);
        return "user-profile";
    }

    @Override
    @PostMapping("/{orderId}")
    public String cancelOrder(@PathVariable UUID orderId) {
        LOG.log(Level.INFO, "Cancel order with id: " + orderId);
        orderService.cancelOrder(orderId);
        return "redirect:/users/profile/canceled";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}