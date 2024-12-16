package com.example.potteryworkshop.controllers;

import com.example.potteryworkshop.models.dtos.PotterInputDTO;
import com.example.potteryworkshop.services.EventService;
import com.example.potteryworkshop.services.PotterService;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.controllers.PotterController;
import org.example.forms.AddPotterForm;
import org.example.forms.EditPotterForm;
import org.example.viewmodels.AdminPotterListViewModel;
import org.example.viewmodels.PotterDetailsViewModel;
import org.example.viewmodels.PotterListViewModel;
import org.example.viewmodels.base.AdminPotterViewModel;
import org.example.viewmodels.base.BaseViewModel;
import org.example.viewmodels.base.EventViewModel;
import org.example.viewmodels.base.PotterViewModel;
import org.example.viewmodels.forms.PotterCreateViewModel;
import org.example.viewmodels.forms.PotterEditViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/potters")
public class PotterControllerImpl implements PotterController {

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private PotterService potterService;
    private EventService eventService;

    @Autowired
    public void setPotterService(PotterService potterService) {
        this.potterService = potterService;
    }

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Override
    @GetMapping("/")
    public String showPotters(Model model) {
        LOG.log(Level.INFO, "Show employed potters for user");
        var potterViewModels = potterService.findEmployedPotters().stream().map(potter -> new PotterViewModel(potter.getId(), potter.getName(), potter.getExperienceYears(), potter.getExperienceMonths(), potter.getImageUrl())).toList();
        var viewModel = new PotterListViewModel(createBaseViewModel("Гончары"), potterViewModels);
        model.addAttribute("model", viewModel);
        return "potters";
    }

    @Override
    @GetMapping("/admin")
    public String adminShowPotters(Model model) {
        LOG.log(Level.INFO, "Show all potters for admin");
        var potterViewModels = potterService.findAllPotters().stream().map(potter -> new AdminPotterViewModel(potter.getId(), potter.getName(), potter.getEmploymentDate(), potter.isEmployed())).toList();
        var viewModel = new AdminPotterListViewModel(createBaseViewModel("Гончары"), potterViewModels);
        model.addAttribute("model", viewModel);
        return "potters-admin";
    }

    @Override
    @GetMapping("/create")
    public String createForm(Model model) {
        LOG.log(Level.INFO, "Create potter");
        var viewModel = new PotterCreateViewModel(createBaseViewModel("Добавление гончара"));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new AddPotterForm("", null, ""));
        return "potter-create";
    }

    @Override
    @PostMapping("/create")
    public String addPotter(@Valid @ModelAttribute("form") AddPotterForm addPotterForm,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            LOG.log(Level.INFO, "Incorrect data for creating potter");
            var viewModel = new PotterCreateViewModel(createBaseViewModel("Добавление гончара"));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", addPotterForm);
            return "potter-create";
        }
        LOG.log(Level.INFO, "Successfully creating potter");
        potterService.addPotter(new PotterInputDTO(addPotterForm.name(), addPotterForm.employmentDate(), addPotterForm.imageUrl()));
        return "redirect:/potters/admin";
    }

    @Override
    @GetMapping("/{potterId}/edit")
    public String editForm(@PathVariable UUID potterId,
                           Model model) {
        LOG.log(Level.INFO, "Edit potter");
        var potter = potterService.findById(potterId);
        var viewModel = new PotterEditViewModel(createBaseViewModel("Редактирование гончара"), potterId);
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new EditPotterForm(potter.getName(), potter.getEmploymentDate(), potter.getImageUrl()));
        return "potter-edit";
    }

    @Override
    @PostMapping("/{potterId}/edit")
    public String editPotter(@PathVariable UUID potterId,
                             @Valid @ModelAttribute("form") EditPotterForm editPotterForm,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            LOG.log(Level.INFO, "Incorrect data for editing potter with id: " + potterId);
            var viewModel = new PotterEditViewModel(createBaseViewModel("Редактирование гончара"), potterId);
            model.addAttribute("model", viewModel);
            model.addAttribute("form", editPotterForm);
            return "potter-edit";
        }
        LOG.log(Level.INFO, "Successfully editing potter");
        potterService.updatePotter(potterId, new PotterInputDTO(editPotterForm.name(), editPotterForm.employmentDate(), editPotterForm.imageUrl()));
        return "redirect:/potters/admin";
    }

    @Override
    @PostMapping("/{potterId}/dismiss")
    public String dismissPotter(@PathVariable UUID potterId) {
        LOG.log(Level.INFO, "Potter dismissal with id: " + potterId);
        potterService.dismissPotter(potterId);
        return "redirect:/potters/admin";
    }

    @Override
    @GetMapping("/{potterId}")
    public String showPotterDetails(@PathVariable UUID potterId,
                                    Model model) {
        LOG.log(Level.INFO, "Show potter details with id: " + potterId);
        var potter = potterService.findById(potterId);
        var events = eventService.showPottersEvents(potterId).stream().map(event -> new EventViewModel(event.getId().toString(), event.getName(), event.getDuration(), event.getCost(), event.getDiscountCost(), event.getDescription(), event.getDate(), event.getImageUrl(), event.getCategoryName(), event.getDifficultyName(), event.getDifficultyName())).toList();
        var viewModel = new PotterDetailsViewModel(createBaseViewModel("Гончар"), new PotterViewModel(potter.getId(), potter.getName(), potter.getExperienceYears(), potter.getExperienceMonths(), potter.getImageUrl()),
                events);
        model.addAttribute("model", viewModel);
        return "potter-details";
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}
