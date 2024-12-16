package com.example.potteryworkshop.controllers;

import com.example.potteryworkshop.services.EventService;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.controllers.HomeController;
import org.example.viewmodels.base.BaseViewModel;
import org.example.viewmodels.base.EventViewModel;
import org.example.viewmodels.home.HomeViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeControllerImpl implements HomeController {

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private EventService eventService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    @GetMapping("/")
    public String createHomeViewModel(Model model) {
        LOG.log(Level.INFO, "Show home page");
        var eventViewModels = eventService.showTopEvents().stream().map(e -> new EventViewModel(e.getId().toString(), e.getName(), e.getDuration(), e.getCost(), e.getDiscountCost(), e.getDescription(), e.getDate(), e.getImageUrl(), e.getCategoryName(), e.getDifficultyName(), e.getPotterName())).toList();
        var viewModel = new HomeViewModel(createBaseViewModel("Главная"), eventViewModels);

        model.addAttribute("model", viewModel);
        return "home";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
