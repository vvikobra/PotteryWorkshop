package com.example.potteryworkshop.controllers;

import com.example.potteryworkshop.exceptions.order.NotEnoughTicketsException;
import com.example.potteryworkshop.models.dtos.*;
import com.example.potteryworkshop.services.*;
import jakarta.validation.Valid;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.controllers.EventController;
import org.example.forms.AddEventForm;
import org.example.forms.EditEventForm;
import org.example.forms.EventRegistrationForm;
import org.example.viewmodels.*;
import org.example.viewmodels.base.*;
import org.example.viewmodels.forms.EventCreateViewModel;
import org.example.viewmodels.forms.EventEditViewModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/events")
public class EventControllerImpl implements EventController {

    private static final Logger LOG = LogManager.getLogger(Controller.class);

    private EventService eventService;
    private CategoryService categoryService;
    private DifficultyService difficultyService;
    private OrderService orderService;
    private PotterService potterService;

    @Autowired
    public void setEventService(EventService eventService) {
        this.eventService = eventService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setDifficultyService(DifficultyService difficultyService) {
        this.difficultyService = difficultyService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setPotterService(PotterService potterService) {
        this.potterService = potterService;
    }

    @Override
    @GetMapping("/")
    public String showAllEvents(Model model) {
        LOG.log(Level.INFO, "Show all events for admin");
        var eventViewModels = eventService.findAllEvents().stream().map(event -> new AdminEventViewModel(event.getId(), event.getName(), event.getDate(), event.getCost(), event.getCategoryName(), event.getDifficultyName())).toList();
        var viewModel = new EventListViewModel(createBaseViewModel("Мероприятия"), eventViewModels);
        model.addAttribute("model", viewModel);
        return "events";
    }

    @Override
    @GetMapping("/create")
    public String createForm(Model model) {
        LOG.log(Level.INFO, "Create event");
        var viewModel = new EventCreateViewModel(createBaseViewModel("Добавление мероприятия"));
        List<String> categories = categoryService.findAllCategories().stream().map(CategoryDTO::getName).toList();
        List<String> difficulties = difficultyService.findAllDifficulties().stream().map(DifficultyDTO::getName).toList();
        List<String> potters = potterService.findEmployedPotters().stream().map(PotterOutputDTO::getName).toList();
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new AddEventForm("", 30, 1000, LocalDateTime.now(), "", "", "", null, "", 1));
        model.addAttribute("categories", categories);
        model.addAttribute("difficulties", difficulties);
        model.addAttribute("potters", potters);

        return "event-create";
    }

    @Override
    @PostMapping("/create")
    public String addEvent(@Valid @ModelAttribute("form") AddEventForm addEventForm,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            LOG.log(Level.INFO, "Incorrect data for creating event");
            var viewModel = new EventCreateViewModel(createBaseViewModel("Добавление мероприятия"));
            List<String> categories = categoryService.findAllCategories().stream().map(CategoryDTO::getName).toList();
            List<String> difficulties = difficultyService.findAllDifficulties().stream().map(DifficultyDTO::getName).toList();
            List<String> potters = potterService.findEmployedPotters().stream().map(PotterOutputDTO::getName).toList();
            model.addAttribute("model", viewModel);
            model.addAttribute("form", addEventForm);
            model.addAttribute("categories", categories);
            model.addAttribute("difficulties", difficulties);
            model.addAttribute("potters", potters);
            return "event-create";
        }
        eventService.addEvent(new EventInputDTO(addEventForm.name(), addEventForm.duration(), addEventForm.cost(), addEventForm.maxParticipants(), addEventForm.description(), addEventForm.date(), addEventForm.imageUrl(), addEventForm.categoryName(), addEventForm.difficultyName(), addEventForm.potterName()));
        LOG.log(Level.INFO, "Successfully creating event");
        return "redirect:/events/";
    }

    @Override
    @GetMapping("/{eventId}/edit")
    public String editForm(@PathVariable UUID eventId, Model model) {
        LOG.log(Level.INFO, "Edit event with id: " + eventId);
        var event = eventService.findById(eventId);
        var viewModel = new EventEditViewModel(createBaseViewModel("Редактирование мероприятия"), eventId);
        List<String> categories = categoryService.findAllCategories().stream().map(CategoryDTO::getName).toList();
        List<String> difficulties = difficultyService.findAllDifficulties().stream().map(DifficultyDTO::getName).toList();
        List<String> potters = potterService.findEmployedPotters().stream().map(PotterOutputDTO::getName).toList();
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new EditEventForm(event.getName(), event.getDuration(), event.getCost(), event.getDate(), event.getCategoryName(), event.getDifficultyName(), event.getImageUrl(), event.getPotterName(), event.getDescription(), event.getMaxParticipants()));
        model.addAttribute("categories", categories);
        model.addAttribute("difficulties", difficulties);
        model.addAttribute("potters", potters);
        return "event-edit";
    }

    @Override
    @PostMapping("/{eventId}/edit")
    public String editEvent(@PathVariable UUID eventId,
                            @Valid @ModelAttribute("form") EditEventForm editEventForm,
                            BindingResult bindingResult,
                            Model model) {
        if (bindingResult.hasErrors()) {
            LOG.log(Level.INFO, "Incorrect data for creating event with id: " + eventId);
            var viewModel = new EventEditViewModel(createBaseViewModel("Редактирование книги"), eventId);
            List<String> categories = categoryService.findAllCategories().stream().map(CategoryDTO::getName).toList();
            List<String> difficulties = difficultyService.findAllDifficulties().stream().map(DifficultyDTO::getName).toList();
            List<String> potters = potterService.findEmployedPotters().stream().map(PotterOutputDTO::getName).toList();
            model.addAttribute("model", viewModel);
            model.addAttribute("form", editEventForm);
            model.addAttribute("categories", categories);
            model.addAttribute("difficulties", difficulties);
            model.addAttribute("potters", potters);
            return "event-edit";
        }
        eventService.updateEvent(eventId, new EventInputDTO(editEventForm.name(), editEventForm.duration(), editEventForm.cost(), editEventForm.maxParticipants(), editEventForm.description(), editEventForm.date(), editEventForm.imageUrl(), editEventForm.categoryName(), editEventForm.difficultyName(), editEventForm.potterName()));
        LOG.log(Level.INFO, "Successfully edit event with id: " + eventId);
        return "redirect:/events/";
    }

    @Override
    @GetMapping("/upcoming")
    public String showUpcomingEvents(Model model) {
        LOG.log(Level.INFO, "Show upcoming events");
        var eventViewModels = eventService.showUpcomingEvents().stream().map(e -> new EventViewModel(e.getId(), e.getName(), e.getDuration(), e.getCost(), e.getDiscountCost(), e.getDescription(), e.getDate(), e.getImageUrl(), e.getCategoryName(), e.getDifficultyName(), e.getPotterName())).toList();
        var viewModel = new UpcomingEventsViewModel(createBaseViewModel("Расписание"),
                eventViewModels);

        model.addAttribute("model", viewModel);
        return "events-upcoming";
    }

    @Override
    @GetMapping("/discount")
    public String showDiscountEvents(Model model) {
        LOG.log(Level.INFO, "Show discounted events");
        var eventViewModels = eventService.showDiscountedEvents().stream().map(e -> new DiscountEventViewModel(e.getId().toString(), e.getName(), e.getDuration(), e.getCost(), e.getDiscountCost(), e.getDescription(), e.getDate(), e.getImageUrl(), e.getCategoryName(), e.getDifficultyName(), e.getPotterName())).toList();
        var viewModel = new ListDiscountEventViewModel(createBaseViewModel("Акции"),
                eventViewModels);
        model.addAttribute("model", viewModel);
        return "events-discount";
    }

    @Override
    @GetMapping("/{eventId}")
    public String registerToEventForm(Principal principal,
                                      @PathVariable UUID eventId, Model model) {
        LOG.log(Level.INFO, "Register to event with id: " + eventId);
        var event = eventService.findById(eventId);
        var potter = potterService.findById(event.getPotterId());
        var viewModel = new EventRegistrationViewModel(createBaseViewModel("Запись на мероприятие"), new EventViewModel(event.getId(), event.getName(), event.getDuration(), event.getCost(), event.getDiscountCost(), event.getDescription(), event.getDate(), event.getImageUrl(), event.getCategoryName(), event.getDifficultyName(), event.getPotterName()),
                new PotterViewModel(potter.getId(), potter.getName(), potter.getExperienceYears(), potter.getExperienceMonths(), potter.getImageUrl()));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new EventRegistrationForm(1));
        return "event-registration";
    }

    @Override
    @PostMapping("/{eventId}")
    public String registerToEvent(Principal principal,
                                  @PathVariable UUID eventId,
                                  @ModelAttribute("form") EventRegistrationForm eventRegistrationForm,
                                  BindingResult bindingResult, Model model) {
        var event = eventService.findById(eventId);
        var email = principal.getName();
        if (bindingResult.hasErrors()) {
            LOG.log(Level.INFO, "Incorrect data for registration to event with id: " + eventId);
            var potter = potterService.findById(event.getPotterId());
            var viewModel = new EventRegistrationViewModel(createBaseViewModel("Запись на мероприятие"), new EventViewModel(event.getId(), event.getName(), event.getDuration(), event.getCost(), event.getDiscountCost(), event.getDescription(), event.getDate(), event.getImageUrl(), event.getCategoryName(), event.getDifficultyName(), event.getPotterName()),
                    new PotterViewModel(potter.getId(), potter.getName(), potter.getExperienceYears(), potter.getExperienceMonths(), potter.getImageUrl()));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", eventRegistrationForm);
            return "event-registration";
        }
        try {
            orderService.addOrder(new OrderInputDTO(eventRegistrationForm.ticketQuantity(), email, eventId), false);
            LOG.log(Level.INFO, "Successfully registration to event with id: " + eventId);
            return "redirect:/users/profile/actual";
        } catch (NotEnoughTicketsException exception) {
            LOG.log(Level.INFO, "Incorrect data for registration to event with id: " + eventId);
            var potter = potterService.findById(event.getPotterId());
            var viewModel = new EventRegistrationViewModel(createBaseViewModel("Запись на мероприятие"), new EventViewModel(event.getId(), event.getName(), event.getDuration(), event.getCost(), event.getDiscountCost(), event.getDescription(), event.getDate(), event.getImageUrl(), event.getCategoryName(), event.getDifficultyName(), event.getPotterName()),
                    new PotterViewModel(potter.getId(), potter.getName(), potter.getExperienceYears(), potter.getExperienceMonths(), potter.getImageUrl()));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", eventRegistrationForm);
            model.addAttribute("error", exception.getMessage());
            return "event-registration";
        }
    }

    @Override
    @GetMapping("/discount/{eventId}")
    public String registerToDiscountEventForm(Principal principal,
                                              @PathVariable UUID eventId,
                                              Model model) {
        LOG.log(Level.INFO, "Register to discount event with id: " + eventId);
        var event = eventService.findById(eventId);
        var potter = potterService.findById(event.getPotterId());
        System.out.println(potter.getId() + potter.getName() + potter.getEmploymentDate());
        var viewModel = new DiscountEventRegistrationViewModel(createBaseViewModel("Запись на мероприятие"),
                new DiscountEventViewModel(event.getId().toString(), event.getName(), event.getDuration(), event.getCost(), event.getDiscountCost(), event.getDescription(), event.getDate(), event.getImageUrl(), event.getCategoryName(), event.getDifficultyName(), event.getPotterName()),
                new PotterViewModel(potter.getId(), potter.getName(), potter.getExperienceYears(), potter.getExperienceMonths(), potter.getImageUrl()));
        model.addAttribute("model", viewModel);
        model.addAttribute("form", new EventRegistrationForm(1));
        return "event-discount-registration";
    }

    @Override
    @PostMapping("/discount/{eventId}")
    public String registerToDiscountEvent(Principal principal,
                                          @PathVariable UUID eventId,
                                          @ModelAttribute("form") EventRegistrationForm eventRegistrationForm,
                                          BindingResult bindingResult,
                                          Model model) {
        var event = eventService.findById(eventId);
        var email = principal.getName();
        if (bindingResult.hasErrors()) {
            LOG.log(Level.INFO, "Incorrect data for registration to discount event with id: " + eventId);
            var potter = potterService.findById(event.getPotterId());
            var viewModel = new DiscountEventRegistrationViewModel(createBaseViewModel("Запись на мероприятие"),
                    new DiscountEventViewModel(event.getId().toString(), event.getName(), event.getDuration(), event.getCost(), event.getDiscountCost(), event.getDescription(), event.getDate(), event.getImageUrl(), event.getCategoryName(), event.getDifficultyName(), event.getPotterName()),
                    new PotterViewModel(potter.getId(), potter.getName(), potter.getExperienceYears(), potter.getExperienceMonths(), potter.getImageUrl()));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", eventRegistrationForm);
            return "event-registration";
        }
        try {
            orderService.addOrder(new OrderInputDTO(eventRegistrationForm.ticketQuantity(), email, eventId), true);
            LOG.log(Level.INFO, "Successfully registration to discount event with id: " + eventId);
            return "redirect:/users/profile/actual";
        } catch (NotEnoughTicketsException exception) {
            LOG.log(Level.INFO, "Incorrect data for registration to discount event with id: " + eventId);
            var potter = potterService.findById(event.getPotterId());
            var viewModel = new DiscountEventRegistrationViewModel(createBaseViewModel("Запись на мероприятие"),
                    new DiscountEventViewModel(event.getId().toString(), event.getName(), event.getDuration(), event.getCost(), event.getDiscountCost(), event.getDescription(), event.getDate(), event.getImageUrl(), event.getCategoryName(), event.getDifficultyName(), event.getPotterName()),
                    new PotterViewModel(potter.getId(), potter.getName(), potter.getExperienceYears(), potter.getExperienceMonths(), potter.getImageUrl()));
            model.addAttribute("model", viewModel);
            model.addAttribute("form", eventRegistrationForm);
            model.addAttribute("error", exception.getMessage());
            return "event-registration";
        }
    }

    @Override
    public BaseViewModel createBaseViewModel(String title) {
        return new BaseViewModel(title);
    }
}